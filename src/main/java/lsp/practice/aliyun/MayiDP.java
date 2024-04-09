package lsp.practice.aliyun;

public class MayiDP {
    public static void main(String[] args) {
        int n = 10;
        int len = 4;
        int[] ab = new int[]{1,2,3,4};
        int[][] dp = new int[len + 1][n + 1];
        int abMin = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            abMin = Math.min(abMin, ab[i]);
        }
        for (int i = 0; i <= n; i++) {
            dp[1][i] = abMin * i * i;
        }
        for (int i = 2; i <= len; i++) {
            for (int j = 1; j <= n; j++ ) {
                System.out.println("=====================");
                System.out.println("i: " + i);
                System.out.println("j: " + j);
                System.out.println("=====================");
                int min = Integer.MAX_VALUE;
                for (int k = 0; k <= j; k++) {
                    min = Math.min(Math.max(dp[i - 1][j - k], ab[i - 1] * k * k), min);
                    System.out.println(min);
                }

                dp[i][j] = min;
            }
        }
        System.out.println("reuslt");
        System.out.println(dp[len][n]);
    }
}
