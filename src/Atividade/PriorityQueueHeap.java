package Atividade;

import java.util.Arrays;

public class PriorityQueueHeap <T extends Comparable<T>> extends AbstractPriorityQueue<T>{
    public PriorityQueueHeap(int capacidadeInicial) {
        super(capacidadeInicial);
    }

    @Override
    public void insert(T elemento) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2); // Redimensiona
        }

        heap[size] = elemento;
        size++;
        heapifyUp(size - 1); // Ajusta a posição
    }

    @Override
    public T remove() {
        if (size == 0) {
            return null;
        }

        T elementoRemovido = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0); // Rebalanceia o heap

        return elementoRemovido;
    }

    @Override
    public boolean contains(T elemento) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(elemento)) {
                return true;
            }
        }
        return false;
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
