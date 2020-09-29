package Tree;

import com.company.Pair;

import java.util.*;

public class ShannonFanoTreeCreator implements TreeCreator {
    @Override
    public Tree createTree(Map<String, Integer> map) {
        return new Tree(split(map));
    }

    private Branch split(Map<String, Integer> counts){
        if (counts.size() == 1)
            return new Leaf((new LinkedList(counts.keySet())).get(0).toString());

        Pair<Map<String, Integer>, Map<String, Integer>> pair = splitCounts(counts);

        return new Fork(split(pair.getLeft()), split(pair.getRight()),
                new LinkedList<>(counts.keySet()));
    }

    private Pair<Map<String, Integer>, Map<String, Integer>> splitCounts(Map<String, Integer> counts){
        Map<String, Integer> left = new HashMap<>();
        Map<String, Integer> right = new HashMap<>();

        // Sorting map in descending order
        List<Map.Entry<String, Integer>> list = new LinkedList<>(counts.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        for (Map.Entry<String, Integer> entry: list){
            if (sumCounts(left) < sumCounts(right) || sumCounts(right) >= sumCounts(counts)/2)
                left.put(entry.getKey(), entry.getValue());
            else
                right.put(entry.getKey(), entry.getValue());
        }

        return new Pair<>(left, right);
    }

    private int sumCounts(Map<String, Integer> counts){
        List<Map.Entry<String, Integer>> list = new LinkedList<>(counts.entrySet());
        int summ = 0;

        for(Map.Entry<String, Integer> entry: list)
            summ += entry.getValue();

        return summ;
    }
}