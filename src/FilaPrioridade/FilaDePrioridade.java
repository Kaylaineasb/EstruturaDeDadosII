package FilaPrioridade;

public class FilaDePrioridade {
    private int[] heap;
    private int tail; //indice do ultimo elemento
    private int capacidade;

    public FilaDePrioridade(int capacidade){
        this.capacidade = capacidade;
        this.heap = new int[capacidade];
        this.tail = -1;
    }
    //retorna se está vazia
    public boolean isEmpty(){
        return this.tail==-1;
    }
    //verifica se o heap está cheio e redimensiona
    public void resize(){
        this.capacidade=capacidade*2;
        int[] novoHeap = new int[this.capacidade];
        for (int i=0;i<=this.tail;i++){
            novoHeap[i] = this.heap[i];
        }
        this.heap=novoHeap;
    }
    //retorna o índice do filho à esquerda de um nó
    private int left(int index){
        return 2* index +1;
    }
    //retorna o índice do filho à direita de um nó
    private int right(int index){
        return 2* (index+1);
    }
    //retorna o indice do pai de um nó
    private int parent(int index){
        return (index-1)/2;
    }
    //troca elementos de dois indices no heap
    private void swap(int i, int j){
        int aux = this.heap[i];
        this.heap[i]= this.heap[j];
        this.heap[j] = aux;
    }

    public void add(int elemento){
        //se o array estiver cheio, redimensiona o heap
        if (tail>=heap.length-1){
            resize();
        }
        //insere o novo elemento ao final da fila
        tail++;
        heap[tail]=elemento;

        int i=tail;
        while (i>0 &&heap[parent(i)]<heap[i]){
            //se o pai do elemento atual for menor, faz a troca
            swap(i,parent(i));
            i = parent(i);
        }
    }
    public int remove(){
        if (isEmpty()){
            throw new RuntimeException("A fila de prioridade está vazia");
        }
        int max = heap[0];

        heap[0] = heap[tail];
        tail--;
        heapify(0);
        return max;
    }
    private void heapify(int i){
        int esquerda = left(i);
        int direita = right(i);
        int maior = i;

        //verifica se o filho à esquerda é maior que o nó atual
        if (esquerda<=tail && heap[esquerda]>heap[maior]){
            maior = esquerda;
        }
        //verifica se o filho da direita é maior que o nó atual ou o filho à esquerda
        if (direita<=tail && heap[direita]>heap[maior]){
            maior=direita;
        }
        //se o maior valor não for o nó atual, faz a troca e continua o heapify
        if (maior!=i){
            swap(i,maior);
            heapify(maior);
        }
    }
    //retorna o maior elemento
    public int peek(){
        if (isEmpty()){
            throw new RuntimeException("A fila de prioridade está vazia");
        }
        return heap[0];
    }
}
