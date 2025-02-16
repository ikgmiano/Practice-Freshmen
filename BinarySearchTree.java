import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
    Node root;

    // INSERTION
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node insert(Node root, T data) {
        if (root == null) {
            return new Node(data);
        }
        
        if (data.compareTo(root.data) < 0) {
            // go left child
            root.left = insert(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            // go right child
            root.right = insert(root.right, data);
        } else {
            // case for duplicates
        }
        return root;
    }

    // DELETION
    public void delete(T data) {
        root = delete(root, data);
    }

    private Node delete(Node root, T data) {
        if (root == null) {
            return null; // to avoid NullPointerException when the data is not in the tree
        }

        if (data.compareTo(root.data) < 0) {
            // go left child
            root.left = delete(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            // go right child
            root.right = delete(root.right, data);
        } else {
            // data to be deleted is found
            if (root.right == null) {
                return root.left; // left child only or leaf node
            } else if (root.left == null) {
                return root.right; // right child only
            } else {
                // two children (either get predecessor or successor, both are valid. in this case it's predecessor)
                root.data = getMax(root.left);
                root.left = delete(root.left, root.data); // delete the predecessor (which is now a duplicate)
            }
        }
        return root;
    }

    // SEARCHING (ITERATIVE)
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

    // TRAVERSAL (INORDER)
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

    // TRAVERSAL (LEVEL-ORDER)
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

    // get min element in the tree
    public T getMin() {
        return getMin(root);
    }
    
    // GET MINIMUM (helper method for getting successor)
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
    
    // get max element in the tree
    public T getMax() {
        return getMax(root);
    }
    
    // GET MAXIMUM (helper method for getting predecessor)
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

    // IS EMPTY
    public boolean isEmpty() {
        return root == null;
    }
    


    // =========================================================
    // ================ TESTING (FOR FUN ONLY!) ================  
    // =========================================================

    // return the root of the tree
    public T getRoot() {
        return (root != null ? root.data : null);
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
                System.out.println("Left: " + (curr.left != null ? curr.left.data : null));
                System.out.println("Right: " + (curr.right != null ? curr.right.data : null));
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
    


    // TREE NODE CLASS STRUCTURE
    class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
        }
    }
}