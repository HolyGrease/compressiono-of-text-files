package Tree;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;

public class Leaf implements Branch, Externalizable {

    private String value;

    // Required for correct implementation of Externalizable interface
    public Leaf(){}

    public Leaf(String string){
        value = string;
    }

    @Override
    public List<String> getValue() {
        List<String> list = new LinkedList<>();
        list.add(value);
        return list;
    }

    @Override
    public List<Boolean> encode(String string) {
        return new LinkedList<>();
    }

    @Override
    public String decode(List<Boolean> list) {
        return value;
    }

    @Override
    public String toString(List<Boolean> list) {
        if (value.equals("\n"))
            return list.toString() + " - \"\\n\"";
        else if ((int)value.charAt(0) == 13)
            return list.toString() + " - \"CR\"";
        else
            return list.toString() + " - \"" + value + "\"";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        value = (String) in.readObject();
    }
}
