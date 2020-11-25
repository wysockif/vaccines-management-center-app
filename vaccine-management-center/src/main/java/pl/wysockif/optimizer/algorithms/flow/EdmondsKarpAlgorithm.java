package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.algorithms.path.FindingPathAlgorithm;
import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

public class EdmondsKarpAlgorithm implements MaxFlowAlgorithm {
    public static final int INFINITY = Integer.MAX_VALUE;
    private final FindingPathAlgorithm findingPathAlgorithm;

    public EdmondsKarpAlgorithm(FindingPathAlgorithm findingPathAlgorithm) {
        this.findingPathAlgorithm = findingPathAlgorithm;
    }

    public Graph findMaxFlow(Graph residualGraph) {
        List<Integer> path = findingPathAlgorithm.findPath(residualGraph);
        while (!path.isEmpty()) {
            int minCapacity = findMinCapacity(residualGraph, path);
            increaseFlow(residualGraph, path, minCapacity);
            path = findingPathAlgorithm.findPath(residualGraph);
            System.out.println(path);
        }

        return residualGraph;
    }


    private void increaseFlow(Graph residualGraph, List<Integer> path, int flow) {
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            double price = residualGraph.getPriceOfEdge(vertex1, vertex2);


            residualGraph.increaseCapacityOfEdge(vertex1, vertex2, -flow);

//            if (residualGraph.containsEdge(vertex2, vertex1))
                residualGraph.increaseCapacityOfEdge(vertex2, vertex1, flow);

                //            else
                residualGraph.setPriceOfEdge(vertex2, vertex1, -price);

        }
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
