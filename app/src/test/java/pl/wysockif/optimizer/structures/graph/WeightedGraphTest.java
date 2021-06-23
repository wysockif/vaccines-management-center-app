package pl.wysockif.optimizer.structures.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeightedGraphTest {

    private Graph graph;

    @Before
    public void setUp() {
        int numberOfVertices = 3;
        graph = new WeightedGraph(numberOfVertices);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_graphDoesNotContainGivenVertex() {
        // given
        int indexOfExistingVertex = 0;
        int indexOfNotExistingVertex = graph.getNumberOfVertices() + 5;

        // when
        graph.addEdge(indexOfExistingVertex, indexOfNotExistingVertex, 100, 100);

        //then
        assert false;
    }

    @Test
    public void should_addEdge_when_graphContainsBothVertices() {
        // given
        int indexOfFirstExistingVertex = 0;
        int indexOfSecondExistingVertex = graph.getNumberOfVertices() - 1;

        // when
        graph.addEdge(indexOfFirstExistingVertex, indexOfSecondExistingVertex, 100, 100);
        boolean actualContains = graph.containsEdge(indexOfFirstExistingVertex, indexOfSecondExistingVertex);

        //then
        boolean expectedContains = true;
        assertEquals(expectedContains, actualContains);
    }

    @Test
    public void should_getPriceOfEdge_when_edgeExists() {
        // given
        int indexOfFirstVertex = 0;
        int indexOfSecondVertex = graph.getNumberOfVertices() - 1;
        int capacity = 123;
        int price = 100;
        graph.addEdge(indexOfFirstVertex, indexOfSecondVertex, capacity, price);

        // when
        int actualPrice = graph.getPriceOfEdge(indexOfFirstVertex, indexOfSecondVertex);

        //then
        int expectedPrice = 100;
        assertEquals(expectedPrice, actualPrice);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_gotPriceOnNotExistingEdge() {
        // given
        int indexOfExistingVertex = 0;
        int indexOfNotExistingVertex = graph.getNumberOfVertices() + 5;

        // when
        graph.getPriceOfEdge(indexOfExistingVertex, indexOfNotExistingVertex);

        //then
        assert false;
    }

    @Test
    public void should_getCapacityOfEdge_when_edgeExists() {
        // given
        int indexOfFirstVertex = 0;
        int indexOfSecondVertex = graph.getNumberOfVertices() - 1;
        int capacity = 123;
        int price = 100;
        graph.addEdge(indexOfFirstVertex, indexOfSecondVertex, capacity, price);

        // when
        int actualPrice = graph.getCapacityOfEdge(indexOfFirstVertex, indexOfSecondVertex);

        //then
        int expectedCapacity = 123;
        assertEquals(expectedCapacity, actualPrice);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_gotCapacityOnNotExistingEdge() {
        // given
        int indexOfExistingVertex = 0;
        int indexOfNotExistingVertex = graph.getNumberOfVertices() + 5;

        // when
        graph.getCapacityOfEdge(indexOfExistingVertex, indexOfNotExistingVertex);

        //then
        assert false;
    }


    @Test
    public void should_returnTrue_when_graphContainsEdge() {
        // given
        int indexOfFirstExistingVertex = 0;
        int indexOfSecondExistingVertex = graph.getNumberOfVertices() - 1;
        graph.addEdge(indexOfFirstExistingVertex, indexOfSecondExistingVertex, 100, 100);

        // when
        boolean actualContains = graph.containsEdge(indexOfFirstExistingVertex, indexOfSecondExistingVertex);

        //then
        boolean expectedContains = true;
        assertEquals(expectedContains, actualContains);
    }

    @Test
    public void should_returnFalse_when_graphDoesNotContainEdge() {
        // given
        int indexOfFirstExistingVertex = 0;
        int indexOfSecondExistingVertex = graph.getNumberOfVertices() - 1;

        // when
        boolean actualContains = graph.containsEdge(indexOfFirstExistingVertex, indexOfSecondExistingVertex);

        //then
        boolean expectedContains = false;
        assertEquals(expectedContains, actualContains);
    }

    @Test
    public void should_setPriceOfEdge_when_edgeExists() {
        // given
        int indexOfFirstVertex = 0;
        int indexOfSecondVertex = graph.getNumberOfVertices() - 1;
        int price = 123;

        // when
        graph.setPriceOfEdge(indexOfFirstVertex, indexOfSecondVertex, price);
        int actualPrice = graph.getPriceOfEdge(indexOfFirstVertex, indexOfSecondVertex);

        //then
        int expectedPrice = 123;
        assertEquals(expectedPrice, actualPrice);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_setPriceOfNotExistingEdge() {
        // given
        int indexOfExistingVertex = 0;
        int indexOfNotExistingVertex = graph.getNumberOfVertices() + 5;
        int price = 123;

        // when
        graph.setPriceOfEdge(indexOfExistingVertex, indexOfNotExistingVertex, price);

        //then
        assert false;
    }

    @Test
    public void should_increaseCapacityOfEdge_when_edgeExists() {
        // given
        int indexOfFirstVertex = 0;
        int indexOfSecondVertex = graph.getNumberOfVertices() - 1;
        int initialCapacity = 12;
        int price = 1;
        int flow = 10;
        graph.addEdge(indexOfFirstVertex, indexOfSecondVertex, initialCapacity, price);

        // when
        graph.increaseCapacityOfEdge(indexOfFirstVertex, indexOfSecondVertex, flow);
        int actualCapacity = graph.getCapacityOfEdge(indexOfFirstVertex, indexOfSecondVertex);

        //then
        int expectedCapacity = 22;
        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_increasedCapacityOnNotExistingEdge() {
        // given
        int indexOfExistingVertex = 0;
        int indexOfNotExistingVertex = graph.getNumberOfVertices() + 5;
        int flow = 10;

        // when
        graph.increaseCapacityOfEdge(indexOfExistingVertex, indexOfNotExistingVertex, flow);

        //then
        assert false;
    }

}