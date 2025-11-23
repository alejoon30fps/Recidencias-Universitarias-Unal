public class MinHeap<T extends Comparable<T>> {

    private Object[] heap;
    private int size;

    public MinHeap(int capacity) {
        this.heap = new Object[capacity];
        this.size = 0;
    }

    public void insert(T value) {
        if (size == heap.length) expand();
        heap[size] = value;
        siftUp(size);
        size++;
    }

    public T extractMin() {
        if (size == 0) return null;

        T min = (T) heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);

        return min;
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;

            T child = (T) heap[idx];
            T par   = (T) heap[parent];

            if (child.compareTo(par) >= 0) break;

            swap(idx, parent);
            idx = parent;
        }
    }

    private void siftDown(int idx) {
        while (true) {
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int smallest = idx;

            if (left < size) {
                T l = (T) heap[left];
                T c = (T) heap[smallest];
                if (l.compareTo(c) < 0) smallest = left;
            }

            if (right < size) {
                T r = (T) heap[right];
                T c = (T) heap[smallest];
                if (r.compareTo(c) < 0) smallest = right;
            }

            if (smallest == idx) break;

            swap(idx, smallest);
            idx = smallest;
        }
    }

    private void swap(int a, int b) {
        Object tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    private void expand() {
        Object[] newArr = new Object[heap.length * 2];
        System.arraycopy(heap, 0, newArr, 0, heap.length);
        heap = newArr;
    }
}
