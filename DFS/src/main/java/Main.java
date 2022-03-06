import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        zadanie6();
    }

    private static void zadanie6() throws IOException {
        File file = new File("src/main/resources/In0206.txt");
        Scanner scan = new Scanner(file);

        int n = scan.nextInt();
        scan.nextLine();

        Graph graph = new Graph(n);

        for ( int i = 1; i <= n; i++ ) {
            String line = scan.nextLine();
            List<String> list = new LinkedList<>(Arrays.asList(line.split(" ")));
            for (String s : list) {
                int edge = Integer.parseInt(s);
                graph.addEdge(i, edge);
            }
        }

        FileWriter fileWriter = new FileWriter("src/main/resources/Out0206.txt");

        graph.startDFS(1, fileWriter);

    }

    public static void writeOutcome(FileWriter fileWriter, String outcome) throws IOException {
        fileWriter.write(outcome);
        fileWriter.flush();
    }
}
