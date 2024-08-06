import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LanguageSwitch {
    private final List<AtomicBoolean> languageSwitch;

    public LanguageSwitch(int size){
        List<AtomicBoolean> list = new ArrayList<>();
        for (int i=0; i<size; i++){
            list.add(new AtomicBoolean(false));
        }
        languageSwitch = Collections.synchronizedList(list);
    }

    public boolean get(int index){
        synchronized (languageSwitch){
            return languageSwitch.get(index).get();
        }
    }

    public void set(int index, boolean value){
        synchronized (languageSwitch){
            languageSwitch.get(index).set(value);
        }
    }
}
