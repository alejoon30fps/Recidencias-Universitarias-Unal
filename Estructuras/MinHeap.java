package Estructuras;
public class MinHeap<T extends Comparable<T> & getAndStIdHeap<T>> {
    
    // El tipo T debe ser Comparable (para ordenar) Y debe implementar getAndStIdHeap 
    // (para obtener y establecer su índice dentro del array del Heap).
    
    private Object[] heap;      // Array que almacena los elementos del Heap.
    private int size;           // Número actual de elementos en el Heap.
    private int capacity=16;    // Capacidad máxima actual del array 'heap'.

    /**
     * Constructor del MinHeap.
     * @param capacity Capacidad inicial deseada para el array.
     */
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Object[capacity];
        this.size = 0;
    }

    // ----------------------------------------------------------------------
    // Métodos de Información
    // ----------------------------------------------------------------------

    /**
     * Devuelve el número actual de elementos en el Heap.
     * @return El tamaño actual (size).
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si el Heap está vacío.
     * @return true si el tamaño es 0, false en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Método auxiliar para obtener un elemento del array con un casting seguro (unchecked).
     * @param idx Índice del elemento a obtener.
     * @return El elemento de tipo T en el índice dado.
     */
    @SuppressWarnings("unchecked")
    private T get(int idx) {
        return (T) heap[idx];
    }

    // ----------------------------------------------------------------------
    // Operaciones Fundamentales
    // ----------------------------------------------------------------------

    /**
     * Inserta un nuevo valor en el MinHeap.
     * Complejidad: O(log n)
     * @param valor El elemento de tipo T a insertar.
     */
    public void insert(T valor) {
        // 1. Si el Heap está lleno, redimensiona el array.
        if (size == capacity) resize();      
        
        // 2. Coloca el nuevo elemento al final (en la primera posición vacía).
        heap[size] = valor;
        
        // 3. Actualiza el índice del elemento DENTRO del propio objeto.
        valor.setIndexHeap(size);
        
        // 4. Mueve el elemento hacia arriba para restaurar la propiedad de Min-Heap.
        siftUp(size);
        
        // 5. Incrementa el contador de tamaño.
        size++;
    }

    /**
     * Extrae y devuelve el elemento más pequeño (la raíz del Heap).
     * Complejidad: O(log n)
     * @return El elemento mínimo de tipo T, o null si está vacío.
     */
    public T extractMin() {
        if (isEmpty()) return null;

        // 1. Guarda el elemento mínimo (raíz).
        T min = get(0);
        T ultimo = get(size - 1);
        
        // 2. Mueve el último elemento a la posición de la raíz.
        heap[0] = ultimo;
        if (ultimo != null) {
            ultimo.setIndexHeap(0);
        }
        
        // 3. Reduce el tamaño. El elemento mínimo se pierde.
        heap[size - 1] = null; // Opcional: Limpia la referencia para GC.
        size--;

        // 4. Mueve el nuevo elemento en la raíz hacia abajo para restaurar la propiedad.
        siftDown(0);
        
        // 5. Devuelve el mínimo extraído.
        min.setIndexHeap(-1); // Marca como eliminado.
        return min;
    }

    // ----------------------------------------------------------------------
    // Operaciones Avanzadas (Dependientes del índice)
    // ----------------------------------------------------------------------

    /**
     * Elimina un elemento específico del Heap.
     * Complejidad: O(log n)
     * @param valor El elemento T a eliminar (debe tener un índice válido).
     * @return true si el elemento fue encontrado y eliminado, false en caso contrario.
     */
    public boolean remove(T valor) {
        // 1. Usa la interfaz para obtener la posición actual del elemento en O(1).
        int idx = valor.getIndexHeap();
        if (idx == -1 || idx >= size) return false; // El elemento no está en el Heap.
        // 2. Obtiene el último elemento para reemplazar el eliminado.
        int lastIndex = size - 1;
        T ultimo = get(lastIndex);

        // 3. Reduce el tamaño y caso especial si es el último elemento.
        size--;
        // Si estamos eliminando el último elemento, solo lo removemos.
        if(idx == lastIndex){
            heap[lastIndex] = null;
            valor.setIndexHeap(-1);
            return true;
        }
        // Mover el ultimo elemento a la posición eliminada.
        heap[idx] = ultimo;
        ultimo.setIndexHeap(idx);

        heap[lastIndex] = null; // Limpia la referencia para GC.

        // 4. Intenta restaurar la propiedad de Heap desde la nueva posición 'idx'.
        //    * siftUp: si el último elemento es menor que el padre.
        siftUp(idx);
        //    * siftDown: si el último elemento es mayor que alguno de los hijos.
        siftDown(idx);

        // 5. Se marca como eliminado estableciendo su índice a -1.
        valor.setIndexHeap(-1);
        return true;
    }

    /**
     * Actualiza la clave (valor) de un elemento existente, manteniendo la estructura.
     * Complejidad: O(log n)
     * @param viejo El objeto existente en el Heap.
     * @param nuevo El nuevo objeto (o el mismo objeto con un valor modificado).
     * @return true si la clave fue modificada y el Heap reestructurado.
     */
    public boolean changeKey(T viejo, T nuevo) {
        // 1. Obtiene la posición del elemento a modificar en O(1).
        int idx = viejo.getIndexHeap();
        if (idx == -1) return false;

        // 2. Reemplaza el objeto en la posición 'idx'.
        heap[idx] = nuevo;
        
        // 3. El nuevo elemento debe saber su índice.
        nuevo.setIndexHeap(idx); 

        // 4. Restaura la propiedad del Heap. Dependiendo de si la clave aumentó o disminuyó, 
        //    el elemento se moverá hacia arriba (siftUp) o hacia abajo (siftDown).
        //    Ambos se llaman porque solo uno tendrá efecto.
        siftUp(idx);
        siftDown(idx);

        return true;
    }

    // ----------------------------------------------------------------------
    // Métodos de Reestructuración (Heapify)
    // ----------------------------------------------------------------------

    /**
     * Mueve el elemento en el índice 'idx' hacia ARRIBA (hacia la raíz) si es menor que su padre.
     * Complejidad: O(log n)
     * @param idx El índice inicial del elemento a mover.
     */
    private void siftUp(int idx) {
        while (idx > 0) {
            // El padre siempre está en (i - 1) / 2.
            int parent = (idx - 1) / 2;

            T child = get(idx);
            T par   = get(parent);

            // Si el hijo es mayor o igual al padre, la propiedad de Min-Heap se cumple. Detener.
            if (child.compareTo(par) >= 0) break;

            // Intercambiar el hijo con el padre.
            swap(idx, parent);
            
            // Moverse al índice del padre para continuar la comparación hacia arriba.
            idx = parent;
        }
    }

    /**
     * Mueve el elemento en el índice 'idx' hacia ABAJO (hacia las hojas) si es mayor que alguno de sus hijos.
     * Complejidad: O(log n)
     * @param idx El índice inicial del elemento a mover.
     */
    private void siftDown(int idx) {
        while (true) {
            // Índices de los hijos.
            int izq = 2 * idx + 1;
            int der = 2 * idx + 2;
            // 'menor' rastrea el índice del elemento más pequeño entre el padre y sus hijos.
            int menor = idx;

            // 1. Compara con el hijo izquierdo. Si es más pequeño, actualiza 'menor'.
            if (izq < size && get(izq).compareTo(get(menor)) < 0) menor = izq;
            
            // 2. Compara con el hijo derecho. Si es más pequeño que el actual 'menor' (padre o izq), actualiza 'menor'.
            if (der < size && get(der).compareTo(get(menor)) < 0) menor = der;

            // Si el padre sigue siendo el menor, la propiedad de Heap se cumple.
            if (menor == idx) break;

            // Intercambiar el padre con el hijo menor.
            swap(idx, menor);
            
            // Moverse al índice del hijo menor para continuar el proceso.
            idx = menor;
        }
    }

    // ----------------------------------------------------------------------
    // Métodos Auxiliares
    // ----------------------------------------------------------------------

    /**
     * Intercambia dos elementos en las posiciones 'a' y 'b'.
     * ESENCIAL: Actualiza el índice DENTRO de los objetos T.
     * @param a Índice del primer elemento.
     * @param b Índice del segundo elemento.
     */
    private void swap(int a, int b) {
        // Obtiene referencias a los objetos para poder actualizar sus índices.
        T temp1 = get(a);
        T temp2 = get(b);

        // 1. Actualiza los índices almacenados DENTRO de los objetos T.
        temp1.setIndexHeap(b); // temp1 se va a la posición 'b'.
        temp2.setIndexHeap(a); // temp2 se va a la posición 'a'.

        // 2. Realiza el intercambio físico de los elementos en el array.
        Object temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
        
    }

    /**
     * Duplica la capacidad del array 'heap' si el MinHeap se llena.
     */
    private void resize() {
        // Duplica la capacidad.
        capacity *= 2;
        Object[] nuevo = new Object[capacity];

        // Copia los elementos del array viejo al nuevo.
        for (int i = 0; i < size; i++) {
            nuevo[i] = heap[i];
        }
        
        // Asigna el nuevo array.
        heap = nuevo;
    }


}
