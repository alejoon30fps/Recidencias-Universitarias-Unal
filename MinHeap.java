public class MinHeap<T extends Comparable<T> & getAndStIdHeap<T>> {
    private Object[] heap;
    private int size;
    private int capacity=16;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Object[capacity];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private T get(int idx) {
        return (T) heap[idx];
    }

    public void insert(T valor) {
        if (size == capacity) resize();      
        heap[size] = valor;
        valor.setIndexHeap(size);
        siftUp(size);
        size++;
    }

    public T extractMin() {
        if (isEmpty()) return null;

        T min = get(0);
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    public boolean remove(T valor) {
        int idx = valor.getIndexHeap();
        if (idx == -1) return false;

        heap[idx] = heap[size - 1];
        size--;

        siftUp(idx);
        siftDown(idx);

        return true;
    }

    public boolean changeKey(T viejo, T nuevo) {
        
        int idx = viejo.getIndexHeap();
        if (idx == -1) return false;

        heap[idx] = nuevo;

        siftUp(idx);
        siftDown(idx);

        return true;
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;

            T child = get(idx);
            T par   = get(parent);

            if (child.compareTo(par) >= 0) break;

            swap(idx, parent);
            
            idx = parent;
        }
    }

    private void siftDown(int idx) {
        while (true) {
            int izq = 2 * idx + 1;
            int der = 2 * idx + 2;
            int menor = idx;

            if (izq < size && get(izq).compareTo(get(menor)) < 0) menor = izq;
            if (der < size && get(der).compareTo(get(menor)) < 0) menor = der;

            if (menor == idx) break;

            swap(idx, menor);
            idx = menor;
        }
    }

    private void swap(int a, int b) {
        T temp1,temp2;

        temp1= get(a);
        temp2=get(b);

        temp1.setIndexHeap(b);
        temp2.setIndexHeap(a);
        
        Object temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;

    }

    private void resize() {
        capacity *= 2;
        Object[] nuevo = new Object[capacity];

        for (int i = 0; i < size; i++) {
            nuevo[i] = heap[i];
        }

        heap = nuevo;
    }
}
