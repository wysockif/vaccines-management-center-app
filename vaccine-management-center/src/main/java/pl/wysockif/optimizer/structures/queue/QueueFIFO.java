package pl.wysockif.optimizer.structures.queue;

public interface QueueFIFO<T extends Comparable<T>> {
    boolean push(T item);

    T pop();

    boolean isEmpty();

    T front();

}
