package pl.wysockif.optimizer.algorithms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DataHandlerTest {

    private DataHandler dataHandler;

    @Mock
    private Producers producers;

    @Mock
    private Pharmacies pharmacies;

    @Mock
    private Connections connections;

    @Before
    public void setUp() {
        dataHandler = new DataHandler(producers, pharmacies, connections);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_graphIsUninitialized() {
        // given
        Graph graph = null;

        // when
        dataHandler.loadResults(graph);

        // then
        assert false;
    }

    @Test
    public void should_loadResultCorrectly_when_graphContainsMoreThanOneDeal() {
        // given
        given(producers.getNumberOfProducers()).willReturn(2);
        given(producers.getProducerByIndex(0)).willReturn(new Producer(0, "producent 0", 100));
        given(producers.getProducerByIndex(1)).willReturn(new Producer(1, "producent 1", 100));
        given(pharmacies.getPharmacyByIndex(0)).willReturn(new Pharmacy(0, "apteka 0", 100));
        Graph graph = new WeightedGraph(5);
        graph.addEdge(3,1, 40, -90);
        graph.addEdge(3,2, 50, -60);

        // when
        List<Deal> actualDeals = dataHandler.loadResults(graph);

        // then
        Deal expectedFirstDeal = new Deal("producent 0", "apteka 0", 40, 90);
        Deal expectedSecondDeal = new Deal("producent 1", "apteka 0", 50, 60);
        List<Deal> expectedDeals = Arrays.asList(expectedFirstDeal, expectedSecondDeal);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_loadResultCorrectly_when_graphContainsOnlyOneDeal() {
        // given
        given(producers.getNumberOfProducers()).willReturn(1);
        given(producers.getProducerByIndex(0)).willReturn(new Producer(0, "producent 0", 100));
        given(pharmacies.getPharmacyByIndex(0)).willReturn(new Pharmacy(0, "apteka 0", 100));
        Graph graph = new WeightedGraph(4);
        graph.addEdge(2,1, 40, -90);

        // when
        List<Deal> actualDeals = dataHandler.loadResults(graph);

        // then
        Deal expectedDeal = new Deal("producent 0", "apteka 0", 40, 90);
        List<Deal> expectedDeals = Collections.singletonList(expectedDeal);
        assertEquals(expectedDeals, actualDeals);
    }

    @Test
    public void should_returnEmptyList_when_graphDoesNotContainAnyDeal() {
        // given
        Graph graph = new WeightedGraph(4);

        // when
        List<Deal> actualDeals = dataHandler.loadResults(graph);

        // then
        List<Deal> expectedDeals = new LinkedList<>();
        assertEquals(expectedDeals, actualDeals);
    }

}