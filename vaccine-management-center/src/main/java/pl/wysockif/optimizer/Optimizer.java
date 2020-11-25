package pl.wysockif.optimizer;

import pl.wysockif.optimizer.algorithms.Deal;
import pl.wysockif.optimizer.algorithms.MinCostMaxFlow;
import pl.wysockif.optimizer.algorithms.flow.FordFulkerson;
import pl.wysockif.optimizer.algorithms.path.BellmanFord;
import pl.wysockif.optimizer.io.InputFileReader;
import pl.wysockif.optimizer.io.OutputFileWriter;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

public class Optimizer {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Optimizer() {

        System.out.println("ODCZYTYWANIE DANYCH");
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/data5.txt");
        producers = inputFileReader.getProducers();
        pharmacies = inputFileReader.getPharmacies();
        connections = inputFileReader.getConnections();
        System.out.println("PRZYGOTOWANIE DANYCH");
        MinCostMaxFlow algorithm = new MinCostMaxFlow(producers, pharmacies, connections);
        BellmanFord bellmanFord = new BellmanFord();
        FordFulkerson fordFulkerson = new FordFulkerson(bellmanFord);
        System.out.println("OBLICZANIE");
        Graph finalGraph = fordFulkerson.findMaxFlow(algorithm.createGraph());
        System.out.println("ZAPISYWANIE");
        List<Deal> deals = algorithm.loadResults(finalGraph);
        OutputFileWriter outputFileWriter = new OutputFileWriter("src/main/resources/output.txt");
        outputFileWriter.saveDeals(deals);

    }


    public static void main(String[] args) {
        Optimizer main = new Optimizer();
    }

}
