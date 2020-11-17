package pl.wysockif.optimizer;

import pl.wysockif.optimizer.io.OutputFileWriter;
import pl.wysockif.optimizer.io.InputFileReader;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;

public class Optimizer {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Optimizer(){
//        InputFile inputFile = new InputFile("src/main/resources/data.txt");
//        inputFile.loadDataFromFile();
//        producers = inputFile.getProducers();
//        pharmacies = inputFile.getPharmacies();
//        connections = inputFile.getConnections();
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/data.txt");
        OutputFileWriter outputFileWriter = new OutputFileWriter("src/main/resources/output.txt");


    }

    public static void main(String[] args) {
        Optimizer main = new Optimizer();
    }

}
