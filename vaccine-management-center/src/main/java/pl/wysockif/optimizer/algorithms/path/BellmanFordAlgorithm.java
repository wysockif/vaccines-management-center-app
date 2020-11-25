package pl.wysockif.optimizer.algorithms.path;

import pl.wysockif.optimizer.structures.graph.Graph;

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
        int[] distances = initializeDistancesArray(numberOfVertices);
//        BigDecimal[] distances = initializeDistancesArray(numberOfVertices);
        int[] predecessors = initializePredecessorsArray(numberOfVertices);
        for (int i = 0; i < numberOfVertices - 1; i++) {
            relaxEdges(residualGraph, numberOfVertices, distances, predecessors);
        }
        checkForNegativeCycle(residualGraph, numberOfVertices, distances);

        if (distances[numberOfVertices - 1] != INFINITY) {
            return findCheapestPath(predecessors);
        }
        return new LinkedList<>();
    }

    private void checkForNegativeCycle(Graph residualGraph, int numberOfVertices, int[] distances) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                if (residualGraph.containsEdge(u, v)) {
                    int price = residualGraph.getPriceOfEdge(u, v);
                    if (distances[u] + price <distances[v]) {
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

    private void relaxEdges(Graph residualGraph, int numberOfVertices, int[] distances, int[] predecessors) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                relaxEdge(residualGraph, distances, predecessors, u, v);
            }
        }
    }

    private void relaxEdge(Graph residualGraph, int[] distances, int[] predecessors, int u, int v) {
        if (residualGraph.containsEdge(u, v)) {
            int price = residualGraph.getPriceOfEdge(u, v);
            if (distances[u]+ price < distances[v]) {
                int value = distances[u] +price;
                distances[v] = value;
                predecessors[v] = u;
            }
        }
    }

    private int[] initializePredecessorsArray(int numberOfVertices) {
        int[] predecessors = new int[numberOfVertices];
        Arrays.fill(predecessors, UNDEFINED);
        return predecessors;
    }

    private int[] initializeDistancesArray(int numberOfVertices) {
        int[] distances = new int[numberOfVertices];
        Arrays.fill(distances, INFINITY);
        distances[0] = 0;
        return distances;
    }

}
