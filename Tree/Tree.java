package Tree;

import java.io.*;
import java.util.*;

public class Tree implements Externalizable {
    private Branch top;

    // Required for correct implementation of Externalizable interface
    public Tree(){}

    public Tree(Branch top){
        this.top = top;
    }

    public List<Boolean> encode(String string){
        List<Boolean> code = top.encode(string);

        if (code.size() == 0)
            code.add(true);

        return code;
    }

    public String decode(List<Boolean> list){
        return top.decode(list);
    }

    public Boolean contains(List<Boolean> list){
        return top.decode(list) != null;
    }

    public String toString(){
        return top.toString(new LinkedList<>());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(top);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        top = (Branch) in.readObject();
    }
}
