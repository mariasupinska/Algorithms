import java.util.*;

public class MyGraph {
    int vertices;
    int source;
    int outlet;
    Map<Integer, Node> nodes = new HashMap<>();

    public MyGraph(int v, int s, int o, List<Edge> edges) {
        vertices = v;
        source = s;
        outlet = o;
        for ( int i = 1; i <= v; i++ ) {
            nodes.put(i, new Node(i));
        }

        for (Edge edge: edges) {
            nodes.get(edge.beg).edges.add(edge);
            nodes.get(edge.end).edges.add(edge);
        }

    }
}
