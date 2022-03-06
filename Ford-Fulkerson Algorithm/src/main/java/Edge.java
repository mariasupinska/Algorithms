public class Edge {
    int beg;
    int end;
    int thruput;
    int flow;

    public Edge(int b, int e, int w) {
        beg = b;
        end = e;
        thruput = w;
        flow = 0;
    }

    @Override
    public String toString() {
        return beg +
                " " + end +
                " [" + thruput + "]";
               // + " [" + flow + "]";
    }

    public boolean isIncoming(Integer v) {
        return end == v;
    }

    public boolean isOutgoing(Integer v) {
        return beg == v;
    }
}
