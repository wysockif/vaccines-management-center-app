package pl.wysockif.optimizer;

import pl.wysockif.optimizer.algorithms.DataHandler;
import pl.wysockif.optimizer.algorithms.Deal;
import pl.wysockif.optimizer.algorithms.flow.FordFulkerson;
import pl.wysockif.optimizer.algorithms.flow.MaxFlow;
import pl.wysockif.optimizer.algorithms.path.BellmanFord;
import pl.wysockif.optimizer.algorithms.path.FindingShortestPath;
import pl.wysockif.optimizer.io.ErrorsHandler;
import pl.wysockif.optimizer.io.InputFileReader;
import pl.wysockif.optimizer.io.OutputFileWriter;
import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

import static pl.wysockif.optimizer.io.ErrorsHandler.INCORRECT_CALL;

public class Optimizer {
    private static String inputFilePath;
    private static String outputFilePath;
    public static boolean isLimit;

    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;
    private DataHandler minCostMaxFlow;
    private MaxFlow maxFlow;


    private void loadData() {
        System.out.println("(1/4) TRWA ODCZYTYWANIE I WALIDACJA DANYCH...");
        InputFileReader inputFileReader = new InputFileReader(inputFilePath);
        producers = inputFileReader.getProducers();
        pharmacies = inputFileReader.getPharmacies();
        connections = inputFileReader.getConnections();
    }

    public Graph prepare() {
        System.out.println("(2/4) TRWA PRZYGOTOWYWANIE...");
        minCostMaxFlow = new DataHandler(producers, pharmacies, connections);
        FindingShortestPath findingPath = new BellmanFord();
        maxFlow = new FordFulkerson(findingPath);
        return minCostMaxFlow.createGraph();
    }

    private Graph optimize(Graph initialGraph) {
        System.out.println("(3/4) TRWA OBLICZANIE...");
        return maxFlow.findMaxFlow(initialGraph);
    }

    public void saveResults(Graph finalGraph) {
        System.out.println("(4/4) TRWA ZAPISYWANIE...");
        List<Deal> deals = minCostMaxFlow.loadResults(finalGraph);
        OutputFileWriter outputFileWriter = new OutputFileWriter(outputFilePath);
        outputFileWriter.saveDeals(deals);
        System.out.println("Wynik został pomyślnie zapisany w pliku: " + outputFilePath + ".");
    }

    public static void main(String[] args) {
        long before = System.nanoTime();
        validateArguments(args);
        Optimizer optimizer = new Optimizer();
        long b = System.nanoTime();
        optimizer.loadData();
        System.out.println("Samo odczytywanie: " + (System.nanoTime() - b) / 1_000_000_000.0);
        Graph initialGraph = optimizer.prepare();
        b = System.nanoTime();
        Graph finalGraph = optimizer.optimize(initialGraph);
        System.out.println("Sam algorytm: " + (System.nanoTime() - b) / 1_000_000_000.0);
        optimizer.saveResults(finalGraph);
        System.out.println("Cały program: " + (System.nanoTime() - before) / 1_000_000_000.0);
    }

    private static void validateArguments(String[] args) {
        if (args.length < 2 || args.length > 3) {
            String message = "[WYWOŁANIE] Nie poprawna ilość argumentów wywołania";
            ErrorsHandler.handleTheError(INCORRECT_CALL, message);
        } else if (args.length == 3) {
            validateInstruction(args[2]);
        } else {
            isLimit = true;
        }

        inputFilePath = args[0];
        outputFilePath = args[1];
    }

    private static void validateInstruction(String instruction) {
        if (instruction.equals("-upper_limit=false")) {
            isLimit = false;
            System.out.println("Zniesiono górny limit ilości danych.");
        } else {
            String message = "[WYWOŁANIE] Nie rozpoznano polecenia: " + instruction;
            ErrorsHandler.handleTheError(INCORRECT_CALL, message);
        }
    }
}
