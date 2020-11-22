package pl.wysockif.optimizer.structures.graph;

public class Edge {
    private int flow;
    private int capacity;
    private double price;

    public Edge(int capacity, double price) {
        this.capacity = capacity;
        this.price = price;
        flow = 0;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("[%12s] ",  +
                + flow + "/"
                + capacity +
                ", " + price );
    }
}