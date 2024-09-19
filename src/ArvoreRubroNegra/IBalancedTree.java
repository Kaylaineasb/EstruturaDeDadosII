package ArvoreRubroNegra;

public interface IBalancedTree <T extends Comparable<T>> extends IBinarySearchTree<T>{
    void balanceAfterInsert(AbstractBinarySearchTree.Node<T> node);
    void balanceAfterDelete(AbstractBinarySearchTree.Node<T> node);
}
