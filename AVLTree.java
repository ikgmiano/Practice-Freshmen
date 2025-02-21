import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    Node root;

    // INSERTION (UPDATED)
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node insert(Node root, T data) {
        if (root == null) {
            return new Node(data);
        }
        
        int difference = data.compareTo(root.data);
        if (difference < 0) {
            root.left = insert(root.left, data);
        } else if (difference > 0) {
            root.right = insert(root.right, data);
        } else {
            // case for duplicates
        }
        // return root;
        updateHeight(root); // updates the height of each visited node
        return rotate(root); // returns the rotated subtree (if there's any rotations)
    }

    // DELETION (UPDATED)
    public void delete(T data) {
        root = delete(root, data);
    }

    private Node delete(Node root, T data) {
        if (root == null) {
            return null;
        }

        int difference = data.compareTo(root.data);
        if (difference < 0) {
            root.left = delete(root.left, data);
        } else if (difference > 0) {
            root.right = delete(root.right, data);
        } else {
            if (root.right == null) {
                return root.left;
            } else if (root.left == null) {
                return root.right;
            } else {
                root.data = getMax(root.left);
                root.left = delete(root.left, root.data);
            }
        }
        // return root;
        updateHeight(root); // updates the height of each visited node
        return rotate(root); // return the rotated subtree (if there's any rotations)
    }

    // HEIGHT UPDATE
    public void updateHeight(Node node) {
        int leftHeight = (node.left != null) ? node.left.height : -1;
        int rightHeight = (node.right != null) ? node.right.height : -1;
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }

    // BALANCE FACTOR
    private int getBalanceFactor(Node node) {
        int leftHeight = (node.left != null) ? node.left.height : -1;
        int rightHeight = (node.right != null) ? node.right.height : -1;
        return leftHeight - rightHeight;
    }

    // ROTATIONS
    public Node rotate(Node node) {
        int balance = getBalanceFactor(node); // valid: [-1, 0, 1]
        if (balance > 1) { // left heavy
            if (getBalanceFactor(node.left) < 0) { // left-right
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) { // right heavy
            if (getBalanceFactor(node.right) > 0) { // right-left
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node; // balanced, no rotations needed
    }

    // rotate left (when right heavy)
    private Node rotateLeft(Node root) {
        Node pivot = root.right;
        root.right = pivot.left;
        pivot.left = root;
        updateHeight(root);
        updateHeight(pivot);
        return pivot;
    }

    // rotate right (when left heavy)
    private Node rotateRight(Node root) {
        Node pivot = root.left;
        root.left = pivot.right;
        pivot.right = root;
        updateHeight(root);
        updateHeight(pivot);
        return pivot;
    }

    public boolean contains(T data) {
        if (isEmpty()) {
            return false;
        }

        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                return true;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    public String inorder() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        return "[" + sb.substring(0, sb.length() - 2) + "]";
    }

    private StringBuilder inorder(Node root, StringBuilder sb) {
        if (root == null) {
            return sb;
        }
        
        inorder(root.left, sb);
        sb.append(root.data).append(", ");
        inorder(root.right, sb);
        return sb;
    }

    public String levelorder() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        levelorder(root, sb);
        return "[" + sb.substring(0, sb.length() - 2) + "]";
    }

    private StringBuilder levelorder(Node root, StringBuilder sb) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.poll();
            if (curr.left != null) {
                q.add(curr.left);
            }
            if (curr.right != null) {
                q.add(curr.right);
            }
            sb.append(curr.data).append(", ");
        }
        return sb;
    }

    public T getMin() {
        return getMin(root);
    }
    
    private T getMin(Node root) {
        if (root == null) {
            return null;
        }
        
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.data;
    }
    
    public T getMax() {
        return getMax(root);
    }
    
    private T getMax(Node root) {
        if (root == null) {
            return null;
        }

        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.data;
    }

    public boolean isEmpty() {
        return root == null;
    }
    


    // =========================================================
    // ================ TESTING (FOR FUN ONLY!) ================  
    // =========================================================

    // return the root of the tree
    public T getRoot() {
        return (root != null) ? root.data : null;
    }

    // find the depth of a given node
    public int getDepth(T data) {
        Node curr = root;
        int depth = 0;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                return depth;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            depth++;
        }
        return -1;
    }

    // find the height of the tree
    public int getHeight() {
        return getHeight(root);
    }
    
    // find the height of a given node
    public int getHeight(T data) {
        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                return getHeight(curr);
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return -1; // tree is empty or data is not in the tree
    }

    private int getHeight(Node root) {
        // base case
        if (root == null) {
            return -1;
        }

        // recursive case
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // display the children of a given node
    public void printChildren(T data) {
        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                System.out.println("Left: " + ((curr.left != null) ? curr.left.data : null));
                System.out.println("Right: " + ((curr.right != null) ? curr.right.data : null));
                return;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        System.out.println("Data is nonexistent.");
    }

    // =========================================================
    // =========================================================
    // =========================================================
    


    // TREE NODE CLASS STRUCTURE FOR AVL TREE IMPLEMENTATION
    class Node {
        T data;
        int height; // for checking the balance factor
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            this.height = 0;
        }
    }
}