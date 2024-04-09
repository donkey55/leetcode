package lsp.practice;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(456);
        Iterator<Integer> iterator = res.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            res.add(123);
            System.out.println(iterator.next());
        }
    }
}