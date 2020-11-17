package pl.wysockif.optimizer.structures.binaryheap;

import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<T>> implements HeapInterface<T> {

    private final List<T> items;

    public MinHeap() {
        items = new ArrayList<>();
    }

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot put null item.");
        }
        items.add(item);

        int lastItemId = items.size() - 1;
        heapUp(lastItemId);
    }

    @Override
    public T pop() {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Cannot pop item from empty heap.");
        }

        int firstItemId = 0;
        int lastItemId = items.size() - 1;

        swapItems(firstItemId, lastItemId);
        T rootItem = items.remove(lastItemId);

        heapDown();

        return rootItem;
    }

    @Override
    public T getMinimum() {
        if(items.size() < 1 ) {
            throw new IllegalArgumentException("Cannot get minimum from empty heap");
        }
        return items.get(0);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    private void heapUp(int childId) {
        int parentId = (childId - 1) / 2;

        while (childId > 0 && isChildSmallerThanParent(childId, parentId)) {

            swapItems(childId, parentId);
            childId = parentId;
            parentId = (childId - 1) / 2;
        }
    }

    private void heapDown() {
        int n = items.size();
        int parentId = 0;
        int childId = 1;

        while (childId < n) {
            if (isRightSmallerThanLeft(childId)) {
                childId++;
            }

            if (isChildSmallerThanParent(childId, parentId)) {
                swapItems(parentId, childId);
                parentId = childId;
                childId = 2 * parentId + 1;
            } else {
                break;
            }
        }
    }

    private boolean isChildSmallerThanParent(int childId, int parentId) {
        T childValue = items.get(childId);
        T parentValue = items.get(parentId);

        return childValue.compareTo(parentValue) < 0;
    }

    private boolean isRightSmallerThanLeft(int leftChildId) {
        int n = items.size();
        int rightChildId = leftChildId + 1;

        boolean rightExist = rightChildId < n;
        boolean rightIsBigger = false;

        if (rightExist) {
            T rightValue = items.get(rightChildId);
            T leftValue = items.get(leftChildId);

            rightIsBigger = rightValue.compareTo(leftValue) < 0;
        }

        return rightIsBigger;
    }

    private void swapItems(int parentId, int childId) {
        T parentValue = items.get(parentId);
        T childValue = items.get(childId);

        items.set(parentId, childValue);
        items.set(childId, parentValue);
    }

    public int getSize() {
        return items.size();
    }

    public T getItem(int index) {
        return items.get(index);
    }
}
