package Tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HuffmanTreeCreator implements TreeCreator {

    public HuffmanTreeCreator(){}

    @Override
    public Tree createTree(Map<String, Integer> map) {
        List<Branch> list = mapToList(map);

        while (list.size() != 1){

            sort(list, map);

            List<String> value = new LinkedList<>(list.get(0).getValue());
            value.addAll(list.get(1).getValue());

            Branch fork = new Fork(list.remove(0), list.remove(0), value);

            list.add(fork);
        }

        return new Tree(list.get(0));
    }

    private List<Branch> mapToList(Map<String, Integer> map){
        List<Map.Entry<String, Integer>> l = new LinkedList<>(map.entrySet());

        List<Branch> list = new LinkedList<>();

        for(Map.Entry<String, Integer> entry: l)
            list.add(new Leaf(entry.getKey()));

        return list;
    }

    private void sort(List<Branch> list, Map<String, Integer> map){
        list.sort(new Comparator<Branch>() {
            @Override
            public int compare(Branch o1, Branch o2) {
                if (sumCounts(o1, map) > sumCounts(o2, map))
                    return 1;
                else if (sumCounts(o1, map) < sumCounts(o2, map))
                    return -1;

                return 0;
            }
        });
    }

    private int sumCounts(Branch branch, Map<String, Integer> map){
        int summ = 0;
        List<String> list = branch.getValue();

        for (String string: list)
            summ += map.get(string);

        return summ;
    }
}
