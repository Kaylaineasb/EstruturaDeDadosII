package EditorDeTexto;

import java.util.StringTokenizer;

public interface IEditor<T> {
    void addLista(String texto);
    void addInicio(String texto);
    void addPosicao(int poisicao, String texto);

    void removerFim();
    void removerIni();
    void removerPosicao(int posicao);

    boolean buscar(String texto);
    int quantPalavras();
    int quantCaracter();
    void imprimir();
}
