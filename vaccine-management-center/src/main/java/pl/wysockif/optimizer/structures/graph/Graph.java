package pl.wysockif.optimizer.structures.graph;

public interface Graph {


    double getPriceOfEdge(int from, int to);

    void addEdge(int from, int to, int capacity, double price);

    boolean containsEdge(int from, int to);

    int getCapacityOfEdge(int from, int to);

    int getNumberOfVertices();

    void removeEdge(int vertex1, int vertex2);

    void increaseCapacityOfEdge(int from, int to, int amount);

    void setPriceOfEdge(int from, int to, double price);
}
