public class Edge implements Comparable<Edge> {
    private int beg;
    private int end;
    private int weight;

    public Edge(int b, int e, int w) {
        beg = b;
        end = e;
        weight = w;
    }

    @Override
    public String toString() {
        return beg +
                " " + end +
                " [" + weight +
                "]";
    }

    @Override
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }

    public int getBeg() {
        return beg;
    }

    public int getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }
}
