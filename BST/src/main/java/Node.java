public class Node implements Comparable<Node> {
    Comparable value;
    Node left;
    Node right;
    Node translation;

    public Node(Comparable value) {
        this.value = value;
        right = null;
        left = null;
        translation = null;
    }

    @Override
    public int compareTo(Node o) {
        if ( o.value instanceof String ) {
            String current = this.value.toString();
            String value = o.value.toString();
            return current.compareToIgnoreCase(value);
        }
        return this.value.compareTo(o.value);
    }

}
