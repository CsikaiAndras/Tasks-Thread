import java.util.List;
import java.util.Random;

public enum Language {

    PYTHON,
    JAVA,
    C,
    RUST,
    TYPESCRIPT;

    private static final List<Language> VALUES = List.of(values());

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public static int getCount() {return SIZE;}

    public static Language randomLanguage() {return VALUES.get(RANDOM.nextInt(SIZE));}

}
