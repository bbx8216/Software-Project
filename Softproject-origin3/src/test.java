public class test {
    public static void main(String[] args) {
        String[] sList = new String[2];
        int sum;
        int[][] arr = { {1, 2}, {3, 4, 5}, {6} } ;
// 1)
        sum = sub2021(arr, sList); // 2)
    }
    static int sub2021(int[][] iA, String[] sA) { int[][][] a3 = new int[3][2][2];
        a3[0] = iA;
        sA[0] = "hi";

        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<2 ; j++){
                for(int k=0 ; k<2 ; k++){
                    System.out.printf("%d",a3[i][j][k]);
                }
                System.out.println("");
            }
        }
// 3)
        return a3[0][1][1]; }
}