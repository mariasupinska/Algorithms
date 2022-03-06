import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        zadanie2();
    }

    private static void zadanie2() throws IOException {
        File file = new File("src/main/resources/In0302.txt");
        Scanner scan = new Scanner(file);
        int n = scan.nextInt();
        int W = scan.nextInt();

        int[] p = new int[n];
        int[] w = new int[n];

        for ( int i = 0; i < n; i++ ) {
            p[i] = scan.nextInt();
            w[i] = scan.nextInt();
        }

        int pom;
        int[][] s1 = new int[W+1][n+1];
        int[][] s2 = new int[W+1][n+1];

        for ( int i = 0; i <= W; i++ ) {
            for ( int j = 0; j <= n;j++ ) {
                s1[i][j] = s2[i][j] = 0;
            }
        }

        for ( int i = 1; i <= W; i++ ) {
            for ( int j = 1; j <= n; j++ ) {

                if ( w[j-1] <= i) {                         //sprawdzenie czy udźwig danej rzeczy jest dozwolony
                    pom = s1[i - w[j-1]][j-1] + p[j-1];     //dodanie do ceny rozważanego przedmiotu (waga przedmiotu w górę, raz w lewo)

                    if ( pom < s1[i][j-1] ) {               //sprawdzenie czy warto wziąć przedmiot do plecka czy lepiej wziąć stary plecak
                        s1[i][j] = s1[i][j-1];              //wersja gdy się nie opłaca

                    } else {
                        s1[i][j] = pom;                     //wersja gdy się opłaca
                        s2[i][j] = 1;                       //zaznaczenie że nowy plecak jest bardziej opłacalny

                    }
                } else {                                    //udźwig przekroczony
                    s1[i][j] = s1[i][j-1];                  //wzięty jest stary plecak
                }
            }
        }

        FileWriter fileWriter = new FileWriter("src/main/resources/Out0302.txt");

        int max = s1[W][n];
        int j = n;
        while ( s1[W][j] == max ) {
            ArrayList<Integer> result = new ArrayList<>();
            pom = W;

            for (int i = j; i >= 0; i--) {
                if (s2[pom][i] == 1) {
                    pom -= w[i - 1];
                    result.add(i);
                }
            }

            String outcome = result.toString() + '\n';
            fileWriter.write(outcome);
            fileWriter.flush();

            j--;
        }
    }
}
