package ArvoreRubroNegra;

public class RedBlackTree <T extends Comparable<T>> extends AbstractBinarySearchTree<T>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    @Override
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        root = insertRec(root, newNode);
        balanceAfterInsert(newNode);
    }
    private Node<T> insertRec(Node<T> root, Node<T> node) {
        if (root == null) {
            return node;
        }
        if (node.data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, node);
            root.left.parent = root;
        } else if (node.data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, node);
            root.right.parent = root;
        }
        return root;
    }

    @Override
    public void balanceAfterInsert(Node<T> node) {
        Node<T> parent = null;
        Node<T> grandParent = null;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            // Caso A: O pai do nó é o filho esquerdo do avô
            if (parent == grandParent.left) {
                Node<T> uncle = grandParent.right;

                // Caso 1: O tio é vermelho (recolorir)
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    // Caso 2: O nó é o filho direito (rotação à esquerda)
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Caso 3: O nó é o filho esquerdo (rotação à direita)
                    rotateRight(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            } else { // Caso B: O pai do nó é o filho direito do avô
                Node<T> uncle = grandParent.left;

                // Caso 1: O tio é vermelho (recolorir)
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    // Caso 2: O nó é o filho esquerdo (rotação à direita)
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Caso 3: O nó é o filho direito (rotação à esquerda)
                    rotateLeft(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            }
        }
        root.color = BLACK;
    }
    @Override
    public void delete(T data) {
        Node<T> nodeToDelete = searchTree(root, data);
        if (nodeToDelete == null) {
            return; // Não encontrou o nó para remover
        }

        Node<T> y = nodeToDelete;
        Node<T> x;
        boolean yOriginalColor = y.color;

        // Caso 1: Nó a ser removido não tem filhos ou tem um filho
        if (nodeToDelete.left == null) {
            x = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == null) {
            x = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        } else {
            // Caso 2: O nó tem dois filhos
            y = minimum(nodeToDelete.right); // Sucessor
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == nodeToDelete) {
                if (x != null) {
                    x.parent = y;
                }
            } else {
                transplant(y, y.right);
                y.right = nodeToDelete.right;
                y.right.parent = y;
            }

            transplant(nodeToDelete, y);
            y.left = nodeToDelete.left;
            y.left.parent = y;
            y.color = nodeToDelete.color;
        }

        if (yOriginalColor == BLACK) {
            balanceAfterDelete(x);
        }
    }
    // Transplanta subárvores
    private void transplant(Node<T> u, Node<T> v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    // Encontra o menor nó (sucessor)
    private Node<T> minimum(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public void balanceAfterDelete(Node<T> x) {
        while (x != root && getColor(x) == BLACK) {
            if (x == x.parent.left) {
                Node<T> sibling = x.parent.right;
                // Caso 1: O irmão de x é vermelho
                if (getColor(sibling) == RED) {
                    sibling.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    sibling = x.parent.right;
                }

                // Caso 2: O irmão de x é preto e ambos os filhos de sibling são pretos
                if (getColor(sibling.left) == BLACK && getColor(sibling.right) == BLACK) {
                    sibling.color = RED;
                    x = x.parent;
                } else {
                    // Caso 3: O irmão de x é preto, o filho esquerdo de sibling é vermelho e o direito é preto
                    if (getColor(sibling.right) == BLACK) {
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = x.parent.right;
                    }

                    // Caso 4: O irmão de x é preto e o filho direito de sibling é vermelho
                    sibling.color = x.parent.color;
                    x.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                // Simétrico ao caso anterior
                Node<T> sibling = x.parent.left;
                if (getColor(sibling) == RED) {
                    sibling.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    sibling = x.parent.left;
                }

                if (getColor(sibling.right) == BLACK && getColor(sibling.left) == BLACK) {
                    sibling.color = RED;
                    x = x.parent;
                } else {
                    if (getColor(sibling.left) == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        rotateLeft(sibling);
                        sibling = x.parent.left;
                    }

                    sibling.color = x.parent.color;
                    x.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }
    private boolean getColor(Node<T> node) {
        if (node == null) {
            return BLACK; // Nulos são pretos
        }
        return node.color;
    }
    private void rotateLeft(Node<T> node) {
        Node<T> temp = node.right;
        node.right = temp.left;
        if (temp.left != null) temp.left.parent = node;
        temp.parent = node.parent;
        if (node.parent == null) root = temp;
        else if (node == node.parent.left) node.parent.left = temp;
        else node.parent.right = temp;
        temp.left = node;
        node.parent = temp;
    }
    private void rotateRight(Node<T> node) {
        Node<T> temp = node.left;
        node.left = temp.right;
        if (temp.right != null) temp.right.parent = node;
        temp.parent = node.parent;
        if (node.parent == null) root = temp;
        else if (node == node.parent.right) node.parent.right = temp;
        else node.parent.left = temp;
        temp.right = node;
        node.parent = temp;
    }
}
