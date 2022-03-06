import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {
    private final int vertices;
    private final LinkedList<Integer>[] neighbours;

    public Graph(int v) {
        vertices = v;
        neighbours = new LinkedList[v+1];
        for ( int i = 1; i <= v; i++ )
            neighbours[i] = new LinkedList<>();
    }

    void addEdge (int v, int v2) {
        neighbours[v].add(v2);
    }

    void startDFS(int v, FileWriter fileWriter) throws IOException {
        boolean[] visited = new boolean[vertices+1];
        visited[0] = true;

        DFS(v, visited, fileWriter);

        checkIfConnected(visited, fileWriter);
    }

    private void DFS (int v, boolean[] visited, FileWriter fileWriter) throws IOException {
        visited[v] = true;
        Main.writeOutcome(fileWriter, v + " ");

        Iterator<Integer> i = neighbours[v].listIterator();
        while ( i.hasNext() ) {
            int n = i.next();
            if ( !visited[n] ) DFS(n, visited, fileWriter);
        }
    }

    private void checkIfConnected (boolean[] visited, FileWriter fileWriter) throws IOException {
        for ( int i = 1; i <= vertices; i++ ) {
            if ( !visited[i] ) {
                Main.writeOutcome(fileWriter, '\n' + "Graf niespójny");
                return;
            }
        }

        Main.writeOutcome(fileWriter, '\n' + "Graf spójny");
    }
}
