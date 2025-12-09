package Estructuras;

public class AVLEstudiante {
    private Nodo raiz;
    
    // Clase Interna Nodo
    public class Nodo {
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

    // Retornar la raíz del AVL (para pruebas o recorridos externos)
    public Nodo getRoot() {
        return raiz;
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
    // Operación de Borrar 
    // ----------------------------------------------------------------------
    

    public boolean remove(Estudiante estudiante) {
        boolean[] eliminado = new boolean[]{false};
        raiz = eliminar(raiz, estudiante, eliminado);
        return eliminado[0];
    }

    private Nodo eliminar(Nodo nodo, Estudiante estudiante, boolean[] eliminado) {

        if (nodo == null) {
            return null;
        }

    int cmp = estudiante.compareTo(nodo.estudiante);

    // 1. Buscar el nodo a eliminar
    if (cmp < 0) {
        nodo.izquierdo = eliminar(nodo.izquierdo, estudiante, eliminado);

    } else if (cmp > 0) {
        nodo.derecho = eliminar(nodo.derecho, estudiante, eliminado);

    } else {
        // Nodo encontrado
        eliminado[0] = true;

        // Caso 1: nodo con 0 o 1 hijo
        if (nodo.izquierdo == null || nodo.derecho == null) {
            Nodo temp = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;

            // Sin hijos
            if (temp == null) {
                return null;
            }

            // Un hijo
            nodo = temp;

        } else {
            // Caso 2: nodo con dos hijos
            Nodo sucesor = minValueNode(nodo.derecho);
            nodo.estudiante = sucesor.estudiante;
            nodo.derecho = eliminar(nodo.derecho, sucesor.estudiante, eliminado);
        }
    }

    // Recalcular altura
    actualizarAltura(nodo);

    // Rebalanceo
    int fb = getFactorBalanceo(nodo);

    // LL
    if (fb > 1 && getFactorBalanceo(nodo.izquierdo) >= 0) {
        return rotarDerecha(nodo);
    }

    // LR
    if (fb > 1 && getFactorBalanceo(nodo.izquierdo) < 0) {
        nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
        return rotarDerecha(nodo);
    }

    // RR
    if (fb < -1 && getFactorBalanceo(nodo.derecho) <= 0) {
        return rotarIzquierda(nodo);
    }

    // RL
    if (fb < -1 && getFactorBalanceo(nodo.derecho) > 0) {
        nodo.derecho = rotarDerecha(nodo.derecho);
        return rotarIzquierda(nodo);
    }

    return nodo;
}

/**
 * Devuelve el nodo con el valor mínimo del subárbol.
 */
private Nodo minValueNode(Nodo nodo) {
    Nodo actual = nodo;
    while (actual.izquierdo != null) {
        actual = actual.izquierdo;
    }
    return actual;
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

    /**
     * Obtiene la cantidad de nodos que se tienen.
     */
    public int totalNodos() {
        return totalNodos(raiz);
    }

    private int totalNodos(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + totalNodos(nodo.izquierdo) + totalNodos(nodo.derecho);
    }

    /**
     * Obtenemos el Estudiante en la posición 'index' del recorrido In-Order.
     * @param index Índice (0-based) en el recorrido In-Order.
     */
    public Estudiante getByIndexInOrder(int index){
    return getByIndexInOrder(raiz, new int[]{index});
    }

    private Estudiante getByIndexInOrder(Nodo nodo, int[] index){
    if (nodo == null) return null;

    Estudiante left = getByIndexInOrder(nodo.izquierdo, index);
    if (left != null) return left;

    if (index[0] == 0) return nodo.estudiante;
    index[0]--;

    return getByIndexInOrder(nodo.derecho, index);
    }
    // ----------------------------------------------------------------------
    // Operación de camnbiar el Valor de un nodo (Implicita cuando se cambia el valor en las demas estructuras)
    // ----------------------------------------------------------------------
    public boolean changeKey(Estudiante viejo,int nuevoPbm) {
    // 1. Verificar que el viejo existe
    Estudiante encontrado = buscar(viejo);
    if (encontrado == null) {
        return false;
    }

        // Se emplean metodos anteriormente ralizados    
    // 2. Eliminar el viejo
    remove(viejo);

    encontrado.setPbm(nuevoPbm);
    // 3. Insertar el nuevo
    insertar(encontrado);

    return true;
}

    public void printInOrder() {
        printInOrder(raiz);
        System.out.println();
    }

    private void printInOrder(Nodo nodo) {
        if (nodo == null) return;
        printInOrder(nodo.izquierdo);
        System.out.print(nodo.estudiante.getPbm() + " ");
        printInOrder(nodo.derecho);
    }

    @SuppressWarnings("rawtypes")
    public String[] listaInOrder(UniversalHashTable aceptados) {

    // Usamos StringBuilder para eficiencia
    StringBuilder aceptadosSB = new StringBuilder();
    StringBuilder noAceptadosSB = new StringBuilder();

    // Iniciar el recorrido
    listaInOrderRec(raiz, aceptados, aceptadosSB, noAceptadosSB);

    // Convertimos a String final SIN concatenación pesada
    return new String[] {
        aceptadosSB.toString(),
        noAceptadosSB.toString()
    };
}

/**
 * Método recursivo para recorrer el AVL In-Order (Izquierda-Nodo-Derecha)
 * y clasificar los estudiantes en dos listas.
 */
    @SuppressWarnings("rawtypes")
    private void listaInOrderRec(Nodo nodo, UniversalHashTable aceptados,
                             StringBuilder aceptadosSB, StringBuilder noAceptadosSB) {
        if (nodo == null) return;

        // 1. Recorre subárbol Izquierdo
         listaInOrderRec(nodo.izquierdo, aceptados, aceptadosSB, noAceptadosSB);

        // 2. Procesa el Nodo Actual (Estudiante del AVL)
        Estudiante estudianteAVL = nodo.estudiante; 
        
        // Verifica si este estudiante existe en la HashTable de aceptados usando su ID como clave.
        // La clave usada en get debe ser el mismo tipo que se usa para almacenar en la tabla.
        Estudiante estudianteEnHash = (Estudiante) aceptados.get(estudianteAVL.getId()); 

        if (estudianteEnHash != null) {
            // El estudiante está en la HashTable: Aceptado.
            aceptadosSB.append(estudianteAVL.toString()).append("\n");
        } else {
            // El estudiante NO está en la HashTable: No Aceptado.
            noAceptadosSB.append(estudianteAVL.toString()).append("\n");
        }

        // 3. Recorre subárbol Derecho
        listaInOrderRec(nodo.derecho, aceptados, aceptadosSB, noAceptadosSB); 
    }

    public String listarPrioridad() {
    StringBuilder sb = new StringBuilder();
    listarPrioridadRec(raiz, sb);
    return sb.toString();
}

private void listarPrioridadRec(Nodo nodo, StringBuilder sb) {
    if (nodo == null) return;

    // Inorder: Izquierdo - Actual - Derecho
    listarPrioridadRec(nodo.izquierdo, sb);
    sb.append(nodo.estudiante.toString()).append("\n");
    listarPrioridadRec(nodo.derecho, sb);
}


}
