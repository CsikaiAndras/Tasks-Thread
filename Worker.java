import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker {

    private static final int PROBLEM_FINISH_MIN = 200;
    private static final int PROBLEM_FINISH_MAX = 300;

    private final String name;

    private final AtomicInteger money = new AtomicInteger(0);

    private Project ownedProject;

    public Worker(final String name) {this.name=name;}



    public void startProject(Project project) throws InterruptedException {
        project.take();
        System.out.println(this.name+" has taken " + project.getInfo());
        System.out.println(this.name+" figuring out problem for " + project.getCompletionTime()+ "msec");
        Thread.sleep(project.getCompletionTime());
        int problemSolveTime = calculateSolveTime(project);
        System.out.println(this.name+" found the problem it takes "+ problemSolveTime + "msec to solve" );
        Thread.sleep(problemSolveTime);
        addMoney(project.getReward());
        this.ownedProject = null;
        System.out.println(this.name + " finished "+ project.getInfo());



    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money.get();
    }

    private void addMoney(int amount){
        money.addAndGet(amount);
    }

    private int calculateSolveTime(Project project){
        int difficultyMultiplier = project.getDifficulty().ordinal() +1;
        int min = PROBLEM_FINISH_MIN * difficultyMultiplier;
        int max = PROBLEM_FINISH_MAX * difficultyMultiplier;
        return getRandom(min, max);
    }

    private int getRandom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min,max);
    }

}
