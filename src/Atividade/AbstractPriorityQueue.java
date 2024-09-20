package Atividade;

public abstract class AbstractPriorityQueue <T extends Comparable<T>> implements IPriorityQueue<T>{
    protected T[] heap;
    protected int size;

    public AbstractPriorityQueue(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    protected int parent(int i) { return (i - 1) / 2; }
    protected int left(int i) { return 2 * i + 1; }
    protected int right(int i) { return 2 * i + 2; }

    public abstract void heapifyUp(int i);
    public abstract void heapifyDown(int i);
}
