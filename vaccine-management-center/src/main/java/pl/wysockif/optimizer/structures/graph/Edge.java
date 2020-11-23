package pl.wysockif.optimizer.structures.graph;

public class Edge {
    private int capacity;
    private double price;

    public Edge(int capacity, double price) {
        this.capacity = capacity;
        this.price = price;
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

    public void increaseCapacity(int amount) {
        capacity += amount;
    }
}