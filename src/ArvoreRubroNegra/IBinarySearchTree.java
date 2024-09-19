package ArvoreRubroNegra;

public interface IBinarySearchTree <T extends Comparable<T>>{
    void insert(T data);
    void delete(T data);
    boolean search(T data);

}
