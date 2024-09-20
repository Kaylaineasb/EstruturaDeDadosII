package Atividade;

public interface IPriorityQueue <T extends Comparable<T>>{
    void insert(T element);  // Insere um elemento na fila de prioridade
    T remove();              // Remove o elemento de maior prioridade
    boolean contains(T element); // Verifica se o elemento está na fila
}
