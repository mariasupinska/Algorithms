import java.util.*;

public class MyGraph {
    private int vertices;
    private List<List<Node>> adj_list = new ArrayList<>();
    private Map<Integer, Set<Edge>> edgesPerWeight = new TreeMap<>();

    public MyGraph(int v, List<Edge> edges) {
        vertices = v;
        for ( int i = 0; i <= v; i++ )
            adj_list.add(i, new ArrayList<>());

        for (Edge edge: edges) {
            adj_list.get(edge.getBeg()).add(new Node(edge.getEnd(), edge.getWeight()));
            edgesPerWeight.putIfAbsent(edge.getWeight(), new TreeSet<>(Comparator.comparing(Edge::getBeg).thenComparing(Edge::getEnd)));
            edgesPerWeight.get(edge.getWeight()).add(edge);
        }

    }

    public List<Edge> kruskal() {
        int[] v = new int[vertices+1];
        for ( int i = 1; i <= vertices; i++ )
            v[i] = i;

        List<Edge> result = new ArrayList<>();

        for (Map.Entry<Integer, Set<Edge>> integerSetEntry : edgesPerWeight.entrySet()) {
            for (Edge edge : integerSetEntry.getValue()) {
                if ( v[edge.getEnd()] > v[edge.getBeg()] ) {
                    for ( int i = 1; i <= vertices; i++ ) {
                        if ( v[i] == edge.getBeg() ) v[i] = edge.getEnd();
                    }
                    result.add(edge);
                    if ( result.size() == (vertices-1) ) return result;
                }
            }
        }
        return result;
    }
}
