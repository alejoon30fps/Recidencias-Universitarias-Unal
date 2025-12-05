public class AVLEstudiante {
    private Nodo raiz;
    
    // Clase Interna Nodo
    class Nodo {
        Estudiante estudiante;
        // int clave; // ¡Eliminado! Usaremos estudiante.compareTo()
        Nodo izquierdo;
        Nodo derecho;
        int altura;

        public Nodo(Estudiante estudiante) {
            this.estudiante = estudiante;
            // this.clave = estudiante.hashCode(); // ¡Eliminado!
            this.altura = 1; 
        }
    }


    /**
     * Calcula el Factor de Balanceo (FB) de un nodo.
     * El FB es la diferencia entre la altura del subárbol izquierdo y el subárbol derecho.
     * FB = Altura_Izquierda - Altura_Derecha
     * * @param nodo El nodo cuya diferencia de altura se va a calcular.
     * @return El Factor de Balanceo. Debe estar entre -1 y 1 para que el nodo esté balanceado.
     */
    private int getFactorBalanceo(Nodo nodo) {
        // Si el nodo es null (caso base o árbol vacío), el factor de balanceo es 0.
        if (nodo == null) {
            return 0;
        }
        
        // Retorna la diferencia de altura, usando el método altura() para obtener 0 si el hijo es null.
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    public void insertar(Estudiante estudiante) {
        raiz = insertar(raiz, estudiante);
    }
    private Nodo insertar(Nodo nodo, Estudiante estudiante) {
        if (nodo == null) {
            return new Nodo(estudiante);
        }
        
        // Usamos compareTo() para la comparación
        int comparacion = estudiante.compareTo(nodo.estudiante);

        // 1. Inserción estándar de un Árbol Binario de Búsqueda (BST)
        if (comparacion < 0) { // El nuevo estudiante es "menor"
            nodo.izquierdo = insertar(nodo.izquierdo, estudiante);
        } else{
            nodo.derecho = insertar(nodo.derecho, estudiante);
        }

        // 2. Actualizar altura
        actualizarAltura(nodo);

        // 3. Obtener el Factor de Balanceo (FB)
        int fb = getFactorBalanceo(nodo);

        // 4. Aplicar Rotaciones (La lógica de rotación ahora usa 'comparacion' para decidir el tipo)

        // Caso LL (Left-Left)
        if (fb > 1 && estudiante.compareTo(nodo.izquierdo.estudiante) < 0) {
            return rotarDerecha(nodo);
        }

        // Caso RR (Right-Right)
        if (fb < -1 && estudiante.compareTo(nodo.derecho.estudiante) > 0) {
            return rotarIzquierda(nodo);
        }

        // Caso LR (Left-Right)
        if (fb > 1 && estudiante.compareTo(nodo.izquierdo.estudiante) > 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo); 
            return rotarDerecha(nodo);                      
        }

        // Caso RL (Right-Left)
        if (fb < -1 && estudiante.compareTo(nodo.derecho.estudiante) < 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);     
            return rotarIzquierda(nodo);                   
        }

        return nodo;
    }
    
    // ----------------------------------------------------------------------
    // Operación de Búsqueda (Modificada)
    // ----------------------------------------------------------------------
    
    /**
     * Busca un estudiante basado en su lógica de comparación natural (compareTo).
     * @param estudianteBuscado Objeto Estudiante que contiene la clave (ej. ID) a buscar.
     * @return El objeto Estudiante encontrado, o null si no existe.
     */
    public Estudiante buscar(Estudiante estudianteBuscado) {
        return buscar(raiz, estudianteBuscado);
    }

    private Estudiante buscar(Nodo nodo, Estudiante estudianteBuscado) {
        if (nodo == null) {
            return null;
        }

        // Usamos compareTo() para la comparación
        int comparacion = estudianteBuscado.compareTo(nodo.estudiante);

        if (comparacion < 0) {
            return buscar(nodo.izquierdo, estudianteBuscado);
        } else if (comparacion > 0) {
            return buscar(nodo.derecho, estudianteBuscado);
        } else {
            // La comparación es 0: Estudiante encontrado
            return nodo.estudiante;
        }
    }


    /**
     * Realiza una Rotación Simple a la Derecha (Rotación LL).
     * Se usa cuando el FB es > 1 y el FB del hijo izquierdo es >= 0.
     * @param y El nodo desbalanceado (padre).
     * @return El nuevo nodo raíz del subárbol rotado.
     */
    private Nodo rotarDerecha(Nodo y) {
        // 1. Definir los nodos involucrados
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho; // T2 es el subárbol que pasa de 'x' a 'y'

        // 2. Realizar la rotación (cambio de punteros)
        x.derecho = y;
        y.izquierdo = T2;

        // 3. Actualizar alturas (IMPORTANTE: primero los nodos inferiores)
        actualizarAltura(y);
        actualizarAltura(x);

        return x; // 'x' es la nueva raíz del subárbol.
    }

    /**
     * Realiza una Rotación Simple a la Izquierda (Rotación RR).
     * Se usa cuando el FB es < -1 y el FB del hijo derecho es <= 0.
     * @param x El nodo desbalanceado (padre).
     * @return El nuevo nodo raíz del subárbol rotado.
     */
    private Nodo rotarIzquierda(Nodo x) {
        // 1. Definir los nodos involucrados
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo; // T2 es el subárbol que pasa de 'y' a 'x'

        // 2. Realizar la rotación (cambio de punteros)
        y.izquierdo = x;
        x.derecho = T2;

        // 3. Actualizar alturas (IMPORTANTE: primero los nodos inferiores)
        actualizarAltura(x);
        actualizarAltura(y);

        return y; // 'y' es la nueva raíz del subárbol.
    }

    /**
     * Actualiza la altura de un nodo después de una operación (inserción o rotación).
     * La altura de un nodo es 1 (el nodo actual) más la altura MÁXIMA de sus hijos.
     * * @param nodo El nodo cuya altura debe ser recalculada.
     */
    private void actualizarAltura(Nodo nodo) {
        if (nodo != null) {
            // Obtenemos la altura del subárbol izquierdo (0 si es null).
            int alturaIzquierda = altura(nodo.izquierdo); 
            // Obtenemos la altura del subárbol derecho (0 si es null).
            int alturaDerecha = altura(nodo.derecho);
            
            // La nueva altura es 1 + el máximo de las alturas de los hijos.
            nodo.altura = 1 + Math.max(alturaIzquierda, alturaDerecha);
        }
    }

    /**
     * Obtiene la altura de un nodo. Retorna 0 si el nodo es null (altura de un árbol vacío).
     */
    private int altura(Nodo nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

}


