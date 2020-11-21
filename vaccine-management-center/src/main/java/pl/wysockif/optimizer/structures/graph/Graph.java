package pl.wysockif.optimizer.structures.graph;

public interface Graph {
    void setWeightOfEdge(int from, int to, double weight);

    double getWeightOfEdge(int from, int to);
}
