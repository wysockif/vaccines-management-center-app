package pl.wysockif.optimizer.structures.queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlockingQueueFIFOTest<T extends Comparable<T>> {

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_pushedItemIsNull() {
        // given
        QueueFIFO<T> anyTypeQueue = new BlockingQueueFIFO<>();
        T item = null;

        // when
        anyTypeQueue.push(item);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_elementIsPoppedFromEmptyQueue() {
        // given
        QueueFIFO<T> anyTypeQueue = new BlockingQueueFIFO<>();

        // when
        anyTypeQueue.pop();

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_thereIsNoFrontElement() {
        // given
        QueueFIFO<T> anyTypeQueue = new BlockingQueueFIFO<>();

        // when
        anyTypeQueue.front();

        // then
        assert false;
    }

    @Test
    public void should_notPushNewElement_when_elementIsDuplicated() {
        // given
        BlockingQueueFIFO<Integer> queue = new BlockingQueueFIFO<>();

        // when
        queue.push(10);
        queue.push(10);
        int actualSizeOfQueue = queue.size();

        // then
        int expectedSizeOfQueue = 1;
        assertEquals(expectedSizeOfQueue, actualSizeOfQueue);
    }

    @Test
    public void should_returnTrue_when_queueIsEmpty() {
        // given
        QueueFIFO<T> queue = new BlockingQueueFIFO<>();

        // when
        boolean isEmpty = queue.isEmpty();

        //then
        assertTrue(isEmpty);
    }

    @Test
    public void should_returnFalse_when_queueIsNotEmpty() {
        // given
        QueueFIFO<Integer> queue = new BlockingQueueFIFO<>();

        // when
        queue.push(10);
        boolean isEmpty = queue.isEmpty();

        //then
        assertFalse(isEmpty);
    }

    @Test
    public void should_returnFrontElementAndNotRemoveIt_when_queueIsNotEmpty() {
        // given
        BlockingQueueFIFO<Integer> queue = new BlockingQueueFIFO<>();

        // when
        queue.push(10);
        queue.push(5);
        int actualFront = queue.front();
        int actualSize = queue.size();

        //then
        int expectedFront = 10;
        int expectedSize = 2;
        assertEquals(expectedFront, actualFront);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void should_popItemsInCorrectOrder_when_givenItemsAreStringType() {
        // given
        QueueFIFO<String> stringTypeQueue = new BlockingQueueFIFO<>();
        String[] givenItems = {"Franek", "Karol", "Anna"};
        for (String s : givenItems) {
            stringTypeQueue.push(s);
        }

        // when
        String actualFirst = stringTypeQueue.pop();
        String actualSecond = stringTypeQueue.pop();
        String actualThird = stringTypeQueue.pop();

        // then
        String expectedFirst = givenItems[0];
        String expectedSecond = givenItems[1];
        String expectedThird = givenItems[2];
        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedSecond, actualSecond);
        assertEquals(expectedThird, actualThird);
    }

    @Test
    public void should_popItemsInCorrectOrder_when_givenItemsAreDoubleType() {
        // given
        QueueFIFO<Double> doubleTypeQueue = new BlockingQueueFIFO<>();
        double[] givenItems = {12.3, 3.2, 5.5};
        for (Double number : givenItems) {
            doubleTypeQueue.push(number);
        }

        // when
        double actualFirst = doubleTypeQueue.pop();
        double actualSecond = doubleTypeQueue.pop();
        double actualThird = doubleTypeQueue.pop();

        // then
        double expectedFirst = givenItems[0];
        double expectedSecond = givenItems[1];
        double expectedThird = givenItems[2];
        assertEquals(expectedFirst, actualFirst, 0);
        assertEquals(expectedSecond, actualSecond, 0);
        assertEquals(expectedThird, actualThird, 0);
    }

    @Test
    public void should_popItemsInCorrectOrder_when_givenItemsAreIntegerType() {
        // given
        QueueFIFO<Integer> integerTypeQueue = new BlockingQueueFIFO<>();
        int[] givenItems = {12, 3, 5};
        for (Integer number : givenItems) {
            integerTypeQueue.push(number);
        }

        // when
        int actualFirst = integerTypeQueue.pop();
        int actualSecond = integerTypeQueue.pop();
        int actualThird = integerTypeQueue.pop();

        // then
        int expectedFirst = givenItems[0];
        int expectedSecond = givenItems[1];
        int expectedThird = givenItems[2];
        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedSecond, actualSecond);
        assertEquals(expectedThird, actualThird);
    }
}