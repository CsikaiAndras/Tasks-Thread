import java.util.List;
import java.util.Random;

public enum CompletionTime {

    SHORT(1000,2500),
    MEDIUM(2500,5000),
    LONG(5000,10000);

    private static final List<CompletionTime> VALUES =List.of(values());

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public final int min;
    public final int max;

    CompletionTime(int min, int max){
        this.min=min;
        this.max=max;
    }

    public static CompletionTime randomTime() {return VALUES.get(RANDOM.nextInt(SIZE));}

}
