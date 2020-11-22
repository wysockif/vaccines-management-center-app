package pl.wysockif.optimizer.algorithms;

import pl.wysockif.optimizer.structures.graph.Graph;
import pl.wysockif.optimizer.structures.graph.WeightedGraph;

import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;

public class BellmanFordAlgorithm {
    public static final int INFINITY = MAX_VALUE;

    public BellmanFordAlgorithm(){

    }

    public void findShortestPath(Graph graph){
        int numberOfVertices = graph.getNumberOfVertices();
        double[] distance = new double[numberOfVertices];
        Arrays.fill(distance, INFINITY);
        distance[0] = 0.0;

        for (int i = 0; i < numberOfVertices - 1; i++) {
            for (int j = 0; j < numberOfVertices - 1; j++) {
                if (graph.isEdgeExist(i,j)){
                    if(distance[i] < distance[j] + graph.getPriceOfEdge(i,j)){
                        distance[i] = distance[j] + graph.getPriceOfEdge(i,j);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        BellmanFordAlgorithm bellmanFordAlgorithm = new BellmanFordAlgorithm();
        bellmanFordAlgorithm.findShortestPath(new WeightedGraph(23));
    }

}
