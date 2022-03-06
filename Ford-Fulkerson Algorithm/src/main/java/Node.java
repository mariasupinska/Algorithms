import java.util.ArrayList;
import java.util.List;

public class Node {
    int value;
    Label label;

    List<Edge> edges = new ArrayList<>();

    public Node(int v) {
        value = v;
    }

    public boolean isWithoutLabel() {
        return label == null;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
