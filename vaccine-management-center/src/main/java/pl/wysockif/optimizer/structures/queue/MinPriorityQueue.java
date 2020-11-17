package pl.wysockif.optimizer.structures.queue;

import pl.wysockif.optimizer.structures.binaryheap.MinHeap;
import pl.wysockif.optimizer.structures.binaryheap.HeapInterface;

public class MinPriorityQueue<T extends Comparable<T>> implements PriorityQueueInterface<T> {
    private final HeapInterface<T> binaryHeap;

    public MinPriorityQueue() {
        binaryHeap = new MinHeap<>();
    }

    @Override
    public void insert(T item) {
        binaryHeap.put(item);
    }

    @Override
    public T minimum() {
        return binaryHeap.getMinimum();
    }

    @Override
    public T extractMin() {
        return binaryHeap.pop();
    }

    @Override
    public boolean isEmpty() {
        return binaryHeap.isEmpty();
    }
}
