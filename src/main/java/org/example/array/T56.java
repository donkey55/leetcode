package org.example.array;

import java.util.*;

/**
 * 合并区间
 * @Datetime: 2024/3/27 18:15
 * @Author: koopkl
 * @Link: <a href="https://leetcode.cn/problems/merge-intervals/description/">合并区间</a>
 */
public class T56 {
    public int[][] merge(int[][] intervals) {
        List<int[]> interList = new ArrayList<>(Arrays.asList(intervals));
        interList.sort((a, b) -> a[0]-b[0]);
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        System.out.println(Arrays.deepToString(intervals));
        int min = interList.getFirst()[0];
        int max = interList.getFirst()[1];
        List<int[]> res = new ArrayList<>();
        for (int i = 1; i < interList.size(); i++) {
            int[] temp = interList.get(i);
            if (temp[0] <= max && temp[1] > max) {
                max = temp[1];
            } else if (temp[0] > max) {
                res.add(new int[]{min, max});
                min = temp[0];
                max = temp[1];
            }
        }
        res.add(new int[]{min, max});
        return res.toArray(new int[res.size()][]);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(new T56().merge(new int[][]{{4, 3}, {2, 6}, {8, 10}, {15, 18}})));
    }
}
