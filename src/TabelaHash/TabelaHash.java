package TabelaHash;

import java.util.LinkedList;

public class TabelaHash <K,V>{
    private LinkedList<Entrada<K,V>>[] tabela;
    private int tamanho;

    public TabelaHash(int capacidadeInicial){
        tabela = new LinkedList[capacidadeInicial];
        tamanho=0;

        //Inicializando as listas em cada posição da tabela
        for (int i=0;i<capacidadeInicial;i++){
            tabela[i] = new LinkedList<>();
        }
    }

    //Função hash que transforma a chave em um índice válido
    private int funcaoHash(K chave){
        int hashCode = chave.hashCode();
        return Math.abs(hashCode) % tabela.length;
    }

    //Método para inserir ou atualizar um valor
    public void inserir(K chave, V valor){
        int indice = funcaoHash(chave);
        LinkedList<Entrada<K,V>> lista = tabela[indice];
        //verificar se a chave já existe
        for (Entrada<K,V> entrada : lista){
            if (entrada.getChave().equals(chave)){
                //Atualiza valor se a chave já existir
                entrada.setValor(valor);
                return;
            }
        }
        //caso contrário, adicionamos uma nova entrada
        lista.add(new Entrada<>(chave,valor));
        tamanho++;
    }
    //Método para buscar um valor pela chave
    public V buscar(K chave){
        int indice = funcaoHash(chave);
        LinkedList<Entrada<K,V>> lista = tabela[indice];
        //Percorrer a lista no índice para encontrar a chave
        for (Entrada<K,V> entrada:lista){
            if (entrada.getChave().equals(chave)){
                return entrada.getValor();
            }
        }
        return null;
    }
    //Método para remover uma entrada pela chave
    public void remover(K chave){
        int indice = funcaoHash(chave);
        LinkedList<Entrada<K,V>> lista = tabela[indice];

        //Procurar a entrada com a chave para removê-la
        Entrada<K,V> paraRemover = null;
        for (Entrada<K,V> entrada: lista){
            if (entrada.getChave().equals(chave)){
                paraRemover = entrada;
                break;
            }
        }
        if (paraRemover!=null){
            lista.remove(paraRemover);
            tamanho--;
        }
    }
    //Método para verificar o tamanho da lista
    public int getTamanho(){
        return tamanho;
    }
}
