package EditorDeTexto;

import java.util.List;

public class EditorDupEnc<T> implements  IEditor{
    private  String texto;
    private  int numeroDePalavras;
    private  int tamanho;
    private No<T> ini;
    private No<T> fim;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNumeroDePalavras() {
        return numeroDePalavras;
    }

    public void setNumeroDePalavras(int numeroDePalavras) {
        this.numeroDePalavras = numeroDePalavras;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public EditorDupEnc(String texto) {
        this.texto = texto;
        this.tamanho =0;
        this.numeroDePalavras = 0;
        this.ini =null;
        this.fim = null;
        this.addLista(texto);
    }

    @Override
    public void addLista(String texto) {
        this.setTexto(texto); // caso chamem o metodo fora do construtor
        char[] textoCaracteres = getTexto().toCharArray();
        for (char c :textoCaracteres) {
            if (getTamanho() == 0){
                No<T> no = (No<T>) new No<>(c);
                this.ini = no;
                this.fim=no;
                this.fim.setAnterior(ini);
                this.ini.setProximo(fim);
            }else {
                No<T> no = (No<T>) new No<>(c);
                this.fim.setProximo(no);
                no.setAnterior(fim);
                this.fim = no;
            }
            this.setTamanho(getTamanho()+1);
        }

    }

    @Override
    public void addInicio(String texto) {
        if (this.getTamanho() == 0){
            this.addLista(texto);
        }
        else {
           this.setTexto(texto);
            char[] textoCaracteres = getTexto().toCharArray();
            for (int i = textoCaracteres.length-1; i >=0; i--) { // conferir se ta certo ou tirar o -1 do length
                No<T> no =(No<T>) new No<>(textoCaracteres[i]);
                no.setProximo(this.ini);
                this.ini.setAnterior(no);
                this.ini = no;
                this.setTamanho(getTamanho()+1);
            }
        }
    }

    @Override
    public void addPosicao(int poisicao, String texto) {
        if (poisicao> getTamanho()){
            this.addLista(texto);
        }else if (poisicao<=0){
            this.addInicio(texto);
        }else {
            this.setTexto(texto);
            char[] textoCaracteres = getTexto().toCharArray();
            for (int i = textoCaracteres.length-1; i >=0; i--) {// vai adicionar cada carecter na msm posicao so q na ordem inversa
                No<T> no =(No<T>) new No<>(textoCaracteres[i]);// da mesma forma que é feita no metodo addinicio
                No<T> novoNo = this.ini;
                for (int j = 0; j < poisicao; j++) {
                    novoNo = novoNo.getProximo();
                }
                no.setAnterior(novoNo);
                no.setProximo(novoNo.getProximo());
                novoNo.getProximo().setAnterior(no);
                novoNo.setProximo(no);
                this.setTamanho(getTamanho()+1);

                }

            }
    }

    @Override
    public void removerFim() {
        if (this.getTamanho() ==0){
            throw new RuntimeException("a lista ja estar vazia");
        }else if (this.getTamanho() ==1){
            this.removerIni();
        }else {
            this.fim = fim.getAnterior();
            fim.setProximo(null);
            this.setTamanho(getTamanho()-1);
        }

    }

    @Override
    public void removerIni() {
        if (this.getTamanho() ==0){
            throw new RuntimeException("a lista ja estar vazia");
        }else if (this.getTamanho() ==1){
           this.ini = null;
           this.fim = null;
        }else {
            this.ini = ini.getProximo();
            this.ini.setAnterior(null);
            this.setTamanho(getTamanho()-1);
        }
    }

    @Override
    public void removerPosicao(int posicao) {
        if (posicao >= this.getTamanho()){
            this.removerFim();
        } else if (posicao <= 0) {
            this.removerIni();
        }else {
            No<T> novoNo = ini;
            for (int i = 0; i <posicao ; i++) {
                novoNo = novoNo.getProximo();
            }
            novoNo.setProximo(novoNo.getProximo().getProximo()); // estar pulando um no
            novoNo.getProximo().getProximo().setAnterior(novoNo);
            this.setTamanho(getTamanho()-1);
        }

    }


    @Override
    public boolean buscar(String texto) {
        this.setTexto(texto);
        char[] caracteres = getTexto().toCharArray(); // separa o texto pelos caracteres armazenando eles em um vetor
        No<T> novoNo = ini;
        for (int i = 0; i < getTamanho(); i++) {
            if (novoNo.getElemento().equals(caracteres[0])){ // se o no for igual a primeira letra buscada
                for (int j = 0; j < caracteres.length-1; j++) {// conferir se é msm usando o -1
                    if (novoNo.getElemento().equals(caracteres[j])){
                        novoNo= novoNo.getProximo(); // conferir se o no nao estar excedendo o numero desejado
                    }else {
                        break; // caso o proximo elemento nao seja o buscado, vai sair do laço de repeticao
                    }
                }
                return true;
            }else {
                novoNo = novoNo.getProximo();// atualiza ate achar o valor buscado
            }

        }
        System.out.println("texto nao encontrado");
        return false;
    }
    public int quantPalavras() {
        No<T> novoNo = this.ini;
        boolean dentroDaPalavra = false; // Flag para rastrear se estamos dentro de uma palavra
        int contadorPalavras = 0;

        while (novoNo != null) {
            T elemento = novoNo.getElemento();
            String elementoString = elemento.toString().trim(); // Remova espaços em branco extras
            if (!elementoString.isEmpty()) {
                // Elemento não é vazio, estamos dentro de uma palavra
                dentroDaPalavra = true;
            } else if (dentroDaPalavra) {
                // Elemento é vazio e estávamos dentro de uma palavra
                contadorPalavras++;
                dentroDaPalavra = false;
            }
            novoNo = novoNo.getProximo();
        }

        // Verifique se a última palavra terminou antes do final da estrutura
        if (dentroDaPalavra) {
            contadorPalavras++;
        }

        return contadorPalavras;
    }

    @Override
    public int quantCaracter() {
        return this.getTamanho();

    }

    @Override
    public void imprimir() {
        No<T> novoNo = this.ini;
        for (int i = 0; i < this.getTamanho() ; i++) {
            System.out.print(novoNo);
            if (novoNo.getProximo()!= null) {
                novoNo = novoNo.getProximo();
            }
        }
    }
}
