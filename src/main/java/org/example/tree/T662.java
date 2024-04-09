package org.example.tree;

import java.util.*;

public class T662 {
    public int widthOfBinaryTree(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = 1;
        int dep = 1;
        Map<TreeNode, Integer> map = new HashMap<>();
        map.put(root, 1);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int begin = 0;
            int end = 0;
            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.poll();
                int index = map.get(treeNode);
                if (i == 0) {
                    begin = map.get(treeNode);
                }
                if (i == size - 1) {
                    end = map.get(treeNode);
                }
                if (treeNode == null) {
                    continue;
                }
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                    map.put(treeNode.left, index << 1);
                }
                if (treeNode.right != null) {
                    map.put(treeNode.right, (index << 1) | 1);
                    queue.offer(treeNode.right);
                }
            }
            res = Math.max(res, end - begin + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode9 = new TreeNode(9);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode1 = new TreeNode(1);
        treeNode1.left = treeNode4;
        treeNode1.right = treeNode2;
        treeNode4.left = treeNode5;
        treeNode4.right = treeNode3;
        treeNode2.right = treeNode9;
        System.out.println(new T662().widthOfBinaryTree(treeNode1));
    }
}
