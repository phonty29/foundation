public class LCS {
    public static void main(String[] args) {
        String s1 = args[0];
        String s2 = args[1];
        int M = s1.length();
        int N = s2.length();
        int[][] opt = new int[M+1][N+1];
        for (int i=M-1; i>=0; i--)
            for (int j=N-1; j>=0; j--)
                if (s1.charAt(i) == s2.charAt(j))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
    }
}
