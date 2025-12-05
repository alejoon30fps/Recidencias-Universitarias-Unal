public class UniversalHashTable<T> {

    // --- Parámetros para Hashing Universal ---
    // Estos valores definen la función hash utilizada y deben elegirse
    // para garantizar una baja probabilidad de colisión (hashing universal).
    private final long a = 1234567L;          // Constante 'a' fija (como si fuera seleccionada aleatoriamente al inicio)
    private final long b = 891011L;           // Constante 'b' fija (como si fuera seleccionada aleatoriamente al inicio)
    private final long p = 1_000_000_007L;    // Número primo grande, debe ser mayor que el rango de las posibles claves de entrada.

    // --- Atributos de la Tabla ---
    private int m = 16;                       // Tamaño inicial de la tabla (capacidad). Se duplica en cada rehash.
    private int size = 0;                     // Cantidad actual de elementos almacenados en la tabla.
    private final float loadFactorLimit =(float) 0.75; // Límite que, si se alcanza, dispara el proceso de rehash.

    private Object[] table;                   // El array subyacente que almacena los elementos.

    // ----------------------------------------------------------------------

    /**
     * Constructor de la tabla de hash.
     * Inicializa el array de almacenamiento con la capacidad inicial 'm'.
     */
    public UniversalHashTable() { 
        table = new Object[m];
    }

    // ----------------------------------------------------------------------

    /**
     * Función de Hashing Universal.
     * Calcula la posición (índice) donde debe almacenarse un elemento.
     * * @param x El valor hash de la clave (key) del elemento (e.g., value.hashCode()).
     * @return El índice del array (de 0 a m-1) donde se ubicará el elemento.
     */
    private int hash(int x) {
        // Convierte el entero (int) de 32 bits a un long (64 bits) para asegurar que 
        // la multiplicación (a * key) no cause un desbordamiento de enteros (overflow) 
        // antes de aplicar el módulo 'p'.
        long key = x & 0xFFFFFFFFL;  
        
        // Aplica el primer módulo con el primo 'p' (Método de Multiplicación-Módulo):
        // r = (a * key + b) mod p
        long r = (a * key + b) % p;
        
        // Aplica el segundo módulo con el tamaño de la tabla 'm' para obtener el índice final.
        // Índice = r mod m
        return (int) (r % m);
    }

    // ----------------------------------------------------------------------

    /**
     * Proceso de Reestructuración y Redimensionamiento (Rehashing).
     * Duplica la capacidad de la tabla cuando el factor de carga es demasiado alto 
     * y reubica todos los elementos en la nueva tabla usando la nueva función hash.
     */
    private void rehash() { 
        // Guarda la referencia a la tabla antigua antes de redimensionar.
        Object[] old = table;

        // 1. Duplica el tamaño de la capacidad de la tabla (m).
        m = m * 2;
        // 2. Crea el nuevo array con el doble de capacidad.
        table = new Object[m];
        // 3. Reinicia el contador de elementos. Se incrementará en el bucle al reinsertar.
        size = 0;

        // 4. Itera sobre la tabla antigua para reinsertar todos los elementos.
        for (int i = 0; i < old.length; i++) {
            // Solo procesa las celdas que contienen un elemento (no nulas).
            if (old[i] != null) {
                @SuppressWarnings("unchecked")
                T value = (T) old[i];
                
                // La clave (key) utilizada es el código hash del objeto.
                int key = value.hashCode();
                
                // Vuelve a insertar el elemento. Este 'insert' utilizará 
                // el nuevo valor de 'm' en la función hash, ubicándolo correctamente 
                // en la nueva y más grande tabla.
                insert(key, value);
            }
        }
    }

    // ----------------------------------------------------------------------

    /**
     * Inserta un elemento en la tabla de hash.
     * * @param key El código hash del elemento (e.g., value.hashCode()).
     * @param value El valor del elemento a almacenar.
     */
    public void insert(int key, T value) {
        // Calcula el factor de carga actual (elementos / capacidad).
        double load = (double) size / m;

        // Verifica si el factor de carga excede el límite. Si es así, redimensiona.
        if (load >= loadFactorLimit) {
            rehash();
        }

        // Calcula el índice donde debe ir el elemento en la tabla actual.
        int idx = hash(key);
        
        // Almacena el valor. 
        // NOTA: Esta implementación usa un método simple de sondeo que no maneja colisiones 
        // eficazmente (el nuevo valor simplemente reemplaza al que existía en ese índice).
        table[idx] = value;
        
        // Incrementa el contador de elementos almacenados.
        size++;
    }

    // ----------------------------------------------------------------------

    /**
     * Recupera un elemento de la tabla usando su clave hash.
     * * @param key El código hash del elemento a buscar.
     * @return El valor T asociado a la clave, o null si la posición está vacía.
     */
    @SuppressWarnings("unchecked")
    public T get(int key) {
        // Calcula el índice del elemento.
        int idx = hash(key);
        
        // Devuelve el elemento en esa posición, haciendo un casting.
        // NOTA: Debido a la falta de manejo de colisiones, este método solo funciona 
        // correctamente si el elemento se encuentra exactamente en el índice calculado.
        return (T) table[idx];
    }

    // ----------------------------------------------------------------------

    /**
     * Obtiene el número de elementos actualmente almacenados en la tabla.
     * * @return El número de elementos ('size').
     */
    public int size() {
        return size;
    }

    // ----------------------------------------------------------------------

    /**
     * Obtiene la capacidad total actual del array subyacente.
     * * @return El tamaño del array ('m').
     */
    public int capacity() {
        return m;
    }
}