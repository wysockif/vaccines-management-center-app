package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.algorithms.path.FindingPathAlgorithm;
import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.List;

public class EdmondsKarpAlgorithm implements MaxFlowAlgorithm {
    public static final int INFINITY = Integer.MAX_VALUE;
    private FindingPathAlgorithm findingPathAlgorithm;

    public EdmondsKarpAlgorithm(FindingPathAlgorithm findingPathAlgorithm) {
        this.findingPathAlgorithm = findingPathAlgorithm;
    }

    public void findMaxFlow(Graph graph) {
        int maxFlow = 0;
        int numberOfVertices = graph.getNumberOfVertices();

        Graph residualGraph = createResidualGraph(graph);
        List<Integer> path = findingPathAlgorithm.findPath(residualGraph);

        int n = 10;
//        while (!path.isEmpty()) {
        while (n > 0) {
            int minCapacity = findMinCapacity(residualGraph, path);
            increaseFlow(residualGraph, path, minCapacity);

            System.out.println(minCapacity + " : " + path);

            System.out.println(residualGraph);
            path = findingPathAlgorithm.findPath(residualGraph);
            n--;
        }


    }

    private void increaseFlow(Graph residualGraph, List<Integer> path, int flow) {
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            int capacity = residualGraph.getCapacityOfEdge(vertex1, vertex2);
            double price = residualGraph.getPriceOfEdge(vertex1, vertex2);
            residualGraph.increaseFlowOfEdge(vertex1, vertex2, capacity - flow);

            if (!residualGraph.isEdgeExist(vertex2, vertex1)) {
                residualGraph.addEdge(vertex2, vertex1, flow, capacity, -price);
            } else {
                residualGraph.setFlowOfEdge(vertex2, vertex1, flow);
                residualGraph.setPriceOfEdge(vertex2, vertex1, -price);
            }

            if (residualGraph.getCapacityOfEdge(vertex1, vertex2) == flow) {
                residualGraph.removeEdge(vertex1, vertex2);
                System.out.println("here");
            }
        }
    }

    private int findMinCapacity(Graph residualGraph, List<Integer> path) {
        int minCapacity = INFINITY;
        int sizeOfPath = path.size();
        for (int i = 0; i < sizeOfPath - 1; i++) {
            int vertex1 = path.get(i);
            int vertex2 = path.get(i + 1);
            if (minCapacity > residualGraph.getCapacityOfEdge(vertex1, vertex2) - residualGraph.getFlowOfEdge(vertex1, vertex2)) {
                minCapacity = residualGraph.getCapacityOfEdge(vertex1, vertex2) - residualGraph.getFlowOfEdge(vertex1, vertex2);
            }
        }
        return minCapacity;
    }

    private Graph createResidualGraph(Graph graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        Graph residualGraph = new WeightedGraph(numberOfVertices);
        for (int u = 0; u < numberOfVertices; u++) {
            for (int v = 0; v < numberOfVertices; v++) {
                copyEdge(graph, residualGraph, u, v);
            }
        }
        return residualGraph;
    }

    private void copyEdge(Graph graph, Graph residualGraph, int u, int v) {
        if (graph.isEdgeExist(u, v)) {
            int capacity = graph.getCapacityOfEdge(u, v);
            double price = graph.getPriceOfEdge(u, v);
            int flow = graph.getFlowOfEdge(u, v);
            residualGraph.addEdge(u, v, flow, capacity, price);
        }
    }
}
