public class UniversalHashTable<T> {

    private final long a = 1234567L;          // fijo
    private final long b = 891011L;           // fijo
    private final long p = 1_000_000_007L;    // primo > ID colombiano máximo

    private int m = 16;               // tamaño inicial
    private int size = 0;             // elementos almacenados
    private final double loadFactorLimit = 0.75;

    private Object[] table;

    public UniversalHashTable() {
        table = new Object[m];
    }

    private int hash(int x) {
        long key = x & 0xFFFFFFFFL;  
        long r = (a * key + b) % p;
        return (int) (r % m);
    }

    private void rehash() {
        Object[] old = table;

        m = m * 2;
        table = new Object[m];
        size = 0;

        for (int i = 0; i < old.length; i++) {
            if (old[i] != null) {
                @SuppressWarnings("unchecked")
                T value = (T) old[i];
                int key = value.hashCode();
                insert(key, value);
            }
        }
    }

    public void insert(int key, T value) {
        double load = (double) size / m;

        if (load >= loadFactorLimit) {
            rehash();
        }

        int idx = hash(key);
        table[idx] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int key) {
        return (T) table[hash(key)];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return m;
    }
}
