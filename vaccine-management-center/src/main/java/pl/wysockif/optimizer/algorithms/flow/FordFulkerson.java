package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.algorithms.path.FindingShortestPath;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

public class FordFulkerson implements MaxFlow {
    public static final int INFINITY = Integer.MAX_VALUE;
    private final FindingShortestPath findingPathAlgorithm;

    public FordFulkerson(FindingShortestPath findingPathAlgorithm) {
        this.findingPathAlgorithm = findingPathAlgorithm;
    }

    @Override
    public Graph findMaxFlow(Graph residualGraph) {
        List<Integer> path = findingPathAlgorithm.findPath(residualGraph);
        while (!path.isEmpty()) {
            int minCapacity = findMinCapacity(residualGraph, path);
            increaseFlow(residualGraph, path, minCapacity);
            path = findingPathAlgorithm.findPath(residualGraph);
        }
        return residualGraph;
    }


    private void increaseFlow(Graph residualGraph, List<Integer> path, int flow) {
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            int price = residualGraph.getPriceOfEdge(vertex1, vertex2);
            changeValuesOnEdges(residualGraph, flow, vertex1, vertex2, price);
        }
    }

    private void changeValuesOnEdges(Graph residualGraph, int flow, int vertex1, int vertex2, int price) {
        residualGraph.increaseCapacityOfEdge(vertex1, vertex2, -flow);
        residualGraph.increaseCapacityOfEdge(vertex2, vertex1, flow);
        residualGraph.setPriceOfEdge(vertex2, vertex1, -price);
    }

    private int findMinCapacity(Graph residualGraph, List<Integer> path) {
        int minCapacity = INFINITY;
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            if (minCapacity > residualGraph.getCapacityOfEdge(vertex1, vertex2)) {
                minCapacity = residualGraph.getCapacityOfEdge(vertex1, vertex2);
            }
        }
        return minCapacity;
    }
}
