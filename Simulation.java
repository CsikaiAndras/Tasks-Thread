import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {

    private final static String[] WORKER_NAMES = "Andris, Laci, Martin, Peti".split(",");

    private final static int PROJECT_MAX = 4;

    private final  static int GOAL_MONEY = 700;

    private final static int WAIT_BETWEEN_PROJECT = 500;

    private final static List<Worker> workers = new ArrayList<>();


    private final static AtomicBoolean simulationOver = new AtomicBoolean(false);
    private final static BlockingQueue<Project> projects = new LinkedBlockingDeque<>(PROJECT_MAX);
    private final static ExecutorService workerExecutor = Executors.newFixedThreadPool(WORKER_NAMES.length + 1);
    private final static ScheduledExecutorService checkExecutor = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) {
        for(String name : WORKER_NAMES){
            workers.add(new Worker(name));

        }

        for(int i = 0; i<PROJECT_MAX; ++i){
            Project project = Project.generateProject();
            projects.add(project);
            System.out.println("New project is up : " + project.getInfo());
        }

        for(Worker worker : workers){
            workerExecutor.submit(()->startWorker(worker));
        }

        workerExecutor.submit(Simulation::generateProject);
        checkExecutor.scheduleAtFixedRate(Simulation::checkSimulationOver, 0, 500, TimeUnit.MILLISECONDS);

        try{
            boolean over = workerExecutor.awaitTermination(1, TimeUnit.MINUTES);
            if (over){
                System.out.println("Everyone finished before closing");
            }else {
                System.out.println("Someone got locked in");
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            simulationOver.set(true);
            workerExecutor.shutdownNow();
            checkExecutor.shutdownNow();
        }

    }

    private static void startWorker(final Worker worker){
        while(!simulationOver.get()){
            Project p = projects.poll();

            assert p != null : "No project to work on";
            if(!p.isOwned()){
                try{
                    worker.startProject(p);
                    Thread.sleep(WAIT_BETWEEN_PROJECT);
                }
                catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void generateProject(){
        while(!simulationOver.get()){
            Project project = Project.generateProject();
            if(projects.offer(project)){
                System.out.println("New project: " + project.getInfo());
            }
            try{
                Thread.sleep(WAIT_BETWEEN_PROJECT);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
    private static void checkSimulationOver(){
        if(!simulationOver.get()){
            for (Worker worker : workers){
                if(worker.getMoney() >= GOAL_MONEY){
                    System.out.println("ᕦ(ò_óˇ)ᕤ " + worker.getName() + " won the race");
                    workerExecutor.shutdown();
                    simulationOver.set(true);
                    break;
                }
            }
        }
    }



}
