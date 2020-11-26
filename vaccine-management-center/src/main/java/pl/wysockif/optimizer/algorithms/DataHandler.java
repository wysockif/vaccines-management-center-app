package pl.wysockif.optimizer.algorithms;

import pl.wysockif.optimizer.items.connections.Connection;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

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

    public Graph createGraph() {
        int numberOfVertices = countNumberOfVertices();
        Graph graph = new WeightedGraph(numberOfVertices);
        addProducersToGraph(graph);
        addPharmaciesToGraph(graph, numberOfVertices);
        addConnectionsToGraph(graph);
        return graph;
    }

    private void addConnectionsToGraph(Graph graph) {
        int numberOfConnections = connections.getNumberOfConnections();
        for (int i = 0; i < numberOfConnections; i++) {
            Connection connection = connections.getConnectionByIndex(i);
            int producerIndex = producers.getProducerIndexById(connection.getProducer().getId());
            int pharmacyIndex = pharmacies.getPharmacyIndexById(connection.getPharmacy().getId());
            int price = connection.getPrice();
            int maxNumberOfVaccines = connection.getMaxNumberOVaccines();
            int from = producerIndex + 1;
            int to = pharmacyIndex + producers.getNumberOfProducers() + 1;
            graph.addEdge(from, to, maxNumberOfVaccines, price);
        }
    }

    private void addPharmaciesToGraph(Graph graph, int numberOfVertices) {
        int numberOfProducers = producers.getNumberOfProducers();
        int numberOfPharmacies = pharmacies.getNumberOfPharmacies();

        for (int i = 0; i < numberOfPharmacies; i++) {
            Pharmacy pharmacy = pharmacies.getPharmacyByIndex(i);
            graph.addEdge(numberOfProducers + i + 1, numberOfVertices - 1, pharmacy.getDailyDemand(), 0);
        }
    }

    private void addProducersToGraph(Graph graph) {
        for (int i = 0; i < producers.getNumberOfProducers(); i++) {
            Producer producer = producers.getProducerByIndex(i);
            graph.addEdge(0, i + 1, producer.getDailyProduction(), 0);
        }
    }

    private int countNumberOfVertices() {
        int numberOfProducers = producers.getNumberOfProducers();
        int numberOfPharmacies = pharmacies.getNumberOfPharmacies();
        return numberOfProducers + numberOfPharmacies + 2;
    }

    public List<Deal> loadResults(Graph finalGraph) {
        List<Deal> deals = new LinkedList<>();
        for (int i = finalGraph.getNumberOfVertices() - 2; i >= 1; i--) {
            for (int j = i - 1; j >= 1; j--) {
                addDeal(finalGraph, deals, i, j);
            }
        }
        return deals;
    }

    private void addDeal(Graph finalGraph, List<Deal> deals, int u, int v) {
        if ((u > v) && finalGraph.containsEdge(u, v)) {
            int price = finalGraph.getPriceOfEdge(u, v) * -1;
            int amount = finalGraph.getCapacityOfEdge(u, v);
            int numberOfProducers = producers.getNumberOfProducers();
            String producerName = producers.getProducerByIndex(v - 1).getName();
            String pharmacyName = pharmacies.getPharmacyByIndex(u - numberOfProducers - 1).getName();
            Deal deal = new Deal(producerName, pharmacyName, amount, price);
            deals.add(deal);
        }
    }

}
