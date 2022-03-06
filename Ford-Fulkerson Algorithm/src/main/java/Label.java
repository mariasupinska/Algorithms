public class Label {
    Integer parent;
    Integer flow;

    public Label() {
        parent = 0;
        flow = 0;
    }

    public Label(Integer p, Integer f) {
        parent = p;
        flow = f;
    }
}
