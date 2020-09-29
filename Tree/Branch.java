package Tree;

import java.util.List;

public interface Branch {
    List<String> getValue();
    List<Boolean> encode(String string);
    String decode(List<Boolean> list);
    String toString(List<Boolean> list);
}
