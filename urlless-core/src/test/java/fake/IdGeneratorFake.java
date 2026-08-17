package fake;

import com.test.generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class IdGeneratorFake implements IdGenerator {

    List<String> ids = new ArrayList<>();

    int count = 0;

    public void add(String id) {
        ids.add(id);
    }
    public String generate(){
        String res = ids.get(count);
        count = (count + 1) % ids.size();
        return res;
    }

}
