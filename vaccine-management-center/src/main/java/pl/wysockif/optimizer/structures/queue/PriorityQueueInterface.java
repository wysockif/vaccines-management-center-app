package pl.wysockif.optimizer.structures.queue;

public interface PriorityQueueInterface<T extends Comparable<T>> {
    void insert(T item);

    T minimum();

    T extractMin();

    boolean isEmpty();

}
