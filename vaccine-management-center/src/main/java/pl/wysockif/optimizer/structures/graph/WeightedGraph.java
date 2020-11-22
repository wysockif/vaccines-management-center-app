package pl.wysockif.optimizer.structures.graph;

public class WeightedGraph implements Graph {
    private final int numberOfVertices;
    private final Edge[][] graph;

    public WeightedGraph(int numberOfVertices) {
        graph = new Edge[numberOfVertices][numberOfVertices];
        this.numberOfVertices = numberOfVertices;
    }

    @Override
    public void addEdge(int from, int to, int flow, int capacity, double price) {
        if (areVerticesExist(from, to)) {
            throw new IllegalArgumentException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        }
        Edge edge = new Edge(flow, capacity, price);
        graph[from][to] = edge;
    }

    @Override
    public boolean isEdgeExist(int from, int to) {
        return graph[from][to] != null;
    }

    @Override
    public double getPriceOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można pobrać ceny z nieistniejącej krawędzi");
        return graph[from][to].getPrice();
    }

    private void checkCorrectnessOfOperation(int from, int to, String message) {
        if (areVerticesExist(from, to)) {
            throw new UnsupportedOperationException("Conajmniej jeden z podanych wierzchołków nie istnieje");
        } else if (!isEdgeExist(from, to)) {
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public int getFlowOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można pobrać przepływu z nieistniejącej krawędzi");
        return graph[from][to].getFlow();
    }

    @Override
    public int getCapacityOfEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można pobrać przepustowości z nieistniejącej krawędzi");
        return graph[from][to].getCapacity();
    }

    @Override
    public void setFlowOfEdge(int from, int to, int flow) {
        checkCorrectnessOfOperation(from, to, "Nie można ustawić przepływu w nieistniejącej krawędzi");
        graph[from][to].setFlow(flow);

    }


    @Override
    public void setCapacityOfEdge(int from, int to, int capacity) {
        checkCorrectnessOfOperation(from, to, "Nie można ustawić przepustowości w nieistniejącej krawędzi");
        graph[from][to].setCapacity(capacity);
    }

    @Override
    public void setPriceOfEdge(int from, int to, double price) {
        checkCorrectnessOfOperation(from, to, "Nie można ustawić ceny w nieistniejącej krawędzi");
        graph[from][to].setPrice(price);
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public void increaseFlowOfEdge(int from, int to, int flow) {
        checkCorrectnessOfOperation(from, to, "Nie można zwwiększyć przepływu w nieistniejącej krawędzi");
        graph[from][to].increaseFlow(flow);
    }

    @Override
    public void removeEdge(int from, int to) {
        checkCorrectnessOfOperation(from, to, "Nie można skasować nieistniejącej krawędzi");
        graph[from][to] = null;
    }

    private boolean areVerticesExist(int from, int to) {
        return (from >= numberOfVertices || to >= numberOfVertices);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                if (graph[i][j] == null) {
                    string.append(String.format("[%12s] ", "0/0, 0.0"));
                } else {
                    string.append(graph[i][j]);
                }
            }
            string.append("\n");
        }
        return String.valueOf(string);
    }
}
