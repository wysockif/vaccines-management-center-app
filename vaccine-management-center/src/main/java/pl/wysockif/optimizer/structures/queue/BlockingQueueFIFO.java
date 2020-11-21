package pl.wysockif.optimizer.structures.queue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueueFIFO<T extends Comparable<T>> implements QueueFIFO<T> {
    private final List<T> queue;

    public BlockingQueueFIFO() {
        queue = new LinkedList<>();
    }

    @Override
    public boolean push(T item) {
        if (queue.contains(item))
            return true;
        return queue.add(item);
    }

    @Override
    public T pop() {
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Nie można usuwać elementów z pustej kolejki");
        }
        return queue.remove(0);
    }


    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public T front() {
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Nie można zwrócić pierwszego elementu gdy kolejka jest pusta");
        }
        return queue.get(0);
    }
}
