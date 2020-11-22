package pl.wysockif.optimizer;

import pl.wysockif.optimizer.algorithms.MinCostMaxFlowAlgorithm;
import pl.wysockif.optimizer.io.InputFileReader;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;

public class Optimizer {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Optimizer() {
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/data.txt");
        producers = inputFileReader.getProducers();
        pharmacies = inputFileReader.getPharmacies();
        connections = inputFileReader.getConnections();
        MinCostMaxFlowAlgorithm algorithm = new MinCostMaxFlowAlgorithm(producers, pharmacies, connections);
        System.out.println(algorithm.createGraph());
//        OutputFileWriter outputFileWriter = new OutputFileWriter("src/main/resources/output.txt");
    }

    public static void main(String[] args) {
        Optimizer main = new Optimizer();
    }

}
