package com.rhino.leetcode.hard;

public class HARD00329_Longest_Incresing_Path_In_a_Matrix {

    public static void main(String[] args) {
//        int[][] matrix = {{9,9,4},{6,6,8},{2,1,1}};
        int[][] matrix = {{3,4,5},{3,2,6},{2,2,1}};
        Solution s = new Solution();
        int result = s.longestIncreasingPath(matrix);
        System.out.println("Result = "+result);
    }
//     1차 시도. time limit 초과, matri를 간소화하는 솔루션이 필요함;
//     중복 경로 조회를 막기 위해 해설을 보고 캐시를 도입함.
//     물론 Node class가 없으면 그만큼 빨라지겠지만,
//     가시성을 위해 만들었음.
//     지금 생각하면 굳이 이러지 않고 그냥 메서드로 분리해도 됬었을듯 함.
    static class Solution {
        public int longestIncreasingPath(int[][] matrix) {
            // longest path algorithm;
            Node[][] nodeMatrix = new Node[matrix.length][];
            for(int i=0;i<matrix.length;i++){
                nodeMatrix[i] = new Node[matrix[0].length];
                for(int j=0;j<matrix[0].length;j++){
                    Node t = new Node();
                    if(i==0 || matrix[i-1][j] <= matrix[i][j] ){
                        t.canUp = false;
                    }
                    if(j==0 || matrix[i][j-1] <= matrix[i][j]){
                        t.canLeft = false;
                    }
                    if(i == matrix.length-1 || matrix[i+1][j] <= matrix[i][j]){
                        t.canDown = false;
                    }
                    if(j == matrix[0].length-1 || matrix[i][j+1] <= matrix[i][j]){
                        t.canRight = false;
                    }
                    nodeMatrix[i][j] = t;
                }
            }
            int result = 1;

            for (int i=0;i<matrix.length;i++){
                for(int j=0;j<matrix[0].length;j++){
                    int re = recursive(nodeMatrix,i,j,1);
                    if(result < re){
                        result = re;
                    }
                }
            }

            return result;
        }

        int recursive(Node[][] n,int i,int j,int count){
            // updownleftright;
            int t1=count,t2=count,t3=count,t4=count;
            // pain point. cache를 했어야 함.
            if(count <= n[i][j].max){
                return count; // // check!
            }else {
                n[i][j].max = count;
            }

            if(n[i][j].canUp){
                t1 = Math.max(count,recursive(n,i-1,j,count+1));
            }
            if(n[i][j].canDown){
                t2 = Math.max(count,recursive(n,i+1,j,count+1));
            }
            if(n[i][j].canLeft){
                t3 = Math.max(count,recursive(n,i,j-1,count+1));
            }
            if(n[i][j].canRight){
                t4 = Math.max(count,recursive(n,i,j+1,count+1));
            }
            return Math.max(Math.max(t1,t2),Math.max(t3,t4));
        }
    }

    static class Node{
        int max=0;
        Node(){
            canUp=true;canDown=true;canLeft=true;canRight=true;
        }
        boolean canUp,canDown,canLeft,canRight;
    }

}
