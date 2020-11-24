package pl.wysockif.optimizer;

import me.tongfei.progressbar.ProgressBar;
import pl.wysockif.optimizer.algorithms.Deal;
import pl.wysockif.optimizer.algorithms.flow.EdmondsKarpAlgorithm;
import pl.wysockif.optimizer.algorithms.path.BellmanFordAlgorithm;
import pl.wysockif.optimizer.algorithms.MinCostMaxFlowAlgorithm;
import pl.wysockif.optimizer.io.InputFileReader;
import pl.wysockif.optimizer.io.OutputFileWriter;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

import static java.lang.Thread.sleep;

public class Optimizer {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Optimizer() {

        System.out.println("ODCZYTYWANIE DANYCH");
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/data.txt");
        producers = inputFileReader.getProducers();
        pharmacies = inputFileReader.getPharmacies();
        connections = inputFileReader.getConnections();
        System.out.println("OBLICZANIE");
        MinCostMaxFlowAlgorithm algorithm = new MinCostMaxFlowAlgorithm(producers, pharmacies, connections);
        BellmanFordAlgorithm bellmanFordAlgorithm = new BellmanFordAlgorithm();
        EdmondsKarpAlgorithm edmondsKarpAlgorithm = new EdmondsKarpAlgorithm(bellmanFordAlgorithm);
        Graph finalGraph = edmondsKarpAlgorithm.findMaxFlow(algorithm.createGraph());
        List<Deal> deals = algorithm.loadResults(finalGraph);
        System.out.println("ZAPISYWANIE");
        OutputFileWriter outputFileWriter = new OutputFileWriter("src/main/resources/output.txt");
        outputFileWriter.saveDeals(deals);

    }


    public static void main(String[] args) {
        Optimizer main = new Optimizer();
    }

}
