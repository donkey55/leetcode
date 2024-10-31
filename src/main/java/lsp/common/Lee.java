package lsp.common;

import org.example.linklist.ListNode;
import org.example.linklist.Node;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class Lee {
    public boolean hasCycle(ListNode head) {
        while (head != null) {
            if (head.val == -1) {
                return true;
            }
            head.val = -1;
            head = head.next;
        }
        return false;
    }
    public ListNode detectCycle(ListNode head) {
        ListNode left = head;
        ListNode right = head;
        while (left != null && right != null) {
            left = left.next;
            right = right.next;
            if (right == null) {
                return null;
            }
            right = right.next;
            if (left == right) {
                break;
            }
        }
        right = head;
        while (right != left) {
            right = right.next;
            left = left.next;
        }
        return right;
    }
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode now1 = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                now1.next = list1;
                list1 = list1.next;
            } else {
                now1.next = list2;
                list2 = list2.next;
            }
            now1 = now1.next;
            now1.next = null;
        }
        now1.next = list1 == null ? list2 : list1;
        return dummy.next;
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int cnt = 0;
        ListNode res = new ListNode();
        ListNode temp = res;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + cnt;
            temp.next = new ListNode(sum % 10);
            cnt = sum / 10;
            temp = temp.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + cnt;
            temp.next = new ListNode(sum % 10);
            cnt = sum / 10;
            temp = temp.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int sum = l2.val + cnt;
            temp.next = new ListNode(sum % 10);
            cnt = sum / 10;
            temp = temp.next;
            l2 = l2.next;
        }
        if (cnt != 0) {
            temp.next = new ListNode(cnt);
            temp = temp.next;
        }
        return res.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode left = head;
        ListNode right = head;
        ListNode res = head;
        while (n-- > 0 && right != null) {
            right = right.next;
        }
        if (right == null) {
            return left.next;
        }
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }
        left.next = left.next.next;
        return res;
    }
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return tempSwap(head);
    }
    public ListNode tempSwap(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp1 = head.next;
        head.next = tempSwap(head.next.next);
        temp1.next = head;
        return temp1;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        int cnt = k;
        ListNode temp1 = head;
        while (temp1 != null && cnt > 0 ) {
            temp1 = temp1.next;
            cnt--;
        }
        if (temp1 == null) {
            return head;
        }
        return tempReverse(head, k);

    }

    public ListNode tempReverse(ListNode head, int k) {
        int cnt = k;
        ListNode temp1 = head;
        while (temp1 != null && cnt > 0 ) {
            temp1 = temp1.next;
            cnt--;
        }
        if (temp1 == null) {
            return head;
        }
        ListNode tempNode = reverseList(head, k);
        head.next = tempReverse(temp1, k);
        return tempNode;
    }
    public ListNode reverseList(ListNode head, int k) {
        if (k == 1) {
            head.next = null;
            return head;
        }
        ListNode temp = reverseList(head.next, k - 1);
        head.next.next = head.next;
        head.next = null;
        return temp;
    }

    Map<Node, Node> nodeMap = new HashMap<>();
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        if (!nodeMap.containsKey(head)) {
            Node node = new Node(head.val);
            nodeMap.put(head, node);
            node.next = copyRandomList(head.next);
            node.random = copyRandomList(head.random);
        }
        return nodeMap.get(head);
    }
    public ListNode sortList(ListNode head) {
        return mySort(head, null);
    }

    public ListNode mySort(ListNode left, ListNode right) {
        if (left == null) {
            return null;
        }
        if (left.next == right) {
            left.next = null;
            return left;
        }
        // 找到中点
        ListNode slow = left;
        ListNode fast = left;
        while (fast != right) {
            slow = slow.next;
            fast = fast.next;
            if (fast != right) {
                fast = fast.next;
            }
        }

        ListNode mid = slow;
        ListNode list1 = mySort(left, mid);
        ListNode list2 = mySort(mid, right);
        ListNode merged = mergeList(list1, list2);
        return merged;
    }

    public ListNode mergeList(ListNode lst1, ListNode lst2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = lst1, temp2 = lst2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
        ListNode res = new ListNode(-1);
        ListNode ptr = res;
        for (var temp : lists) {
            queue.add(temp);
        }
        while (!queue.isEmpty()) {
            ListNode peek = queue.poll();
            ptr.next = peek;
            if (peek.next != null) {
                queue.add(peek.next);
            }
            ptr = ptr.next;
        }
        return res.next;
    }
}
