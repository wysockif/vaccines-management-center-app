package pl.wysockif.optimizer.structures.binaryheap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinHeapTest<T extends Comparable<T>> {
    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_givenItemIsNull() {
        // given
        MinHeap<T> anyTypeHeap = new MinHeap<>();
        T item = null;

        // when
        anyTypeHeap.put(item);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_heapIsEmpty() {
        // given
        MinHeap<T> anyTypeHeap = new MinHeap<>();

        // when
        anyTypeHeap.pop();

        // then
        assert false;
    }

    @Test
    public void should_putItemsCorrectly_when_givenItemsAreStringType() {
        // given
        MinHeap<String> stringTypeHeap = new MinHeap<>();
        String[] givenItems = {"Franek", "Karol", "Anna"};

        // when
        for (String s : givenItems) {
            stringTypeHeap.put(s);
        }
        String[] expectedItems = {"Anna", "Karol", "Franek"};

        // then
        for (int i = 0; i < stringTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], stringTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putItemsCorrectly_when_oneOfGivenStringsIsEmpty() {
        // given
        MinHeap<String> stringTypeHeap = new MinHeap<>();
        String[] givenItems = {"Franek", "", "Anna", "Karol"};

        // when
        for (String s : givenItems) {
            stringTypeHeap.put(s);
        }
        String[] expectedItems = {"", "Franek", "Anna", "Karol"};

        // then
        for (int i = 0; i < stringTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], stringTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putStringsCorrectly_when_someOfThemAreDuplicated() {
        // given
        MinHeap<String> stringTypeHeap = new MinHeap<>();
        String[] givenItems = {"Franek", "Anna", "Karol", "Franek"};

        // when
        for (String s : givenItems) {
            stringTypeHeap.put(s);
        }
        String[] expectedItems = {"Anna", "Franek", "Karol", "Franek"};

        // then
        for (int i = 0; i < stringTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], stringTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_popItemsCorrectly_when_theyAreStringType() {
        // given
        MinHeap<String> stringTypeHeap = new MinHeap<>();
        String[] givenItems = {"Franek", "Anna", "Karol"};
        for (String s : givenItems) {
            stringTypeHeap.put(s);
        }

        // when
        String actualRootValue = stringTypeHeap.pop();
        String expectedRootValue = "Anna";
        String[] expectedItems = {"Franek", "Karol"};

        // then
        assertEquals(expectedRootValue, actualRootValue);
        for (int i = 0; i < stringTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], stringTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putTheItemsCorrectly_when_givenItemsAreIntegerType() {
        // given
        MinHeap<Integer> integerTypeHeap = new MinHeap<>();
        Integer[] givenItems = {5, -2, 4, 3, 6, 1};

        // when
        for (Integer s : givenItems) {
            integerTypeHeap.put(s);
        }
        Integer[] expectedItems = {-2, 3, 1, 5, 6, 4};

        // then
        for (int i = 0; i < integerTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], integerTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putIntegersCorrectly_when_someOfThemAreDuplicated() {
        // given
        MinHeap<Integer> integerTypeHeap = new MinHeap<>();
        Integer[] givenItems = {5, 6, 4, 3, 6, 7};

        // when
        for (Integer s : givenItems) {
            integerTypeHeap.put(s);
        }
        Integer[] expectedItems = {3, 4, 5, 6, 6, 7};

        // then
        for (int i = 0; i < integerTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], integerTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_popItemsCorrectly_when_theyAreIntegerType() {
        // given
        MinHeap<Integer> integerTypeHeap = new MinHeap<>();
        Integer[] givenItems = {5, -2, 4, 3, 6, 7};
        for (Integer s : givenItems) {
            integerTypeHeap.put(s);
        }

        // when
        Integer actualRootValue = integerTypeHeap.pop();
        Integer expectedRootValue = -2;
        Integer[] expectedItems = {3, 5, 4, 7, 6};

        // then
        assertEquals(expectedRootValue, actualRootValue);
        for (int i = 0; i < integerTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], integerTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putItemsCorrectly_when_givenItemsAreDoubleType() {
        // given
        MinHeap<Double> doubleTypeHeap = new MinHeap<>();
        Double[] givenItems = {2.2, -2.4, 4.6, 3.2, 6.4, 7.9};

        // when
        for (Double s : givenItems) {
            doubleTypeHeap.put(s);
        }
        Double[] expectedItems = {-2.4, 2.2, 4.6, 3.2, 6.4, 7.9};

        // then
        for (int i = 0; i < doubleTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], doubleTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_putDoublesCorrectly_when_theyAreDuplicated() {
        // given
        MinHeap<Double> doubleTypeHeap = new MinHeap<>();
        Double[] givenItems = {2.2, -2.4, 4.6, 3.2, 2.2, 7.9};

        // when
        for (Double s : givenItems) {
            doubleTypeHeap.put(s);
        }
        Double[] expectedItems = {-2.4, 2.2, 4.6, 3.2, 2.2, 7.9};

        // then
        for (int i = 0; i < doubleTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], doubleTypeHeap.getItem(i));
        }
    }

    @Test
    public void should_popItemsCorrectly_when_theyAreDoubleType() {
        // given
        MinHeap<Double> doubleTypeHeap = new MinHeap<>();
        Double[] givenItems = {2.2, -2.4, 4.6, 3.2, 6.4, 7.9};
        for (Double s : givenItems) {
            doubleTypeHeap.put(s);
        }

        // when
        Double actualRootValue = doubleTypeHeap.pop();
        Double expectedRootValue = -2.4;
        Double[] expectedItems = {2.2, 3.2, 4.6, 7.9, 6.4};

        // then
        assertEquals(expectedRootValue, actualRootValue);
        for (int i = 0; i < doubleTypeHeap.getSize(); i++) {
            assertEquals(expectedItems[i], doubleTypeHeap.getItem(i));
        }
    }

}