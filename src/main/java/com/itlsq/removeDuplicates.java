package com.itlsq;

//删除子序列中连续三个相同的字母(注意删除后可能会因为拼接而出现相同的)
public class removeDuplicates {

    public static void main(String[] args) {
        removeDuplicates solver = new removeDuplicates();

        String input = "cabbbaa";
        int k = 3;

        String result = solver.removeDuplicates(input, k);

        System.out.println("输入: " + input + ", k=" + k);
        System.out.println("输出: " + result);
    }

    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int[] count = new int[s.length()];

        for (char c : s.toCharArray()) {
            sb.append(c);
            int lastIndex = sb.length() - 1;

            if (lastIndex > 0 && sb.charAt(lastIndex) == sb.charAt(lastIndex - 1)) {
                count[lastIndex] = count[lastIndex - 1] + 1;
            } else {
                count[lastIndex] = 1;
            }

            if (count[lastIndex] == k) {
                sb.delete(lastIndex - k + 1, lastIndex + 1);
            }
        }
        return sb.toString();
    }
}