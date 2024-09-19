package ArvoreRubroNegra;

public abstract class AbstractBinarySearchTree <T extends Comparable<T>> implements IBalancedTree<T>{
    protected Node<T> root;
    protected static class Node<T> {
        T data;
        Node<T> left, right, parent;
        boolean color; // true = RED, false = BLACK

        Node(T data) {
            this.data = data;
            this.left = this.right = this.parent = null;
            this.color = true; // Novo nó é inicialmente vermelho
        }
    }
    // Implementação genérica de busca
    @Override
    public boolean search(T data) {
        return searchTree(root, data) != null;
    }

    Node<T> searchTree(Node<T> node, T data) {
        if (node == null || data.compareTo(node.data) == 0)
            return node;
        if (data.compareTo(node.data) < 0)
            return searchTree(node.left, data);
        else
            return searchTree(node.right, data);
    }
    @Override
    public abstract void balanceAfterInsert(Node<T> node);

    @Override
    public abstract void balanceAfterDelete(Node<T> node);
}
