package Atividade;

public interface IHeap <T extends Comparable<T>>{
    void insert(T element);   // Insere um elemento no heap
    T extractMax();           // Remove e retorna o maior elemento
    boolean isEmpty();        // Verifica se o heap está vazio
}
