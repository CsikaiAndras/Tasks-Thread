import java.util.List;
import java.util.Random;

public enum Difficulty {

    EASY(100),
    MEDIUM(150),
    HARD(200),
    VERY_HARD(250);

    private static final List<Difficulty> VALUES =
            List.of(values());

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public final int reward;

    Difficulty(int reward) {this.reward=reward; }

    public static Difficulty randomDifficulty() {return VALUES.get(RANDOM.nextInt(SIZE));}


}
