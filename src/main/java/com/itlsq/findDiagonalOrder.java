package com.itlsq;

import java.util.Arrays;

//对角线遍历:给你一个大小为 m x n 的矩阵 mat ，
//请以对角线遍历的顺序，用一个数组返回这个矩阵中的所有元素(模拟原题)。
//leetcode:498
public class findDiagonalOrder {
    public static void main(String[] args) {
        findDiagonalOrder solver = new findDiagonalOrder();
        int[][] mat = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[] result = solver.findDiagonalOrder(mat);

        System.out.println("输入矩阵:");
        for (int[] row : mat) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("输出对角线遍历结果: " + Arrays.toString(result));
    }

    public int[] findDiagonalOrder(int[][] mat) {
        int m=mat.length;
        int n=mat[0].length;
        int[] ans=new int[m*n];
        int idx=0;
        for(int k=0;k<m+n-1;k++){
            int minJ=Math.max(k-m+1,0);
            int maxJ=Math.min(k,n-1);
            if(k%2==0){
                for(int j=minJ;j<=maxJ;j++){
                    ans[idx++]=mat[k-j][j];
                }
            }else{
                for(int j=maxJ;j>=minJ;j--){
                    ans[idx++]=mat[k-j][j];
                }
            }
        }
        return ans;
    }
}
