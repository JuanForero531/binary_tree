package p10;

import java.util.InputMismatchException;
import java.util.Scanner;

class Nodo {
    int valor;
    Nodo izquierdo;
    Nodo derecho;
    int altura;

    Nodo(int valor) {
        this.valor = valor;
        altura = 1;
    }
}

class AVLTree {
    Nodo raiz;

    int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    int obtenerFactorEquilibrio(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertar(nodo.derecho, valor);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        int factorEquilibrio = obtenerFactorEquilibrio(nodo);

        if (factorEquilibrio > 1 && valor < nodo.izquierdo.valor) {
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && valor > nodo.derecho.valor) {
            return rotacionIzquierda(nodo);
        }

        if (factorEquilibrio > 1 && valor > nodo.izquierdo.valor) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && valor < nodo.derecho.valor) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    Nodo obtenerNodoMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    Nodo eliminar(Nodo nodo, int valor) {
        if (nodo == null) {
            return nodo;
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = eliminar(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = eliminar(nodo.derecho, valor);
        } else {
            if ((nodo.izquierdo == null) || (nodo.derecho == null)) {
                Nodo temp = null;
                if (temp == nodo.izquierdo) {
                    temp = nodo.derecho;
                } else {
                    temp = nodo.izquierdo;
                }

                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                Nodo temp = obtenerNodoMinimo(nodo.derecho);
                nodo.valor = temp.valor;
                nodo.derecho = eliminar(nodo.derecho, temp.valor);
            }
        }

        if (nodo == null) {
            return nodo;
        }

        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;

        int factorEquilibrio = obtenerFactorEquilibrio(nodo);

        if (factorEquilibrio > 1 && obtenerFactorEquilibrio(nodo.izquierdo) >= 0) {
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio > 1 && obtenerFactorEquilibrio(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && obtenerFactorEquilibrio(nodo.derecho) <= 0) {
            return rotacionIzquierda(nodo);
        }

        if (factorEquilibrio < -1 && obtenerFactorEquilibrio(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    void preOrden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preOrden(nodo.izquierdo);
            preOrden(nodo.derecho);
        }
    }

    void ejecutar() {
        AVLTree arbol = new AVLTree();

        
        arbol.raiz = arbol.insertar(arbol.raiz, 10);
        arbol.raiz = arbol.insertar(arbol.raiz, 20);
        arbol.raiz = arbol.insertar(arbol.raiz, 30);
        arbol.raiz = arbol.insertar(arbol.raiz, 40);
        arbol.raiz = arbol.insertar(arbol.raiz, 50);
        arbol.raiz = arbol.insertar(arbol.raiz, 25);

        System.out.println("Arbol AVL inicial:");
        arbol.preOrden(arbol.raiz);
        System.out.println();

        Scanner sc = new Scanner(System.in);
        boolean valorValido = false;

        while (!valorValido) {
            try {
                System.out.print("Ingrese el valor del nodo que desea eliminar: ");
                int valorEliminar = sc.nextInt();
                arbol.raiz = arbol.eliminar(arbol.raiz, valorEliminar);
                System.out.println("Arbol AVL despues de eliminar el nodo " + valorEliminar + ":");
                arbol.preOrden(arbol.raiz);
                valorValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero entero.");
                sc.nextLine(); 
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree();
        arbol.ejecutar();
    }
}