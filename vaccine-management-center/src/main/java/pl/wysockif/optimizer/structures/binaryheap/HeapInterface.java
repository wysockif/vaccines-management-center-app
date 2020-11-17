package pl.wysockif.optimizer.structures.binaryheap;

public interface HeapInterface<T extends Comparable<T>> {
    void put(T item);

    T pop();

    T getMinimum();

    boolean isEmpty();

}
