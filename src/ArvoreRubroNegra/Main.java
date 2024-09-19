package ArvoreRubroNegra;

public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbTree = new RedBlackTree<>();
        //inserção
        System.out.println("Inserindo valores na árvore rubro-negra");
        rbTree.insert(10);
        rbTree.insert(20);
        rbTree.insert(30);
        rbTree.insert(15);
        rbTree.insert(25);

        //Testar busca
        System.out.println("\nTestando busca: ");
        System.out.println("Buscar 15: " + (rbTree.search(15) ? "Encontrado" : "Não encontrado"));
        System.out.println("Buscar 50: " + (rbTree.search(50) ? "Encontrado" : "Não encontrado"));

        // Testar remoção
        System.out.println("\nRemovendo 20 da árvore:");
        rbTree.delete(20);

        // Verificar se a árvore ainda é válida após a remoção
        System.out.println("Buscar 20 após remoção: " + (rbTree.search(20) ? "Encontrado" : "Não encontrado"));

        // Testar outras remoções
        System.out.println("\nRemovendo 10 da árvore:");
        rbTree.delete(10);

        // Buscar após remoção
        System.out.println("Buscar 10 após remoção: " + (rbTree.search(10) ? "Encontrado" : "Não encontrado"));
    }
}
