public class AVLTree<T extends Comparable<T>> {

    // -----------------------------
    // Nodo del AVL
    // -----------------------------
    private class Node {
        T key;
        Node left, right;
        int height;

        Node(T key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root;

    // -----------------------------
    // Utilidades internas
    // -----------------------------
    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // -----------------------------
    // Rotación simple derecha
    // -----------------------------
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // rotación
        x.right = y;
        y.left = T2;

        // actualizar alturas
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // -----------------------------
    // Rotación simple izquierda
    // -----------------------------
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // rotación
        y.left = x;
        x.right = T2;

        // actualizar alturas
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // -----------------------------
    // Inserción pública
    // -----------------------------
    public void insert(T key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node node, T key) {
        // 1. Inserción normal BST
        if (node == null)
            return new Node(key);

        if (key.compareTo(node.key) < 0)
            node.left = insertRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = insertRec(node.right, key);
        else
            return node; // no insertar duplicados

        // 2. Actualizar altura
        node.height = 1 + max(height(node.left), height(node.right));

        // 3. Obtener balance
        int balance = getBalance(node);

        // 4. Casos de rotación
        // Izquierda-Izquierda
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rotateRight(node);

        // Derecha-Derecha
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return rotateLeft(node);

        // Izquierda-Derecha
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Derecha-Izquierda
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node; // sin cambios
    }

    // -----------------------------
    // Búsqueda pública
    // -----------------------------
    public boolean contains(T key) {
        return containsRec(root, key);
    }

    private boolean containsRec(Node node, T key) {
        if (node == null)
            return false;
        if (key.compareTo(node.key) == 0)
            return true;
        return key.compareTo(node.key) < 0
                ? containsRec(node.left, key)
                : containsRec(node.right, key);
    }

    // -----------------------------
    // Mínimo de un subárbol
    // -----------------------------
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // -----------------------------
    // Eliminación pública
    // -----------------------------
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node node, T key) {
        if (node == null)
            return null;

        // 1. Eliminación tipo BST
        if (key.compareTo(node.key) < 0)
            node.left = deleteRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = deleteRec(node.right, key);
        else {
            // Nodo con 1 hijo o 0 hijos
            if ((node.left == null) || (node.right == null)) {
                Node temp = (node.left != null) ? node.left : node.right;

                if (temp == null) { // sin hijos
                    node = null;
                } else { // un hijo
                    node = temp;
                }
            } else {
                // Nodo con 2 hijos → usar sucesor
                Node temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = deleteRec(node.right, temp.key);
            }
        }

        if (node == null)
            return null;

        // 2. Actualizar altura
        node.height = 1 + max(height(node.left), height(node.right));

        // 3. Rebalancear
        int balance = getBalance(node);

        // Izquierda-Izquierda
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        // Izquierda-Derecha
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Derecha-Derecha
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        // Derecha-Izquierda
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // -----------------------------
    // Recorrido in-order
    // -----------------------------
    public void printInOrder() {
        printInOrderRec(root);
        System.out.println();
    }

    private void printInOrderRec(Node node) {
        if (node != null) {
            printInOrderRec(node.left);
            System.out.print(node.key + " ");
            printInOrderRec(node.right);
        }
    }

    // -----------------------------
    // Obtener raíz (opcional)
    // -----------------------------
    public Node getRoot() {
        return root;
    }
}
