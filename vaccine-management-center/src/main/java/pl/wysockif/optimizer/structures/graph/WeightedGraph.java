package pl.wysockif.optimizer.structures.graph;

public class WeightedGraph implements Graph {
    private final int numberOfVertices;
    private final int[][] prices;
    private final int[][] capacities;

    public WeightedGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        prices = new int[numberOfVertices][numberOfVertices];
        capacities = new int[numberOfVertices][numberOfVertices];
    }

    @Override
    public void addEdge(int from, int to, int capacity, int price) {
        if (containsVertices(from, to)) {
            throw new IllegalArgumentException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
        prices[from][to] = price;
        capacities[from][to] = capacity;
    }

    @Override
    public boolean containsEdge(int from, int to) {
        return capacities[from][to] > 0;
    }

    @Override
    public int getPriceOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to);
        return prices[from][to];
    }

    private void checkCorrectnessOfOperation(int from, int to) {
        if (containsVertices(from, to)) {
            throw new UnsupportedOperationException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
    }


    @Override
    public int getCapacityOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to);
        return capacities[from][to];
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public void increaseCapacityOfEdge(int from, int to, int amount) {
        checkCorrectnessOfOperation(from, to);
        capacities[from][to] += amount;
    }

    private boolean containsVertices(int from, int to) {
        return (from >= numberOfVertices || to >= numberOfVertices);
    }

    @Override
    public void setPriceOfEdge(int from, int to, int price) {
        prices[from][to] = price;
    }
}
