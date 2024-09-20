package Atividade;

import java.util.Arrays;

public class MaxHeap <T extends Comparable<T>> extends AbstractHeap<T>{
    public MaxHeap(int capacidadeInicial) {
        super(capacidadeInicial);
    }

    @Override
    public void insert(T elemento) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2); // Redimensiona
        }

        heap[size] = elemento;
        size++;
        heapifyUp(size - 1);
    }

    @Override
    public T extractMax() {
        if (size == 0) {
            return null;
        }

        T max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);

        return max;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void heapifyUp(int i) {
        int pai = parent(i);
        while (i > 0 && heap[i].compareTo(heap[pai]) > 0) {
            trocar(i, pai);
            i = pai;
            pai = parent(i);
        }
    }

    @Override
    public void heapifyDown(int i) {
        int maior = i;
        int esquerda = left(i);
        int direita = right(i);

        if (esquerda < size && heap[esquerda].compareTo(heap[maior]) > 0) {
            maior = esquerda;
        }

        if (direita < size && heap[direita].compareTo(heap[maior]) > 0) {
            maior = direita;
        }

        if (maior != i) {
            trocar(i, maior);
            heapifyDown(maior);
        }
    }

    private void trocar(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
