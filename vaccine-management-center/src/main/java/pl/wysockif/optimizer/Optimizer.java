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

import java.math.BigDecimal;
import java.util.List;

import static pl.wysockif.optimizer.io.ErrorsHandler.INCORRECT_CALL;

public class Optimizer {
    public static boolean isLimit;
    private static String inputFilePath;
    private static String outputFilePath;

    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;
    private DataHandler minCostMaxFlow;
    private MaxFlow maxFlow;


    public static void main(String[] args) {
        validateArguments(args);
        Optimizer optimizer = new Optimizer();
        optimizer.loadData();
        Graph initialGraph = optimizer.prepare();
        Graph finalGraph = optimizer.optimize(initialGraph);
        optimizer.saveResults(finalGraph);
    }

    private void loadData() {
        System.out.print("(1/4) TRWA ODCZYTYWANIE I WALIDACJA DANYCH... ");
        long before = System.nanoTime();
        InputFileReader inputFileReader = new InputFileReader(inputFilePath);
        producers = inputFileReader.getProducers();
        pharmacies = inputFileReader.getPharmacies();
        connections = inputFileReader.getConnections();
        double time = (double)(System.nanoTime() - before) / 1_000_000_000.0;
        System.out.printf("[ %.4fs ]\n", time);
    }

    public Graph prepare() {
        System.out.print("(2/4) TRWA PRZYGOTOWYWANIE... ");
        long before = System.nanoTime();
        minCostMaxFlow = new DataHandler(producers, pharmacies, connections);
        FindingShortestPath findingPath = new BellmanFord();
        maxFlow = new FordFulkerson(findingPath);
        Graph graph = minCostMaxFlow.createGraph();
        double time = (double)(System.nanoTime() - before) / 1_000_000_000.0;
        System.out.printf("[ %.4fs ]\n", time);
        return graph;
    }

    private Graph optimize(Graph initialGraph) {
        System.out.print("(3/4) TRWA OBLICZANIE... ");
        long before = System.nanoTime();
        Graph finalGraph = this.maxFlow.findMaxFlow(initialGraph);
        double time = (double)(System.nanoTime() - before) / 1_000_000_000.0;
        System.out.printf("[ %.4fs ]\n", time);
        return finalGraph;
    }

    public void saveResults(Graph finalGraph) {
        System.out.print("(4/4) TRWA ZAPISYWANIE... ");
        long before = System.nanoTime();
        List<Deal> deals = minCostMaxFlow.loadResults(finalGraph);
        OutputFileWriter outputFileWriter = new OutputFileWriter(outputFilePath);
        double price = outputFileWriter.saveDeals(deals);
        double time = (double)(System.nanoTime() - before) / 1_000_000_000.0;
        System.out.printf("[ %.4fs ]\n", time);
        System.out.println("Wynik został pomyślnie zapisany w pliku: " + outputFilePath + ".\n" +
                "Całkowity koszt wyniósł: " + BigDecimal.valueOf(price).toPlainString() + " zł.");
    }

    private static void validateArguments(String[] args) {
        if (args.length < 2 || args.length > 3) {
            String message = "[WYWOŁANIE] Nie poprawna ilość argumentów wywołania";
            ErrorsHandler.handleError(INCORRECT_CALL, message);
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
            ErrorsHandler.handleError(INCORRECT_CALL, message);
        }
    }
}
