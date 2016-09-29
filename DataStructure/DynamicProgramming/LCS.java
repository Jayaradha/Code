package dynamicprogramming;

/**
 * Created by Jayaradha on 9/27/16.
 * Reference :https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/LongestCommonSubsequence.java
 * Question
 * TOPIC - Dynamic programming
 * Given two sequences, find the longest subsequence present in both of them
 */
public class LCS {


    public String lcsDynamicstr(String strX, String strY)
    {
        char str1[] = strX.toCharArray();
        char str2[] = strY.toCharArray();
        int temp[][] = new int[str1.length + 1][str2.length + 1];
        int max = 0;
        StringBuilder b = new StringBuilder();
        for(int i=1; i < temp.length; i++){
            for(int j=1; j < temp[i].length; j++){
                if(str1[i-1] == str2[j-1]) {
                    b.append(str1[i-1]);
                    temp[i][j] = temp[i - 1][j - 1] + 1;
                }
                else
                {
                    temp[i][j] = Math.max(temp[i][j-1],temp[i-1][j]);

                }
                if(temp[i][j] > max){
                    max = temp[i][j];
                }
            }
        }
        return b.toString();
    }


    public static void main(String[] args)
    {
        LCS l = new LCS();
        String str1 = "ABCDGHLQR";
        String str2 = "AEDPHR";
        String str = l.lcsDynamicstr(str1,str2);
        System.out.println("Length of the Common Sequence = " + str.length());
        System.out.println("Common Sequence = " + str);
    }

}
