import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        zadanie3();
    }

    private static void zadanie3() throws IOException {
        File file = new File("src/main/resources/In0303.txt");
        Scanner scan = new Scanner(file);
        int n = scan.nextInt();
        scan.nextLine();

        List<Edge> edges = new ArrayList<>();

        for ( int i = 1; i <= n; i++ ) {
            String line = scan.nextLine();
            List<String> list = new ArrayList<>(Arrays.asList(line.split(" ")));

            int j = 0;
            while( (j+1) <= list.size()-1 ) {
                edges.add(new Edge(i, Integer.parseInt(list.get(j)), Integer.parseInt(list.get(j+1))));
                j += 2;
            }
        }

        MyGraph graph = new MyGraph(n, edges);
        List<Edge> result = graph.kruskal();

        FileWriter fileWriter = new FileWriter("src/main/resources/Out0303.txt");

        String outcome = result.toString() + '\n';
        fileWriter.write(outcome);
        fileWriter.flush();

        int resultWeight = 0;
        for (Edge edge : result)
            resultWeight += edge.getWeight();

        outcome = String.valueOf(resultWeight);
        fileWriter.write(outcome);
        fileWriter.flush();

    }
}
