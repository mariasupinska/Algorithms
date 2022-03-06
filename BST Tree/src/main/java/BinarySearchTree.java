import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class BinarySearchTree {
    Node root;

    private Node search(Node current, Node value) {
        if ( current == null ) {
            return null;
        }
        if ( value.compareTo(current) == 0 ) {
            return current;
        }
        return (value.compareTo(current)) < 0
                ? search(current.left, value)
                : search(current.right, value);
    }

    public Node search(Node value) {
        return search(root, value);
    }

    private Node insert(Node current, Node value) {
        if ( current == null ) {
            try {
                write(value.value + " " + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
            return value;
        }

        if ( (value.compareTo(current)) < 0 ) {
            current.left = insert(current.left, value);
        } else if ( (value.compareTo(current)) > 0 ) {
            current.right = insert(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    public void insert(Node value) {
        root = insert(root, value);
    }

    private void write(final String s) throws IOException {
        Files.write(
                Paths.get("src/main/resources/OutA0502.txt"),
                s.getBytes(StandardCharsets.UTF_8),
                CREATE, APPEND
        );
    }

    private Node delete(Node current, Comparable value) {
        if ( current == null ) {
            return null;
        }

        if ( value.compareTo(current.value) == 0 ) {
            // No leaves
            if ( current.left == null && current.right == null ) {
                return null;
            }

            // One leaf
            if ( current.right == null ) {
                return current.left;
            }
            if ( current.left == null ) {
                return current.right;
            }

            // Two leaves
            Node replacementNode = findReplacementNode(current.right);
            current.value = replacementNode.value;
            current.translation = replacementNode.translation;
            current.right = delete(current.right, replacementNode.value);
            return current;
        }

        // jest robiony bo był problem z porównywaniem Stringów - wielkość liter
        Node valueNode = new Node(value);

        if ( (valueNode.compareTo(current)) < 0 ) {
            current.left = delete(current.left, value);
            return current;
        } else {
            current.right = delete(current.right, value);
            return current;
        }
    }

    public void delete(Comparable value) {
        root = delete(root, value);
    }

    private Node findReplacementNode(Node root) {
        return root.left == null ? root : findReplacementNode(root.left);
    }

    private String toKLPString(Node current) {
        if ( current == null ) return null;
        List<String> nodes = new ArrayList<>();
        nodes.add(String.valueOf(current.value));
        nodes.add(toKLPString(current.left));
        nodes.add(toKLPString(current.right));
        return nodes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

    public String toKLPString() {
        return toKLPString(root);
    }

    private String toKLPStringWithTranslation(Node current) {
        if ( current == null ) return null;
        List<String> nodes = new ArrayList<>();
        nodes.add(current.value + " " + current.translation.value + '\n');
        int d = 2;
        nodes.add(toKLPStringWithTranslation(current.left));
        nodes.add(toKLPStringWithTranslation(current.right));
        return nodes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(""));
    }

    public String toKLPStringWithTranslation() {
        return toKLPStringWithTranslation(root);
    }
}
