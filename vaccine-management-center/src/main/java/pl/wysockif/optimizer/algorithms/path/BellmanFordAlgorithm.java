package pl.wysockif.optimizer.algorithms.path;

import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class BellmanFordAlgorithm implements FindingPathAlgorithm {
    public static final int INFINITY = MAX_VALUE;
    public static final int UNDEFINED = -1;

    public BellmanFordAlgorithm() {

    }

    @Override
    public List<Integer> findPath(Graph residualGraph) {
        int numberOfVertices = residualGraph.getNumberOfVertices();
        double[] distances = initializeDistancesArray(numberOfVertices);
        int[] predecessors = initializePredecessorsArray(numberOfVertices);

        for (int i = 0; i < numberOfVertices - 1; i++) {
            relaxEdges(residualGraph, numberOfVertices, distances, predecessors);
        }

        List<Integer> cheapestPath = findCheapestPath(predecessors);

        return cheapestPath;
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

    private void relaxEdges(Graph residualGraph, int numberOfVertices, double[] distances, int[] predecessors) {
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                relaxEdge(residualGraph, distances, predecessors, u, v);
            }
        }
    }

    private void relaxEdge(Graph residualGraph, double[] distances, int[] predecessors, int u, int v) {
        if (residualGraph.isEdgeExist(u, v)) {
            if (distances[u] + residualGraph.getPriceOfEdge(u, v) < distances[v]) {
                distances[v] = distances[u] + residualGraph.getPriceOfEdge(u, v);
                predecessors[v] = u;
            }
        }
    }

    private int[] initializePredecessorsArray(int numberOfVertices) {
        int[] predecessors = new int[numberOfVertices];
        Arrays.fill(predecessors, UNDEFINED);
        return predecessors;
    }

    private double[] initializeDistancesArray(int numberOfVertices) {
        double[] distances = new double[numberOfVertices];
        Arrays.fill(distances, INFINITY);
        distances[0] = 0.0;
        return distances;
    }

}
