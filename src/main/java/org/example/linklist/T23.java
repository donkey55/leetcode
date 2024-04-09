package org.example.linklist;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并k个升序列表
 * @Datetime: 2024/4/5 16:40
 * @Author: koopkl
 * @Link: <a href="https://leetcode.cn/problems/merge-k-sorted-lists/description/">合并k个升序列表</a>
 */
public class T23 {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        for (ListNode node : lists) {
            if (node != null) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            ListNode poll = queue.poll();
            if (poll.next != null) {
                queue.add(poll.next);
            }
            temp.next = poll;
            temp = temp.next;
        }
        return dummy.next;

    }
}
