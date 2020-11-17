package pl.wysockif.optimizer.structures.queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinPriorityQueueTest<T extends Comparable<T>> {

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_givenItemToInsertIsNull() {
        // given
        PriorityQueueInterface<T> anyTypePriorityQueue = new MinPriorityQueue<>();
        T item = null;

        // when
        anyTypePriorityQueue.insert(item);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_thereIsExtractingElementFromEmptyQueue() {
        // given
        PriorityQueueInterface<T> anyTypePriorityQueue = new MinPriorityQueue<>();

        // when
        anyTypePriorityQueue.extractMin();

        // then
        assert false;
    }

    @Test
    public void should_extractItemsInCorrectOrder_when_givenItemsAreStringType() {
        // given
        MinPriorityQueue<String> stringTypePriorityQueue = new MinPriorityQueue<>();
        String[] givenItems = {"Franek", "Karol", "Anna"};
        for (String s : givenItems) {
            stringTypePriorityQueue.insert(s);
        }

        // when
        String actualFirst = stringTypePriorityQueue.extractMin();
        String actualSecond = stringTypePriorityQueue.extractMin();
        String actualThird = stringTypePriorityQueue.extractMin();

        // then
        String expectedFirst = givenItems[2];
        String expectedSecond = givenItems[0];
        String expectedThird = givenItems[1];
        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedSecond, actualSecond);
        assertEquals(expectedThird, actualThird);
    }

    @Test
    public void should_returnFalse_when_queueIsNotEmpty() {
        // given
        MinPriorityQueue<String> stringTypePriorityQueue = new MinPriorityQueue<>();
        String[] givenItems = {"Franek", "Karol", "Anna"};
        for (String s : givenItems) {
            stringTypePriorityQueue.insert(s);
        }

        // when
        boolean isEmpty = stringTypePriorityQueue.isEmpty();

        // then
        assertFalse(isEmpty);
    }

    @Test
    public void should_returnTrue_when_queueIsEmpty() {
        // given
        MinPriorityQueue<String> stringTypePriorityQueue = new MinPriorityQueue<>();

        // when
        boolean isEmpty = stringTypePriorityQueue.isEmpty();

        // then
        assertTrue(isEmpty);
    }

    @Test
    public void should_returnMinimumValue_when_queueIsNotEmpty() {
        // given
        MinPriorityQueue<Double> priorityQueue = new MinPriorityQueue<>();
        priorityQueue.insert(1.0);
        priorityQueue.insert(-4.2);
        priorityQueue.insert(5.6);

        // when
        Double actualMinimum = priorityQueue.minimum();

        // then
        Double expectedMinimum = -4.2;
        assertEquals(expectedMinimum, actualMinimum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_thereIsGettingMinimumFromEmptyQueue() {
        // given
        MinPriorityQueue<Double> priorityQueue = new MinPriorityQueue<>();

        // when
        priorityQueue.minimum();

        // then
        assert false;
    }




}