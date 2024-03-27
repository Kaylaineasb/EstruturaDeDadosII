package EditorDeTexto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Teste {
    EditorDupEnc<String> editor = new EditorDupEnc<>("Eu amo estrutura de dados 2. Vou resolver em 10 minutos sozinho, o problema");
    @Test
    public void testQuantCaracter(){
        int tamanhoEsperado = 75;
        int resultado = editor.quantCaracter();
        assertEquals(tamanhoEsperado,resultado);
    }
    @Test
    public void testQtdPalavras(){
        int qtdEsperada = 14;
        int resultado = editor.quantPalavras();
        assertEquals(qtdEsperada,resultado);
    }
}

