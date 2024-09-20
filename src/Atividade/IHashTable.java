package Atividade;

public interface IHashTable <K,V>{
    void put(K key, V value);   // Insere uma chave e valor
    V get(K key);               // Retorna o valor associado à chave
    void remove(K key);         // Remove a chave e seu valor
    boolean containsKey(K key); // Verifica se a chave existe na tabela
}
