import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        zadanie2();
    }

    private static void zadanie2() throws IOException {
        File file = new File("src/main/resources/In0402.txt");
        Scanner scan = new Scanner(file);
        int n = scan.nextInt();
        int s = scan.nextInt();
        int t = scan.nextInt();
        scan.nextLine();

        List<Edge> edges = new ArrayList<>();

        for ( int i = 1; i <= n-1; i++ ) {
            String line = scan.nextLine();
            List<String> list = new ArrayList<>(Arrays.asList(line.split(" ")));

            int j = 0;
            while( (j+1) <= list.size()-1 ) {
                edges.add(new Edge(i, Integer.parseInt(list.get(j)), Integer.parseInt(list.get(j+1))));
                j += 2;
            }
        }

        MyGraph graph = new MyGraph(n, s, t, edges);

        FileWriter fileWriter = new FileWriter("src/main/resources/Out0402.txt");

        FordFulkerson(graph, fileWriter, edges);

        writeAdjList(n, graph, fileWriter);

        calculateFinalFlow(edges, graph, fileWriter);
    }

    public static void FordFulkerson(MyGraph graph, FileWriter fileWriter, List<Edge> edges) throws IOException {
        Stack<Integer> labelStack = new Stack<>();
        graph.nodes.get(graph.source).label = new Label(null, Integer.MAX_VALUE);
        labelStack.push(graph.source);
        writeOutcome(fileWriter, labelStack);

        while( !labelStack.empty() ) {
            int v = labelStack.pop();
            Node parent = graph.nodes.get(v);

            for ( Edge e : parent.edges ) {
                Node neighbour = graph.nodes.get(e.end);
                if (parent == graph.nodes.get(e.end)) {
                    neighbour = graph.nodes.get(e.beg);
                }
                if ( neighbour.isWithoutLabel() ) {
                    if ( e.isIncoming(neighbour.value) && (e.thruput-e.flow > 0) ) {
                        neighbour.label = new Label(parent.value, Math.min(parent.label.flow, (e.thruput-e.flow)));
                    } else {
                        if ( e.isOutgoing(neighbour.value) && e.flow > 0 ) {
                            neighbour.label = new Label(parent.value, Math.min(parent.label.flow, e.flow));
                        }
                    }

                    if ( !neighbour.isWithoutLabel() ) {
                        if ( neighbour.value == graph.outlet ) {
                            extendPath(graph, backtrackEdges(graph), fileWriter);

                            graph.nodes.values().stream()
                                    .filter(node -> node.value != graph.source)
                                    .forEach(node -> node.label = null);
                            labelStack.clear();
                            labelStack.push(graph.source);
                            fileWriter.write('\n');
                            fileWriter.flush();
                            writeOutcome(fileWriter, labelStack);
                            break;
                        } else {
                            labelStack.push(neighbour.value);
                            writeOutcome(fileWriter, labelStack);
                        }
                    }
                }
            }
        }
    }

    private static List<Edge> backtrackEdges(MyGraph graph) {
        List<Edge> pathEdges = new ArrayList<>();
        Node current = graph.nodes.get(graph.outlet);
        while(current.value != graph.source) {
            Integer parent = current.label.parent;
            Edge path = current.edges.stream().filter(edge -> edge.beg == parent || edge.end == parent)
                    .findFirst().get();
            pathEdges.add(path);
            current = graph.nodes.get(current.label.parent);
        }

        Collections.reverse(pathEdges);
        return pathEdges;
    }

    private static void extendPath(MyGraph graph, List<Edge> pathEdges, FileWriter fileWriter) throws IOException {
        Node outlet = graph.nodes.get(graph.outlet);
        Integer outletFlow = outlet.label.flow;
        Edge prev = null;
        LinkedHashSet<Integer> path = new LinkedHashSet<>();

        for ( int i = pathEdges.size()-1; i >= 0; i-- ) {
            Edge edge = pathEdges.get(i);
            path.add(edge.end);
            path.add(edge.beg);
            if ( prev != null ) {
                if ( prev.beg == edge.end ) {
                    edge.flow += outletFlow;    //obie przednie
                } else if ( prev.end == edge.beg ) {
                    edge.flow -= outletFlow;    //obie wsteczne
                } else if ( prev.beg == edge.beg ) {
                    edge.flow -= outletFlow;    //rozbieżne
                } else if ( prev.end == edge.end ) {
                    edge.flow += outletFlow;    //zbieżne
                }
            } else {
                if ( edge.isIncoming(edge.end) ) {
                    edge.flow += outletFlow;
                } else {
                    edge.flow -= outletFlow;
                }
            }
            prev = edge;
        }

        String outcome = '\n' + path.toString() + '\n';
        fileWriter.write(outcome);
        fileWriter.flush();
        pathEdges.clear();
    }

    private static void writeOutcome(FileWriter fileWriter, Stack<Integer> labelStack) throws IOException {
        String outcome = labelStack.peek() + " ";
        fileWriter.write(outcome);
        fileWriter.flush();
    }

    private static void writeAdjList(int n, MyGraph graph, FileWriter fileWriter) throws IOException {
        fileWriter.write('\n');
        fileWriter.flush();
        for (int i = 1; i <= n; i++ ) {
            fileWriter.write("[" + i + "]" + " ");
            Node current = graph.nodes.get(i);

            for ( Edge e : current.edges ) {
                if ( e.isOutgoing(current.value) ) {
                    fileWriter.write(e.end + " " + e.flow + ", ");
                }
            }
            fileWriter.write('\n');
            fileWriter.flush();
        }
    }

    private static void calculateFinalFlow(List<Edge> edges, MyGraph graph, FileWriter fileWriter) throws IOException {
        List<Integer> labeled = new ArrayList<>();
        List<Integer> notLabeled = new ArrayList<>();

        for (int i = 1; i <= graph.vertices; i++ ) {
            Node vertex = graph.nodes.get(i);
            if ( vertex.label != null ) {
                labeled.add(vertex.value);
            } else {
                notLabeled.add(vertex.value);
            }
        }

        int finalFlow = 0;
        for (Integer v1 : labeled) {
            for (Integer v2 : notLabeled) {
                for (Edge e : edges) {
                    if ( (e.beg == v1 && e.end == v2) || (e.beg == v2 && e.end == v1) ) {
                        finalFlow += e.flow;
                    }
                }
            }
        }

        fileWriter.write(String.valueOf(finalFlow));
        fileWriter.flush();
    }
}
