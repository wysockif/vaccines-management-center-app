package pl.wysockif.optimizer.algorithms.path;

import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BellmanFord implements FindingShortestPath {
    public static final int INFINITY = 1_000_000_000;
    public static final int UNDEFINED = -1;


    @Override
    public List<Integer> findPath(Graph residualGraph) {
        int numberOfVertices = residualGraph.getNumberOfVertices();
        int[] distances = initializeDistancesArray(numberOfVertices);
        int[] predecessors = initializePredecessorsArray(numberOfVertices);

        relaxGraph(residualGraph, numberOfVertices, distances, predecessors);

        if (distances[numberOfVertices - 1] != INFINITY) {
            return findCheapestPath(predecessors);
        }
        return new LinkedList<>();
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

    private void relaxGraph(Graph residualGraph, int numberOfVertices, int[] distances, int[] predecessors) {
        boolean areChanges = true;
        for (int i = 0; i < numberOfVertices - 1 && areChanges; i++) {
            areChanges = relaxEdges(residualGraph, numberOfVertices, distances, predecessors);
        }
        checkForNegativeCycle(residualGraph, numberOfVertices, distances);
    }

    private void checkForNegativeCycle(Graph residualGraph, int numberOfVertices, int[] distances) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                tryToRelaxOneMoreTime(residualGraph, distances, u, v);
            }
        }

    }
    private void tryToRelaxOneMoreTime(Graph residualGraph, int[] distances, int u, int v) {
        if (residualGraph.containsEdge(u, v)) {
            int price = residualGraph.getPriceOfEdge(u, v);
            if (distances[u] + price < distances[v]) {
                String message = "Wykryto ujemny cykl w grafie";
                throw new UnsupportedOperationException(message);
            }
        }
    }

    private boolean relaxEdges(Graph residualGraph, int numberOfVertices, int[] distances, int[] predecessors) {
        boolean areChanges = false;
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                areChanges = relaxSingleEdge(residualGraph, distances, predecessors, areChanges, i, j);
            }
        }
        return areChanges;
    }

    private boolean relaxSingleEdge(Graph residualGraph, int[] distances, int[] predecessors, boolean areChanges, int u, int v) {
        if (residualGraph.containsEdge(u, v)) {
            int price = residualGraph.getPriceOfEdge(u, v);
            if (distances[u]+ price < distances[v]) {
                int value = distances[u] +price;
                distances[v] = value;
                predecessors[v] = u;
                areChanges = true;
            }
        }
        return areChanges;
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
