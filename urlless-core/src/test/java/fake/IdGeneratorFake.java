package fake;

import com.test.generator.IdGenerator;
import lombok.Getter;

import java.util.*;

@Getter
public class IdGeneratorFake implements IdGenerator {

    List<String> ids = new ArrayList<>();

    int count = 0;

    Set<String> collisions ;

    public void add( String ... id) {
        ids.addAll(Arrays.asList(id));
    }
    public String generate(String url, Set<String> collisions){
        String res = ids.get(count);
        count = (count + 1) % ids.size();
        this.collisions = collisions;
        return res;
    }

}
