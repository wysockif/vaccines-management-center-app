package pl.wysockif.optimizer.structures.graph;

public class WeightedGraph implements Graph {
    private final int numberOfVertices;
    private final double[][] prices;
    private final int[][] capacities;

    public WeightedGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        prices = new double[numberOfVertices][numberOfVertices];
        capacities = new int[numberOfVertices][numberOfVertices];
    }

    @Override
    public void addEdge(int from, int to, int capacity, double price) {
        if (!areVerticesExist(from, to)) {
            throw new IllegalArgumentException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
        prices[from][to] = price;
        capacities[from][to] = capacity;
    }

    @Override
    public boolean isEdgeExist(int from, int to) {
        return capacities[from][to] > 0;
    }

    @Override
    public double getPriceOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można pobrać ceny z nieistniejącej krawędzi");
        return prices[from][to];
    }

    private void checkCorrectnessOfOperation(int from, int to, String message) {
        if (!areVerticesExist(from, to)) {
            throw new UnsupportedOperationException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
    }


    @Override
    public int getCapacityOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można pobrać przepustowości z nieistniejącej krawędzi");
        return capacities[from][to];
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public void removeEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można skasować nieistniejącej krawędzi");
        capacities[from][to] = 0;
    }

    @Override
    public void increaseCapacityOfEdge(int from, int to, int amount) {
        checkCorrectnessOfOperation(from, to, "Nie można zwiększyć przepływu w nieistniejącej krawędzi");
        capacities[from][to] += amount;
    }

    private boolean areVerticesExist(int from, int to) {
        return (from < numberOfVertices && to < numberOfVertices);
    }

}
