package pl.wysockif.optimizer.algorithms.path;

import pl.wysockif.optimizer.structures.graph.Graph;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BellmanFordAlgorithm implements FindingPathAlgorithm {
    public static final int INFINITY = 100_000;
    public static final int UNDEFINED = -1;


    @Override
    public List<Integer> findPath(Graph residualGraph) {
        int numberOfVertices = residualGraph.getNumberOfVertices();
        BigDecimal[] distances = initializeDistancesArray(numberOfVertices);
        int[] predecessors = initializePredecessorsArray(numberOfVertices);
        for (int i = 0; i < numberOfVertices - 1; i++) {
            relaxEdges(residualGraph, numberOfVertices, distances, predecessors);
        }
        checkForNegativeCycle(residualGraph, numberOfVertices, distances);

        if (!distances[numberOfVertices - 1].equals(INFINITY)) {
            return findCheapestPath(predecessors);
        }
        return new LinkedList<>();
    }

    private void checkForNegativeCycle(Graph residualGraph, int numberOfVertices, BigDecimal[] distances) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                if (residualGraph.containsEdge(u, v)) {
                    BigDecimal price = BigDecimal.valueOf(residualGraph.getPriceOfEdge(u, v));
                    if (distances[u].add(price).compareTo(distances[v]) < 0) {
                        throw new UnsupportedOperationException("Ujemny cykl");
                    }
                }
            }
        }

    }

    private List<Integer> findCheapestPath(int[] predecessors) {
        List<Integer> cheapestPath = new LinkedList<>();
        int predecessorIndex = predecessors.length - 1;
        if (predecessors[predecessorIndex] == UNDEFINED) {
            return cheapestPath;
        }
        while (predecessorIndex != UNDEFINED) {

            cheapestPath.add(predecessorIndex);
            predecessorIndex = predecessors[predecessorIndex];

        }
        Collections.reverse(cheapestPath);
        return cheapestPath;
    }

    private void relaxEdges(Graph residualGraph, int numberOfVertices, BigDecimal[] distances, int[] predecessors) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                relaxEdge(residualGraph, distances, predecessors, u, v);
            }
        }
    }

    private void relaxEdge(Graph residualGraph, BigDecimal[] distances, int[] predecessors, int u, int v) {
        if (residualGraph.containsEdge(u, v)) {
            BigDecimal price = BigDecimal.valueOf(residualGraph.getPriceOfEdge(u, v));
            if (distances[u].add(price).compareTo(distances[v]) < 0) {
                BigDecimal value = distances[u].add(price);
                distances[v] = new BigDecimal(String.valueOf(value));
                predecessors[v] = u;
            }
        }
    }

    private int[] initializePredecessorsArray(int numberOfVertices) {
        int[] predecessors = new int[numberOfVertices];
        Arrays.fill(predecessors, UNDEFINED);
        return predecessors;
    }

    private BigDecimal[] initializeDistancesArray(int numberOfVertices) {
        BigDecimal[] distances = new BigDecimal[numberOfVertices];
        for (int i = 1; i < numberOfVertices; i++) {
            distances[i] = new BigDecimal(INFINITY);
        }
        distances[0] = new BigDecimal("0.0");
        return distances;
    }

}
