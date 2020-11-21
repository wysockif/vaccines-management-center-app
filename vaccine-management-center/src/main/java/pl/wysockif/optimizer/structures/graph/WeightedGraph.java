package pl.wysockif.optimizer.structures.graph;

public class WeightedGraph implements Graph {
    private final int numberOfVertices;
    private final double[][] graph;

    public WeightedGraph(int numberOfVertices) {
        graph = new double[numberOfVertices][numberOfVertices];
        this.numberOfVertices = numberOfVertices;
    }

    @Override
    public void setWeightOfEdge(int from, int to, double weight) {
        checkIfVerticesExist(from, to);
        graph[from][to] = weight;
    }

    @Override
    public double getWeightOfEdge(int from, int to){
        checkIfVerticesExist(from, to);
        return graph[from][to];
    }

    private void checkIfVerticesExist(int from, int to) {
        if(from >= numberOfVertices || to >= numberOfVertices){
            throw new IllegalArgumentException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
    }

}
