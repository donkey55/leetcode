package org.example;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        String str = in.next();
//        int[] kk = new int[n];
//        for (int i = 0; i < n; i++) {
//            kk[i] = str.charAt(i) - 'A';
//        }
//        int[] dp = new int[n + 1];
//        for (int i = 0; i < n; i++) {
//            dp[i + 1] = dp[i] + kk[i];
//        }
//        int res = 0;
//        for (int i = 2; i <= kk.length; i += 2) {
//            for (int j = 0; j <= kk.length - i; j++) {
//                int left = j;
//                int right = j + i - 1;
//                int sum = -1;
//                boolean flag = false;
//                if (i == 2) {
//                    if (kk[j] + kk[j + 1] == 1) {
//                        res = Math.max(res, i);
//                    }
//                    continue;
//                }
//                if (j + 1 < n &&  right - 1 >= 0 && getBool(left + 1, right - 1, dp)) {
//                    sum = kk[left] + kk[right];
//                    if (sum == 1) {
//                        flag = true;
//                    }
//                } else if (j + 1 < n &&  right - 1 >= 0 && ! getBool(left + 1, right - 1, dp)) {
//                    sum = kk[left] + kk[right];
//                    if (sum + getSum(left + 1, right - 1, dp) == i / 2) {
//                        flag = true;
//                    }
//                } else if (right - 2 > 0 &&  getBool(left , right - 2, dp)) {
//                    sum = kk[right - 1] + kk[right];
//                    if (sum == 1) {
//                        flag = true;
//                    }
//                } else if (right - 2 > 0 && !getBool(left , right - 2, dp)) {
//                    sum = kk[right - 1] + kk[right];
//                    if (sum + getSum(right - 1, right, dp)== i / 2) {
//                       flag = true;
//                    }
//                } else if (left + 2 < n && getBool(left + 2 , right, dp)) {
//                    sum = kk[left] + kk[left + 1];
//                    if (sum == 1) {
//                        flag = true;
//                    }
//                } else if (left + 2 < n && !getBool(left + 2 , right, dp)) {
//                    if (kk[left] + kk[left + 1] +getSum(left + 2, right, dp) == i / 2) {
//                        flag = true;
//                    }
//                }
//                if (flag) {
//                    res = Math.max(res, i);
//                }
//            }
//        }
//        System.out.println(res);
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        System.out.println((a + b) % c);
        System.out.println(((a % c) + (b % c)) % c);
        System.out.println((a * b) % c);
        System.out.println(((a % c) * (b % c)) % c);
    }

    public static int getSum(int left, int right, int[] sum) {
        return sum[right + 1] - sum[left];
    }

    public static boolean getBool(int left, int right, int[] sum) {
        if (left == right) {
            return true;
        }
        return (sum[right + 1] - sum[left]) == (right - left + 1) / 2;
    }

    public void common2() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int mod = (int) Math.pow(10, 9) + 7;
        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            int sum = 0;
            int max = 0;
            int allSum = 0;
            for (int j = 0; j < n; j++) {
                int mm = sc.nextInt();
                if (mm >= 0) {
                    sum += mm;
                } else {
                    max = Math.max(sum, max);
                    sum = 0;
                }
                allSum = (allSum % mod + mm % mod) % mod;
            }
            max = Math.max(sum, max);
            if (max == 0) {
                System.out.println(allSum);
            } else {
                System.out.println(((allSum % mod) + ((max * k)) % mod) % mod);
            }
        }
    }

}