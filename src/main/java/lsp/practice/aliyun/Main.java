package lsp.practice.aliyun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 注意类名必须为 MayiDP, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String s = in.next();
        countSubstrings("011");
        List<Integer> res = new ArrayList<>();
    }

    public static void countSubstrings(String s) {
        int res1 = 0;
        int res2 = 0;
        for (int center = 0; center < s.length() * 2 - 1; center++) {
            int left = center / 2;
            int right = left + center % 2;
            while (left >= 0 && right < s.length()) {
                if (s.charAt(right) != s.charAt(left)) {
                    break;
                }
                char c = s.charAt(left);
                while (left >= 0 && s.charAt(left) == c) {
                    int len = right - left + 1;
                    if (len % 2 == 0) {
                        res1++;
                    } else {
                        res2++;
                    }
                    left--;
                }
                while (right < s.length() && c == s.charAt(right)) {
                    int len = right - left + 1;
                    if (len % 2 == 0) {
                        res1++;
                    } else {
                        res2++;
                    }
                    right++;
                }
            }
        }
        System.out.println(res1 + " " + res2);

    }


}