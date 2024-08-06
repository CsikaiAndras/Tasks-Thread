import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Project {

    private static final String INFO_TEMPLATE = "Project #%d: %s with %s completion time in %s language";

    private final Language language;

    private final Difficulty difficulty;

    private final CompletionTime completionTime;

    private final int contractID;
    private int actualCompletionTime = 0;

    private final AtomicBoolean isOwned = new AtomicBoolean(false);
    private static final AtomicInteger idGen= new AtomicInteger(1);

    private Project(final Language language, final Difficulty difficulty, final CompletionTime completionTime){
        this.language = language;
        this.difficulty=difficulty;
        this.completionTime=completionTime;
        this.contractID = idGen.getAndIncrement();
    }

    public static Project generateProject(){
        return new Project(Language.randomLanguage(), Difficulty.randomDifficulty(), CompletionTime.randomTime());
    }


    public void take(){
        isOwned.set(true);
    }

    public boolean isOwned() {return isOwned.get();}

    public Language getLanguage() {return this.language; }
    public Difficulty getDifficulty() {return this.difficulty; }
    public int getReward() {return difficulty.reward;}

    public int getCompletionTime(){
        if(actualCompletionTime == 0){
            actualCompletionTime = getRandom(completionTime.min, completionTime.max);
        }
        return actualCompletionTime;
    }
    public String getInfo(){
        return String.format(INFO_TEMPLATE, contractID, difficulty.name(), completionTime.name(), language.name());
    }

    private int getRandom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min,max);
    }

}
