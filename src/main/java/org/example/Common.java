package org.example;

import org.example.linklist.ListNode;
import org.example.tree.TreeNode;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Common {
    public static void main(String[] args) {
        new Common().uniquePaths(3, 7);
    }

    public void sort() {
        int[] kk = new int[]{6,2,4,5,7,10, 12, 20};
        quicklySort(kk, 0, kk.length);
        System.out.println(Arrays.toString(kk));
    }
    public void quicklySort(int[] nums, int left, int right) {
       if (left < right) {
           int i = split(nums, left, right);
           quicklySort(nums, left, i - 1);
           quicklySort(nums, i + 1, right);
       }
    }

    public int split(int[] nums, int left, int right) {
        int val = nums[left];
        int j = left;
        for (int i = left + 1; i < right; i++) {
            if (nums[i] < val) {
                j++;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        int temp = nums[j];
        nums[j] = nums[left];
        nums[left] = temp;
        return j;
    }
    int maxLen = -1;
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getLen(root);
    }

    public int getLen(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left1 = getLen(root.left);
        int right1 = getLen(root.right);
        int temp = left1 + right1 + 2;
        if (temp > maxLen) {
            maxLen = temp;
        }
        return Math.max(left1, right1) + 1;
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> tempList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                tempList.add(poll.val);
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            res.add(tempList);
        }
        return res;
    }
    Map<Integer, Integer> map = new HashMap<>();

    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 1; i < n; i++) {
            dp[i + 1] = Math.max(dp[i] + 1, dp[i - 1] + 1);
        }
        return dp[n];
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= cost.length; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[cost.length];
    }

    public int rob(int[] nums) {
        int[][] dp = new int[nums.length + 1][2];
        dp[0][0] = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i + 1][1] = Math.max(dp[i][0] + nums[i], dp[i][1]);
            dp[i + 1][0] = Math.max(Math.max(dp[i][1], dp[i][0]), dp[i][1]);
        }
        return Math.max(dp[nums.length][0], dp[nums.length][1]);
    }

    public int deleteAndEarn(int[] nums) {
        int maxVal = -1;
        for (int num : nums) {
            maxVal = Math.max(num, maxVal);
        }
        int[] sum1 = new int[maxVal + 1];
        for (int i = 0; i < nums.length; i++) {
            sum1[nums[i]] += nums[i];
        }
        return rob(sum1);
    }
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        System.out.println(Arrays.deepToString((dp)));
        return dp[m - 1][n - 1];
    }
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[m - 1][n - 1];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[0][i] == 1) {
                break;
            }
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[i] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public int findJudge(int n, int[][] trust) {
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        if (trust.length == 0 && n == 1) {
            return 1;
        }
        for (int i = 0; i < trust.length; i++) {
            map.put(trust[i][1], map.getOrDefault(trust[i][1], 0) + 1);
            map2.put(trust[i][0], map.getOrDefault(trust[i][0], 0) + 1);
        }
        for (Integer index : map.keySet()) {
            if (map.get(index) == n - 1 && map2.get(index) == null) {
                return index;
            }
        }
        return -1;
    }

}