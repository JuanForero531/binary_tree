package p9;

import javax.swing.*;


class Node {
    int value;
    Node left;
    Node right;
    int height;

    Node(int value) {
        this.value = value;
        this.height = 1;
    }
}

public class AVLTree {
     Node root;

 

    public Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        
        node.height = 1 + Math.max(height(node.left), height(node.right));

        
        int balance = getBalance(node);

        
        if (balance > 1 && value < node.left.value) {
            return rotateRight(node);
        }
        if (balance < -1 && value > node.right.value) {
            return rotateLeft(node);
        }
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    @Override
    public String toString() {
        return toStringHelper(root, "");
    }

    private String toStringHelper(Node node, String prefix) {
        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(toStringHelper(node.right, prefix + "  R->"));
        sb.append(prefix).append(node.value).append(" (h=").append(node.height).append(")").append("\n");
        sb.append(toStringHelper(node.left, prefix + "  L->"));

        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Arbol AVL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AVLTree_View avlTreeGUI = new AVLTree_View();
        frame.getContentPane().add(avlTreeGUI);

        frame.pack();
        frame.setVisible(true);
    }
}

