package Atividade.ArvoreBPlus;

import Atividade.ArvoreB.BTree;
import Atividade.ArvoreB.IBTree;

public class BPlusTree <T extends Comparable<T>> implements IBTree<T> {
    private Node root;
    private final int t; // Grau mínimo (tamanho mínimo de filhos)

    private class Node {
        int n; // Número de chaves
        boolean leaf; // Verifica se é folha
        Comparable<T>[] keys;
        Node[] children;
        Node next; // Ponteiro para a próxima folha (apenas para nós folhas)

        @SuppressWarnings("unchecked")
        public Node(boolean leaf) {
            this.leaf = leaf;
            this.keys = new Comparable[2 * t - 1]; // Máximo de chaves
            this.children = new BPlusTree.Node[2 * t]; // Máximo de filhos
            this.n = 0;
            this.next = null;
        }
    }

    public BPlusTree(int t) {
        this.t = t;
        this.root = new Node(true);
    }

    @Override
    public void insert(T value) {
        Node r = root;
        if (r.n == 2 * t - 1) { // Se a raiz está cheia
            Node s = new Node(false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, value);
        } else {
            insertNonFull(r, value);
        }
    }

    private void insertNonFull(Node x, T value) {
        int i = x.n - 1;
        if (x.leaf) {
            // Inserção em um nó folha
            while (i >= 0 && value.compareTo((T) x.keys[i]) < 0) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = value;
            x.n++;
        } else {
            // Inserção em um nó interno
            while (i >= 0 && value.compareTo((T) x.keys[i]) < 0) {
                i--;
            }
            i++;
            if (x.children[i].n == 2 * t - 1) {
                splitChild(x, i, x.children[i]);
                if (value.compareTo((T) x.keys[i]) > 0) {
                    i++;
                }
            }
            insertNonFull(x.children[i], value);
        }
    }

    private void splitChild(Node x, int i, Node y) {
        Node z = new Node(y.leaf);
        z.n = t - 1;

        // Transfere as chaves do meio para o novo nó z
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        if (!y.leaf) {
            // Transfere os filhos também se o nó y não for folha
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1; // Reduz o número de chaves em y

        // Move os filhos de x para abrir espaço para o novo filho
        for (int j = x.n; j >= i + 1; j--) {
            x.children[j + 1] = x.children[j];
        }

        // Liga o novo nó z ao nó pai x
        x.children[i + 1] = z;

        // Move as chaves de x para abrir espaço para a nova chave
        for (int j = x.n - 1; j >= i; j--) {
            x.keys[j + 1] = x.keys[j];
        }

        // Eleva a chave do meio para o nó pai
        x.keys[i] = y.keys[t - 1];
        x.n++;

        // Liga os nós folhas
        if (y.leaf) {
            z.next = y.next;
            y.next = z;
        }
    }

    @Override
    public boolean search(T value) {
        return searchLeaf(root, value);
    }

    private boolean searchLeaf(Node x, T value) {
        int i = 0;
        while (i < x.n && value.compareTo((T) x.keys[i]) > 0) {
            i++;
        }

        if (x.leaf) {
            if (i < x.n && value.compareTo((T) x.keys[i]) == 0) {
                return true;
            }
            return false;
        }

        return searchLeaf(x.children[i], value);
    }

    @Override
    public void delete(T value) {
        delete(root, value);

        // Se a raiz não tiver chaves após a remoção, substituí-la pelo primeiro filho
        if (root.n == 0) {
            if (!root.leaf) {
                root = root.children[0];
            } else {
                root = null;
            }
        }
    }

    private void delete(Node x, T value) {
        int idx = findKey(x, value);

        if (idx < x.n && x.keys[idx].compareTo(value) == 0) {
            // Se a chave estiver presente no nó
            if (x.leaf) {
                removeFromLeaf(x, idx);
            } else {
                removeFromNonLeaf(x, idx);
            }
        } else {
            if (x.leaf) {
                // A chave não está na árvore
                return;
            }

            // Se o nó filho onde a chave pode estar tem menos que t chaves
            if (x.children[idx].n < t) {
                fill(x, idx);
            }

            // Recursivamente tenta deletar a chave do nó correto
            delete(x.children[idx], value);
        }
    }

    private int findKey(Node x, T value) {
        int idx = 0;
        while (idx < x.n && x.keys[idx].compareTo(value) < 0) {
            idx++;
        }
        return idx;
    }

    private void removeFromLeaf(Node x, int idx) {
        // Remove a chave de um nó folha
        for (int i = idx + 1; i < x.n; i++) {
            x.keys[i - 1] = x.keys[i];
        }
        x.n--;
    }

    private void removeFromNonLeaf(Node x, int idx) {
        T value = (T) x.keys[idx];

        if (x.children[idx].n >= t) {
            T pred = getPredecessor(x, idx);
            x.keys[idx] = pred;
            delete(x.children[idx], pred);
        } else if (x.children[idx + 1].n >= t) {
            T succ = getSuccessor(x, idx);
            x.keys[idx] = succ;
            delete(x.children[idx + 1], succ);
        } else {
            merge(x, idx);
            delete(x.children[idx], value);
        }
    }

    private T getPredecessor(Node x, int idx) {
        Node current = x.children[idx];
        while (!current.leaf) {
            current = current.children[current.n];
        }
        return (T) current.keys[current.n - 1];
    }

    private T getSuccessor(Node x, int idx) {
        Node current = x.children[idx + 1];
        while (!current.leaf) {
            current = current.children[0];
        }
        return (T) current.keys[0];
    }

    private void fill(Node x, int idx) {
        if (idx != 0 && x.children[idx - 1].n >= t) {
            borrowFromPrev(x, idx);
        } else if (idx != x.n && x.children[idx + 1].n >= t) {
            borrowFromNext(x, idx);
        } else {
            if (idx != x.n) {
                merge(x, idx);
            } else {
                merge(x, idx - 1);
            }
        }
    }

    private void borrowFromPrev(Node x, int idx) {
        Node child = x.children[idx];
        Node sibling = x.children[idx - 1];

        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = x.keys[idx - 1];

        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        x.keys[idx - 1] = sibling.keys[sibling.n - 1];
        child.n++;
        sibling.n--;
    }

    private void borrowFromNext(Node x, int idx) {
        Node child = x.children[idx];
        Node sibling = x.children[idx + 1];

        child.keys[child.n] = x.keys[idx];

        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        x.keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n++;
        sibling.n--;
    }

    private void merge(Node x, int idx) {
        Node child = x.children[idx];
        Node sibling = x.children[idx + 1];

        child.keys[t - 1] = x.keys[idx];

        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + t] = sibling.keys[i];
        }

        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }

        for (int i = idx + 1; i < x.n; i++) {
            x.keys[i - 1] = x.keys[i];
        }

        for (int i = idx + 2; i <= x.n; i++) {
            x.children[i - 1] = x.children[i];
        }

        child.n += sibling.n + 1;
        x.n--;
    }
}
