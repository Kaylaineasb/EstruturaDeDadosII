package Heap;

public class Heap {
    private int[] heap; //declara um array para armazenar os elementos do heap
    private int tail; //índice do ultimo elemento do heap


    //construtor que inicializa o heap com a capacidade fornecida e define tail como -1(heap vazio)
    public Heap(int capacidade){
        this.heap = new int[capacidade];
        this.tail = -1;
    }
    //verifica se o heap está vazio
    public boolean isEmpty(){
        return this.tail == -1;
    }
    //calcula o índice do filho à esquerda de um nó
    public int left(int index){
        return 2 * index +1;
    }
    //calcula o índice do filho à direita de um nó
    public int right(int index){
        return 2* (index + 1);
    }
    //calcula o índice do nó pai
    public int parent(int index){
        return Math.floorDiv(index-1,2);
    }

    public void add(int n){
        //se o heap estiver cheio, redimensiona o array para dobrar a capacidade
        if (tail >= (heap.length-1)){
            resize();
        }
        tail +=1; //incrementa o índice 'tail' apontando para a nova posição
        this.heap[tail] = n; //adiciona o novo elemento no final do heap

        int i = tail; //inicia a partir do índice do último elemento
        //realiza a operação de 'up-heap' para manter a propriedade do heap
        while (i> 0 && this.heap[parent(i)]<this.heap[i]){
            //se o pai for menor que o elemento atual, troca-os
            int aux = this.heap[i];
            this.heap[i] = this.heap[parent(i)];
            this.heap[parent(i)] = aux;
            i = parent(i); //move o índice do pai e repete o processo
        }
    }
    //remove o maior elemento (raiz)
    public int remove(){
        //lança uma exceção se o heap estiver vazio
        if (isEmpty()) throw new RuntimeException("Empty");
        int element = this.heap[0]; //armazena o elemento da raiz
        this.heap[0]=this.heap[tail]; //substitui a raiz pelo ultimo
        this.tail -=1; //reduz o tamanho do heap

        this.heapify(0); //reorganiza o heap

        return element; //retorna o elemento removido
    }
    //reorganiza o heap a partir de um índice específico
    public void heapify(int index){
        //se o nó for uma folha ou índice for invalido, não faz nada
        if (isLeaf(index)||!isValidIndex(index))
            return;
        //encontra o maior valor entre o nó e seu filha à esquerda e à direita
        int index_max = max_index(index,left(index),right(index));
        //se o mior valor não estiver no índice atual, faz a troca
        if (index_max!=index){
            swap(index,index_max);
            heapify(index_max); //repete o proecesso
        }
    }
    //método que retorna o índice do maior valor entre três nós
    private int max_index(int index, int left, int right){
        if (this.heap[index] > this.heap[left]){
            //se o nó atual for maior que o filho esquerdo, verifica o direito
            if (isValidIndex(right)){
                if (this.heap[index]< this.heap[right]) {
                    return right; //se o filho direito for maior, retorna seu índice
                }
            }
            return index; // caso contrário, retorna indice atual
        }else {
            // se o filho esquerdo for maior, verifica o filho direito
            if (isValidIndex(right)){
                if (this.heap[left]<this.heap[right])
                    return right; //retorna o indice do maior filho
            }
            return left; //retorna o índice do filho esquerdo
        }
    }
    //verifica se o índice é valido dentro do heap
    private boolean isValidIndex(int index){
        return index >=0 && index <=tail;
    }
    //verifica se um nó é uma folha(não tem filhos)
    private boolean isLeaf(int index){
        return index > parent(tail) && index <= tail;
    }
    //troca elementos de dois indices no heap
    private void swap(int i, int j){
        int aux = this.heap[i];
        this.heap[i]= this.heap[j];
        this.heap[j] = aux;
    }
    //constrói o heap a partir de um array de elementos não organizados
    private void buildHeap(){
        //chama o heapify começando dos pais das folhas até a raiz
        for (int i= parent(this.tail);i>=0;i--){
            heapify(i);
        }
    }
    //redimensiona o array do heap quando o espaço é insuficiente
    public void resize(){
        int newCapacity =  this.heap.length*2;//dobra a capacidade do heap
        int[] newHeap = new int[newCapacity];//cria um novo array com a nova capacidade
        //copia os elementos do heap antigo
        for (int i=0; i<= this.tail;i++){
            newHeap[i] = this.heap[i];
        }
        //substitui o array antigo pelo novo
        this.heap= newHeap;
    }
}

