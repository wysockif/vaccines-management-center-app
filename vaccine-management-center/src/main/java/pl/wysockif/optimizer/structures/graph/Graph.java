package pl.wysockif.optimizer.structures.graph;

public interface Graph {


    double getPriceOfEdge(int from, int to);

    void addEdge(int from, int to, int capacity, double price);

    boolean isEdgeExist(int from, int to);

    double getFlowOfEdge(int from, int to);

    double getCapacityOfEdge(int from, int to);

    void setFlowOfEdge(int from, int to, int flow);

    void setCapacityOfEdge(int from, int to, int capacity);

    void setPriceOfEdge(int from, int to, double price);

    int getNumberOfVertices();
}
