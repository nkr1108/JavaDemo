class BinarySearchTree {

    class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    Node root;
    private int comparisonCount;

    BinarySearchTree() {
        root = null;
    }

    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    boolean search(int target) {
        comparisonCount = 0;
        return searchRec(root, target);
    }

    private boolean searchRec(Node node, int target) {
        if (node == null) return false;
        comparisonCount++;
        if (node.data == target) return true;
        if (target < node.data) return searchRec(node.left, target);
        else return searchRec(node.right, target);
    }

    public int getComparisonCount(){
        return comparisonCount;
    }

}