package arvore;

public class ArvoreBinaria {
    // a raiz da árvore
    No raiz;
    public ArvoreBinaria(){
        this.raiz = null;
    }

    public void inserir(int valor){
        raiz = inserirRecursivo(raiz,valor);
    }
    public No inserirRecursivo(No no, int valor){
        //se o nó atual é nulo, cria um novo nó com o valor
        if(no==null){
            return new No(valor);
        }
        // se o valor é menor que o valor do nó atual, insere à esquerda
        if (valor< no.valor){
            no.esquerda = inserirRecursivo(no.esquerda,valor);
        }
        //Se o valor é maior que o vaor do nó atual, insere à direita
        else if (valor > no.valor) {
            no.direita = inserirRecursivo(no.direita,valor);
        }
        return no;
    }
    public boolean buscar(int valor){
        return buscarRecursivo(raiz,valor);
    }
    private boolean buscarRecursivo(No no, int valor){
        //Se o nó atual é nulo, o valor não foi encontrado
        if (no==null){
            return false;
        }
        //se o valor é igual ao valor do nó atual, o valor foi encontrado
        if (no.valor==valor){
            return true;
        }
        //se o valor é menor que o valor do nó atual , busca à esquerda
        else if (valor<no.valor) {
            return buscarRecursivo(no.esquerda,valor);
        }
        //se o valor é maior que o valor do nó atual, busca à direita
        else {
            return buscarRecursivo(no.direita,valor);
        }
    }
    public void percursoEmOrdem(){
        percursoEmOrdemRecursivo(raiz);
    }
    //esquerda->raiz->direita
    private void percursoEmOrdemRecursivo(No no){
        if(no!=null){
            percursoEmOrdemRecursivo(no.esquerda);
            System.out.println(no.valor+"");
            percursoEmOrdemRecursivo(no.direita);
        }
    }
    public void percusoPreOrdem(){
        percusoPreOrdemRecursivo(raiz);
    }
    //raiz-> esquerda->direita
    private void percusoPreOrdemRecursivo(No no){
        if(no!=null){
            System.out.println(no.valor+"");//visita o nó atual
            percursoEmOrdemRecursivo(no.esquerda);//visita a subárvore esquerda
            percursoEmOrdemRecursivo(no.direita);// visita a subárvore direita
        }
    }
    public void percusoPosOrdem(){
        percusoPosOrdemRecurso(raiz);
    }
    //esquerda>direita->raiz
    private void percusoPosOrdemRecurso(No no){
        if (no!=null){
            percusoPosOrdemRecurso(no.esquerda); //visita a subárvore esquerda
            percusoPosOrdemRecurso(no.direita); //visita a subárvore direita
            System.out.println(no.valor+""); //visita o nó atual
        }
    }
    public void remover(int valor) {
        raiz = removerRecursivo(raiz, valor);
    }
    private No removerRecursivo(No no, int valor) {
        // se o nó atual é nulo, o valor não foi encontrado
        if (no == null) {
            System.out.println("O valor não foi encontrado");
            return null;
        }
        //se o valor é menor que o valor do nó atual, remove à esquerda
        if (valor < no.valor) {
            no.esquerda = removerRecursivo(no.esquerda, valor);
        }
        //Se o valor é maior que o valor do nó atual, remove à direita
        else if (valor > no.valor) {
            no.direita = removerRecursivo(no.direita, valor);
        }
        //se o valor é igual ao valor do nó atual, remove o nó
        else {
            //se o nó não tem filhos, simplesmente remove o nó
            if (no.esquerda == null && no.direita == null) {
                return null;
            }
            //Se o nó tem apenas um filho à direita, o filho substitui o n[o
            else if (no.direita == null) {
                return no.esquerda;
            }
            //Se o nó tem apenas um filho à esquerda, o filho substittui o nó
            else if (no.esquerda == null) {
                return no.direita;
            }
            //Se o nó tem dois filhos, substitui o nó pelo menor valor da subarvore direita
            else {
                no.valor = encontrarMenorValor(no.direita);
                no.direita = removerRecursivo(no.direita, no.valor);
            }
        }
        return no;
    }
    private int encontrarMenorValor(No no) {
        return no.esquerda == null ? no.valor : encontrarMenorValor(no.esquerda);
    }
}
