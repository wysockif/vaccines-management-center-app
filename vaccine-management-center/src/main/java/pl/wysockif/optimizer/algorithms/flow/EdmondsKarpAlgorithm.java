package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.algorithms.path.FindingPathAlgorithm;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.List;

public class EdmondsKarpAlgorithm implements MaxFlowAlgorithm {
    public static final int INFINITY = Integer.MAX_VALUE;
    private final FindingPathAlgorithm findingPathAlgorithm;

    public EdmondsKarpAlgorithm(FindingPathAlgorithm findingPathAlgorithm) {
        this.findingPathAlgorithm = findingPathAlgorithm;
    }

    public Graph findMaxFlow(Graph residualGraph) {
//        Graph residualGraph = createResidualGraph(graph);
        List<Integer> path = findingPathAlgorithm.findPath(residualGraph);
        while (!path.isEmpty()) {
            int minCapacity = findMinCapacity(residualGraph, path);
            increaseFlow(residualGraph, path, minCapacity);
            path = findingPathAlgorithm.findPath(residualGraph);
//            System.out.println(path);
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

            if (residualGraph.isEdgeExist(vertex2, vertex1))
                residualGraph.increaseCapacityOfEdge(vertex2, vertex1, flow);
            else
                residualGraph.addEdge(vertex2, vertex1, flow, -price);

            if (residualGraph.getCapacityOfEdge(vertex2, vertex1) == 0)
                residualGraph.removeEdge(vertex2, vertex1);

            if (residualGraph.getCapacityOfEdge(vertex1, vertex2) == 0)
                residualGraph.removeEdge(vertex1, vertex2);

        }
    }

    private int findMinCapacity(Graph residualGraph, List<Integer> path) {
        int minCapacity = INFINITY;
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            if (minCapacity > residualGraph.getCapacityOfEdge(vertex1, vertex2) ) {
                minCapacity = residualGraph.getCapacityOfEdge(vertex1, vertex2) ;
            }
        }
        return minCapacity;
    }

//    private Graph createResidualGraph(Graph graph) {
//        int numberOfVertices = graph.getNumberOfVertices();
//        Graph residualGraph = new WeightedGraph(numberOfVertices);
//        for (int u = 0; u < numberOfVertices; u++) {
//            for (int v = 0; v < numberOfVertices; v++) {
//                copyEdge(graph, residualGraph, u, v);
//
//            }
//        }
//        return residualGraph;
//    }

    private void copyEdge(Graph graph, Graph residualGraph, int u, int v) {
        if (graph.isEdgeExist(u, v)) {
            int capacity = graph.getCapacityOfEdge(u, v);
            double price = graph.getPriceOfEdge(u, v);
            residualGraph.addEdge(u, v, capacity, price);
        }
    }
}
