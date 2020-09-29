package Tree;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;

public class Fork implements Branch, Externalizable {
    private List<String> value;
    private Branch left;
    private Branch right;

    // Required for correct implementation of Externalizable interface
    public Fork(){}

    public Fork(Branch left, Branch right, List<String> value){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public List<Boolean> encode(String string) {
        if (left.getValue().contains(string)){
            List list = left.encode(string);
            list.add(0, true);
            return list;
        }
        else if (right.getValue().contains(string)){
            List list = right.encode(string);
            list.add(0, false);
            return list;
        }
        else
            return new LinkedList<>();
    }

    @Override
    public String decode(List<Boolean> list) {
        if (list.isEmpty())
            return null;
        else if (list.get(0))
            return left.decode(list.subList(1, list.size()));
        else
            return right.decode(list.subList(1, list.size()));

    }

    @Override
    public String toString(List<Boolean> list) {
        String output = "";

        List<Boolean> leftList = new LinkedList<>(list);
        leftList.add(true);
        output += left.toString(leftList);

        output += "\n";

        list.add(false);
        output += right.toString(list);

        return output;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(value);
        out.writeObject(left);
        out.writeObject(right);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        value = (List<String>) in.readObject();
        left = (Branch) in.readObject();
        right = (Branch) in.readObject();
    }
}
