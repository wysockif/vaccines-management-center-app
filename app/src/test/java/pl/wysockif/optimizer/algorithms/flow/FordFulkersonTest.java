package pl.wysockif.optimizer.algorithms.flow;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wysockif.optimizer.algorithms.path.FindingPath;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class FordFulkersonTest {
    private MaxFlow maxFlow;

    @Mock
    private FindingPath findingShortestPath;

    @Before
    public void setUp() {
        maxFlow = new FordFulkerson(findingShortestPath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_givenGraphIsNull() {
        // given
        Graph graph = null;

        // when
        maxFlow.findMaxFlow(graph);

        // then
        assert false;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_graphDoesNotContainAnyVertices() {
        // given
        int numberOfVertices = 0;
        Graph graph = new WeightedGraph(numberOfVertices);

        // when
        maxFlow.findMaxFlow(graph);

        // then
        assert false;
    }

    @Test
    public void should_findMaxFlow_when_graphContainsOnlyOneVertex() {
        // given
        int numberOfVertices = 1;
        int indexOfVertex = 0;
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        List<Integer> singleElementList = Collections.singletonList(indexOfVertex);
        List<Integer> emptyList = new LinkedList<>();
        given(findingShortestPath.findPath(residualGraph)).willReturn(singleElementList).willReturn(emptyList);

        // when
        Graph graph = maxFlow.findMaxFlow(residualGraph);
        int actualCapacityOfFirstEdge = graph.getCapacityOfEdge(indexOfVertex, indexOfVertex);

        // then
        int expectedCapacity = 0;
        assertEquals(expectedCapacity, actualCapacityOfFirstEdge);
    }

    @Test
    public void should_findMaxFlow_when_graphContainsOnlyOneEdge() {
        // given
        int numberOfVertices = 2;
        int capacity = 100;
        List<Integer> path = new LinkedList<>(Arrays.asList(0, 1));
        List<Integer> emptyPath = new LinkedList<>();
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        residualGraph.increaseCapacityOfEdge(0, 1, capacity);
        given(findingShortestPath.findPath(residualGraph)).willReturn(path).willReturn(emptyPath);

        // when
        Graph graph = maxFlow.findMaxFlow(residualGraph);
        int actualCapacityOfFirstEdge = graph.getCapacityOfEdge(0, 1);

        // then
        int expectedCapacity = 0;
        assertEquals(expectedCapacity, actualCapacityOfFirstEdge);
    }

    @Test
    public void should_findMaxFlow_when_graphContainsManyPathsWithDifferentCapacities() {
        // given
        int numberOfVertices = 4;
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        residualGraph.increaseCapacityOfEdge(0, 1, 100);
        residualGraph.increaseCapacityOfEdge(0, 2, 70);
        residualGraph.increaseCapacityOfEdge(1, 3, 120);
        residualGraph.increaseCapacityOfEdge(2, 3, 90);
        List<Integer> shortestPath = Arrays.asList(0, 1, 3);
        List<Integer> emptyPath = new LinkedList<>();
        given(findingShortestPath.findPath(residualGraph)).willReturn(shortestPath).willReturn(emptyPath);

        // when
        Graph graph = maxFlow.findMaxFlow(residualGraph);
        int actualCapacityOfFirstEdge = graph.getCapacityOfEdge(0, 1);
        int actualCapacityOfSecondEdge = graph.getCapacityOfEdge(0, 2);
        int actualCapacityOfThirdEdge = graph.getCapacityOfEdge(1, 3);
        int actualCapacityOfFourthEdge = graph.getCapacityOfEdge(2, 3);

        // then
        int expectedCapacityOfFirstEdge = 0;
        int expectedCapacityOfSecondEdge = 70;
        int expectedCapacityOfThirdEdge = 20;
        int expectedCapacityOfFourthEdge = 90;
        assertEquals(expectedCapacityOfFirstEdge, actualCapacityOfFirstEdge);
        assertEquals(expectedCapacityOfSecondEdge, actualCapacityOfSecondEdge);
        assertEquals(expectedCapacityOfThirdEdge, actualCapacityOfThirdEdge);
        assertEquals(expectedCapacityOfFourthEdge, actualCapacityOfFourthEdge);
    }

    @Test
    public void should_findMaxFlow_when_graphContainsManyPathsWithTheSameCapacity() {
        // given
        int numberOfVertices = 4;
        int capacity = 100;
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        residualGraph.increaseCapacityOfEdge(0, 1, capacity);
        residualGraph.increaseCapacityOfEdge(0, 2, capacity);
        residualGraph.increaseCapacityOfEdge(1, 3, capacity);
        residualGraph.increaseCapacityOfEdge(2, 3, capacity);
        List<Integer> shortestPath = Arrays.asList(0, 1, 3);
        List<Integer> emptyPath = new LinkedList<>();
        given(findingShortestPath.findPath(residualGraph)).willReturn(shortestPath).willReturn(emptyPath);

        // when
        Graph graph = maxFlow.findMaxFlow(residualGraph);
        int actualCapacityOfFirstEdge = graph.getCapacityOfEdge(0, 1);
        int actualCapacityOfSecondEdge = graph.getCapacityOfEdge(0, 2);
        int actualCapacityOfThirdEdge = graph.getCapacityOfEdge(1, 3);
        int actualCapacityOfFourthEdge = graph.getCapacityOfEdge(2, 3);

        // then
        int expectedCapacityOfFirstEdge = 0;
        int expectedCapacityOfSecondEdge = 100;
        int expectedCapacityOfThirdEdge = 0;
        int expectedCapacityOfFourthEdge = 100;
        assertEquals(expectedCapacityOfFirstEdge, actualCapacityOfFirstEdge);
        assertEquals(expectedCapacityOfSecondEdge, actualCapacityOfSecondEdge);
        assertEquals(expectedCapacityOfThirdEdge, actualCapacityOfThirdEdge);
        assertEquals(expectedCapacityOfFourthEdge, actualCapacityOfFourthEdge);
    }

    @Test
    public void should_increaseResidualCapacity_when_decreasedOriginalOne() {
        // given
        int numberOfVertices = 3;
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        residualGraph.increaseCapacityOfEdge(0, 1, 100);
        residualGraph.increaseCapacityOfEdge(1, 2, 75);
        List<Integer> shortestPath = Arrays.asList(0, 1, 2);
        List<Integer> emptyPath = new LinkedList<>();
        given(findingShortestPath.findPath(residualGraph)).willReturn(shortestPath).willReturn(emptyPath);

        // when
        Graph graph = maxFlow.findMaxFlow(residualGraph);
        int actualResidualCapacityOfFirstEdge = graph.getCapacityOfEdge(1, 0);
        int actualResidualCapacityOfSecondEdge = graph.getCapacityOfEdge(2, 1);

        // then
        int expectedResidualCapacityOfFirstEdge = 75;
        int expectedResidualCapacityOfSecondEdge = 75;
        assertEquals(expectedResidualCapacityOfFirstEdge, actualResidualCapacityOfFirstEdge);
        assertEquals(expectedResidualCapacityOfSecondEdge, actualResidualCapacityOfSecondEdge);
    }

    @Test
    public void should_removeEdge_when_itsCapacityEqualsZero() {
        // given
        int numberOfVertices = 3;
        Graph graph = new WeightedGraph(numberOfVertices);
        graph.increaseCapacityOfEdge(0, 1, 30);
        graph.increaseCapacityOfEdge(1, 2, 40);
        List<Integer> shortestPath = Arrays.asList(0, 1, 2);
        List<Integer> emptyPath = new LinkedList<>();
        given(findingShortestPath.findPath(graph)).willReturn(shortestPath).willReturn(emptyPath);

        // when
        boolean isFirstEdge = maxFlow.findMaxFlow(graph).containsEdge(0, 1);

        // then
        assertFalse(isFirstEdge);
    }


}