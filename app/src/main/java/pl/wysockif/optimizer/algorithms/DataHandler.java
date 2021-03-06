package pl.wysockif.optimizer.algorithms;

import pl.wysockif.optimizer.items.connections.Connection;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DataHandler {
    private final Producers producers;
    private final Pharmacies pharmacies;
    private final Connections connections;

    public DataHandler(Producers producers, Pharmacies pharmacies, Connections connections) {
        this.producers = producers;
        this.pharmacies = pharmacies;
        this.connections = connections;
    }

    public List<Deal> loadResults(Graph finalGraph) {
        checkIfArgumentIsNotNull(finalGraph);
        int lastPharmacyVertexIndex = finalGraph.getNumberOfVertices() - 2;
        int firstProducerVertexIndex = 1;
        List<Deal> deals = new LinkedList<>();

        for (int i = lastPharmacyVertexIndex; i >= firstProducerVertexIndex; i--) {
            for (int j = i - 1; j >= firstProducerVertexIndex; j--) {
                addDeal(finalGraph, deals, i, j);
            }
        }
        return deals;
    }

    private void addDeal(Graph finalGraph, List<Deal> deals, int firstVertex, int secondVertex) {
        if (firstVertex > secondVertex && finalGraph.containsEdge(firstVertex, secondVertex)) {
            int price = finalGraph.getPriceOfEdge(firstVertex, secondVertex) * -1;
            int amount = finalGraph.getCapacityOfEdge(firstVertex, secondVertex);
            int numberOfProducers = producers.getNumberOfProducers();
            String producerName = producers.getProducerByIndex(secondVertex - 1).getName();
            String pharmacyName = pharmacies.getPharmacyByIndex(firstVertex - numberOfProducers - 1).getName();

            Deal deal = new Deal(producerName, pharmacyName, amount, price);
            ((LinkedList<Deal>) deals).addFirst(deal);
        }
    }

    private void checkIfArgumentIsNotNull(Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Niezainicjowany argument!");
        }
    }

    public Graph createGraph() {
        int numberOfVertices = countNumberOfVertices();
        Graph graph = new WeightedGraph(numberOfVertices);

        addProducersToGraph(graph);
        addPharmaciesToGraph(graph, numberOfVertices);
        addConnectionsToGraph(graph);

        return graph;
    }

    private void addConnectionsToGraph(Graph graph) {
        Collection<Connection> connectionsCollection = connections.getConnectionsCollection();

        for (Connection connection : connectionsCollection) {
            int producerIndex = producers.getProducerIndexById(connection.getProducer().getId());
            int pharmacyIndex = pharmacies.getPharmacyIndexById(connection.getPharmacy().getId());
            int price = connection.getPrice();
            int maxNumberOfVaccines = connection.getMaxNumberOVaccines();
            int indexOfSource = 1;
            int from = producerIndex + indexOfSource;
            int to = pharmacyIndex + producers.getNumberOfProducers() + indexOfSource;

            graph.addEdge(from, to, maxNumberOfVaccines, price);
        }
    }

    private void addPharmaciesToGraph(Graph graph, int numberOfVertices) {
        int index = 0;
        int price = 0;
        int numberOfProducers = producers.getNumberOfProducers();
        Collection<Pharmacy> pharmaciesCollection = pharmacies.getPharmaciesCollection();

        for (Pharmacy pharmacy : pharmaciesCollection) {
            int from = numberOfProducers + index + 1;
            int to = numberOfVertices - 1;
            int dailyDemand = pharmacy.getDailyDemand();

            graph.addEdge(from, to, dailyDemand, price);
            index++;
        }
    }

    private void addProducersToGraph(Graph graph) {
        int index = 0;
        int price = 0;
        Collection<Producer> producersCollection = producers.getProducersCollection();

        for (Producer producer : producersCollection) {
            int from = 0;
            int to = index + 1;
            int dailyProduction = producer.getDailyProduction();

            graph.addEdge(from, to, dailyProduction, price);
            index++;
        }
    }

    private int countNumberOfVertices() {
        int numberOfProducers = producers.getNumberOfProducers();
        int numberOfPharmacies = pharmacies.getNumberOfPharmacies();
        int numberOfSourcesAndSinks = 2;

        return numberOfProducers + numberOfPharmacies + numberOfSourcesAndSinks;
    }
}
