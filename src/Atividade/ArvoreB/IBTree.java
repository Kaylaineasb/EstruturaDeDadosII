package Atividade.ArvoreB;

public interface IBTree<T extends Comparable<T>> {
    void insert(T value);  // Insere uma chave na árvore B
    void delete(T value);  // Remove uma chave da árvore B
    boolean search(T value); // Busca por uma chave na árvore B
}
