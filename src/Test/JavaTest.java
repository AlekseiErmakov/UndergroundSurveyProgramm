package Test;

import java.util.ArrayList;

public class JavaTest {

    double [][] mas = new double[11][9];
    int x;
    int i;int j;
    ArrayList<Integer> Man = new ArrayList<>();
    public JavaTest(){
        massivs();
    }
    public static void main(String[] args){
       new JavaTest();

    }
    public void massivs(){

       double first[]={1,2,344,5,6123121212.1212,123.1212131,12282.12626,21817267.1228,1299783.12};
       double sec[]={1,2,3,4,4,6,7,12828281,1123213.2130,11};




        x=1;
        for (i=0; i<9;i++){
            mas[0][i]= first[i];
            mas[1][i]= sec[i];


        }
        for (i=0; i<2; i++){
            System.out.println();
            for(j=0; j<9; j++){
                System.out.print(mas[i][j]+"\t");
            }
        }
    }
}1
