package Atividade;

import java.util.LinkedList;

public class TabelaHash <K,V> implements IHashTable<K,V>{
    private LinkedList<Entry<K, V>>[] tabela;
    private int tamanho;
    private int capacidade;
    private final float fatorDeCarga = 0.75f;

    // Classe interna para representar entradas da tabela hash
    private static class Entry<K, V> {
        K chave;
        V valor;

        public Entry(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    @SuppressWarnings("unchecked")
    public TabelaHash(int capacidadeInicial) {
        this.capacidade = capacidadeInicial;
        this.tabela = new LinkedList[capacidade];
        this.tamanho = 0;

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(K chave) {
        return Math.abs(chave.hashCode()) % capacidade;
    }

    @Override
    public void put(K chave, V valor) {
        int indice = hash(chave);
        LinkedList<Entry<K, V>> lista = tabela[indice];

        for (Entry<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                entrada.valor = valor; // Atualiza valor
                return;
            }
        }

        lista.add(new Entry<>(chave, valor));
        tamanho++;

        // Redimensionar se o fator de carga for ultrapassado
        if ((float) tamanho / capacidade > fatorDeCarga) {
            redimensionar();
        }
    }

    @Override
    public V get(K chave) {
        int indice = hash(chave);
        LinkedList<Entry<K, V>> lista = tabela[indice];

        for (Entry<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                return entrada.valor;
            }
        }

        return null; // Chave não encontrada
    }

    @Override
    public void remove(K chave) {
        int indice = hash(chave);
        LinkedList<Entry<K, V>> lista = tabela[indice];

        for (Entry<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                lista.remove(entrada);
                tamanho--;
                return;
            }
        }
    }

    @Override
    public boolean containsKey(K chave) {
        return get(chave) != null;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar() {
        capacidade *= 2;
        LinkedList<Entry<K, V>>[] novaTabela = new LinkedList[capacidade];

        for (int i = 0; i < capacidade; i++) {
            novaTabela[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> lista : tabela) {
            for (Entry<K, V> entrada : lista) {
                int novoIndice = Math.abs(entrada.chave.hashCode()) % capacidade;
                novaTabela[novoIndice].add(entrada);
            }
        }

        tabela = novaTabela;
    }
}
