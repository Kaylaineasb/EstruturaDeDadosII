package arvore;

public class Main {
    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        arvore.inserir(50);
        arvore.inserir(70);
        arvore.inserir(45);
        arvore.inserir(25);
        arvore.inserir(13);
        arvore.inserir(68);
        arvore.inserir(20);

        System.out.println("Árvore em Ordem: ");
        arvore.percursoEmOrdem();

        System.out.println("Árvore em Pre-Ordem: ");
        arvore.percusoPreOrdem();

        System.out.println("Árvore em Pos-Ordem: ");
        arvore.percusoPosOrdem();

        arvore.remover(10);

        System.out.println("\nÁrvore após a remoção:");
        arvore.percursoEmOrdem();
    }
}

