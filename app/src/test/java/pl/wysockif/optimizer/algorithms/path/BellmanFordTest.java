package pl.wysockif.optimizer.algorithms.path;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BellmanFordTest {
    private FindingPath bellmanFord;

    @Mock
    private Graph residualGraph;

    @Before
    public void setUp() {
        bellmanFord = new BellmanFord();
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_givenGraphIsNull() {
        // given
        Graph graph = null;

        // when
        bellmanFord.findPath(graph);

        // then
        assert false;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_graphDoesNotContainAnyVertices() {
        // given
        int numberOfVerticesWithCapacityIsZero = 0;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVerticesWithCapacityIsZero);

        // when
        bellmanFord.findPath(residualGraph);

        // then
        assert false;
    }


    @Test
    public void should_returnEmptyList_when_graphContainsOnlyOneVertex() {
        // given
        int numberOfVertices = 1;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = new LinkedList<>();
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnBothVertices_when_graphContainsOnlyOneEdge() {
        // given
        int numberOfVertices = 2;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(0, 1)).willReturn(true);
        given(residualGraph.getPriceOfEdge(0, 1)).willReturn(123);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = Arrays.asList(0, 1);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnShortestPath_when_graphContainsOnlyOnePath() {
        // given
        int numberOfVertices = 3;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(0, 1)).willReturn(true);
        given(residualGraph.containsEdge(1, 2)).willReturn(true);
        given(residualGraph.getPriceOfEdge(0, 1)).willReturn(123);
        given(residualGraph.getPriceOfEdge(0, 1)).willReturn(123);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = Arrays.asList(0, 1, 2);
        assertEquals(expectedDeals, actualDeals);
    }


    @Test
    public void should_returnShortestPath_when_graphContainsManyPathsWithDifferentWeights() {
        // given
        int numberOfVertices = 4;
        int lowerWeight = 1;
        int greaterWeight = 100;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(0, 1)).willReturn(true);
        given(residualGraph.containsEdge(0, 2)).willReturn(true);
        given(residualGraph.containsEdge(1, 3)).willReturn(true);
        given(residualGraph.containsEdge(2, 3)).willReturn(true);
        given(residualGraph.containsEdge(3, 4)).willReturn(true);
        given(residualGraph.getPriceOfEdge(1, 3)).willReturn(greaterWeight);
        given(residualGraph.getPriceOfEdge(2, 3)).willReturn(lowerWeight);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = Arrays.asList(0, 2, 3);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnFirstShortestPath_when_graphContainsManyPathsWithTheSameWeights() {
        // given
        int numberOfVertices = 4;
        int weightOfEachEdge = 100;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(0, 1)).willReturn(true);
        given(residualGraph.containsEdge(0, 2)).willReturn(true);
        given(residualGraph.containsEdge(1, 3)).willReturn(true);
        given(residualGraph.containsEdge(2, 3)).willReturn(true);
        given(residualGraph.containsEdge(3, 4)).willReturn(true);
        given(residualGraph.getPriceOfEdge(1, 3)).willReturn(weightOfEachEdge);
        given(residualGraph.getPriceOfEdge(2, 3)).willReturn(weightOfEachEdge);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = Arrays.asList(0, 1, 3);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnShortestPath_when_someWeightsAreNegative(){
        // given
        int numberOfVertices = 4;
        int lowerWeight = -100;
        int greaterWeight = 100;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(0, 1)).willReturn(true);
        given(residualGraph.containsEdge(0, 2)).willReturn(true);
        given(residualGraph.containsEdge(1, 3)).willReturn(true);
        given(residualGraph.containsEdge(2, 3)).willReturn(true);
        given(residualGraph.getPriceOfEdge(1, 3)).willReturn(greaterWeight);
        given(residualGraph.getPriceOfEdge(2, 3)).willReturn(lowerWeight);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = Arrays.asList(0, 2, 3);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnEmptyList_when_flowOnEachEdgeIsFull() {
        // given
        int numberOfVertices = 4;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.getCapacityOfEdge(0, 1)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(0, 2)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(1, 3)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(2, 3)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(3, 4)).willReturn(0);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = new LinkedList<>();
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnEmptyList_when_flowOnOneEdgeOfEverySinglePathIsFull() {
        // given
        int numberOfVertices = 4;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(1, 3)).willReturn(true);
        given(residualGraph.containsEdge(2, 3)).willReturn(true);
        given(residualGraph.containsEdge(3, 4)).willReturn(true);
        given(residualGraph.getCapacityOfEdge(0, 1)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(0, 2)).willReturn(0);
        given(residualGraph.getCapacityOfEdge(1, 3)).willReturn(100);
        given(residualGraph.getCapacityOfEdge(2, 3)).willReturn(100);
        given(residualGraph.getCapacityOfEdge(3, 4)).willReturn(100);

        // when
        List<Integer> actualDeals = bellmanFord.findPath(residualGraph);

        // then
        List<Integer> expectedDeals = new LinkedList<>();
        assertEquals(expectedDeals, actualDeals);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_throwUnsupportedOperationException_when_graphContainsNegativeCycle() {
        // given
        int numberOfVertices = 4;
        given(residualGraph.getNumberOfVertices()).willReturn(numberOfVertices);
        given(residualGraph.containsEdge(1, 1)).willReturn(true);
        given(residualGraph.containsEdge(1, 2)).willReturn(true);
        given(residualGraph.containsEdge(2, 1)).willReturn(true);
        given(residualGraph.containsEdge(2, 3)).willReturn(true);
        given(residualGraph.getPriceOfEdge(0, 1)).willReturn(10);
        given(residualGraph.getPriceOfEdge(1, 2)).willReturn(-40);
        given(residualGraph.getPriceOfEdge(2,1)).willReturn(-50);
        given(residualGraph.getPriceOfEdge(2, 3)).willReturn(100);

        // when
        bellmanFord.findPath(residualGraph);

        // then
        assert false;
    }
}