import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
    Node root;

    // testing for finding the height of a tree (through finding the height of subtrees recursively)
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node root) {
        // base case
        if (root == null) {
            return 0;
        }

        // recursive calls
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    // insertion 1 (geek)
    public void add(T value) {
        if (root == null) {
            root = new Node(value);
        } else {
            add(root, value);
        }
    }

    private void add(Node root, T value) {
        if (value.compareTo(root.value) < 0) {
            // left child
            if (root.left == null) {
                root.left = new Node(value);
            } else {
                add(root.left, value);
            }
        } else if (value.compareTo(root.value) > 0) {
            // right child
            if (root.right == null) {
                root.right = new Node(value);
            } else {
                add(root.right, value);
            }
        } else {
            // case for duplicates
        }
    }

    // insertion 2 (bro)
    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node root, T value) {
        if (root == null) {
            return new Node(value);
        }
        
        if (value.compareTo(root.value) < 0) {
            root.left = insert(root.left, value);
        } else if (value.compareTo(root.value) > 0) {
            root.right = insert(root.right, value);
        } else {
            // case for duplicates
        }
        return root;
    }

    // deletion 1 (geek)
    /*
     * the logic here is checking if the right child is null, if it's null, it solves the problem for left child only or no child
     * since in both cases, right child is null. then if right is not null, we check if left is null, if it is, solve the problem
     * for right child only. the else{} is for when the root has two children, where we can pick either the successor or predecessor 
     */
    public void delete(T value) {
        root = delete(root, value);
    }

    public Node delete(Node root, T value) {
        if (root == null) {
            return root;
        }

        if (value.compareTo(root.value) == 0) {
            if (root.right == null) {
                // left child only or no children/leaf node
                return root.left;
            } else if (root.left == null) {
                // right child only
                return root.right;
            } else {
                // two children (either get predecessor or successor, both are valid. in this case it's predecessor)
                root.value = getPredecessor(root);
                root.left = delete(root.left, root.value);
            }
        } else if (value.compareTo(root.value) < 0) {
            // go left child
            root.left = delete(root.left, value);
        } else {
            // go right child
            root.right = delete(root.right, value);
        }
        return root;
    }

    // deletion 2 (bro)
    /*
     * the difference in here is that the logic always goes for the successor/predecessor if it's not a leaf node,
     * unlike the first one that replaces the to-be-deleted node with its one child, handling the two child case
     * separately
     */
    public void remove(T value) {
        root = remove(root, value);
    }

    private Node remove(Node root, T value) {
        if (root == null) {
            return root;
        }

        if (value.compareTo(root.value) == 0) {
            if (root.left == null && root.right == null) {
                // no children/leaf node
                return null;
            } else if (root.left != null) {
                // replace root's value with predecessor (max value in left subtree)
                root.value = getPredecessor(root);
                // then remove the predecessor in the left subtree
                root.left = remove(root.left, root.value);
            } else {
                // replace root's value with successor (min value in right subtree)
                root.value = getSuccessor(root);
                // then remove the successor in the right subtree
                root.right = remove(root.right, root.value);
            }
        } else if (value.compareTo(root.value) < 0) {
            // go left child
            root.left = remove(root.left, value);
        } else {
            // go right child
            root.right = remove(root.right, value);
        }
        return root;
    }

    // Search Iterative
    public boolean contains(T value) {
        if (isEmpty()) {
            return false;
        }

        Node curr = root;
        while (curr != null) {
            if (value.compareTo(curr.value) == 0) {
                return true;
            } else if (value.compareTo(curr.value) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    // Search Recursive
    public boolean search(T value) {
        return search(root, value);
    }

    private boolean search(Node root, T value) {
        // Tree is either empty or does not contain the value (reached the end)
        if (root == null) {
            return false;
        }

        if (value.compareTo(root.value) == 0) {
            return true;
        } else if (value.compareTo(root.value) < 0) {
            return search(root.left, value);
        } else {
            return search(root.right, value);
        }
    }

    // traversal
    public void inorder() {
        if (root == null) {
            return;
        }

        inorder(root);
    }

    private void inorder(Node root) {
        if (root == null) {
            return;
        }
        
        inorder(root.left);
        System.out.println(root.value);
        inorder(root.right);
    }

    public void levelorder() {
        if (root == null) {
            return;
        }

        levelorder(root);
    }

    private void levelorder(Node root) {
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
            System.out.println(curr.value);
        }
    }

    // successor
    private T getSuccessor(Node root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root.value;
    }

    // predecessor
    private T getPredecessor(Node root) {
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root.value;
    }

    public boolean isEmpty() {
        return root == null;
    }

    class Node {
        T value;
        Node left;
        Node right;

        Node(T value) {
            this.value = value;
        }
    }
}