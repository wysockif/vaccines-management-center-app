package pl.wysockif.optimizer.items.producers;

import java.util.Objects;

public class Producer {
    private final int id;
    private final String name;
    private final int dailyProduction;

    public Producer(int id, String name, int dailyProduction) {
        this.id = id;
        this.name = name;
        this.dailyProduction = dailyProduction;
    }

    public int getId() {
        return id;
    }

    public int getDailyProduction() {
        return dailyProduction;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producer producer = (Producer) o;
        return id == producer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dailyProduction=" + dailyProduction +
                '}';
    }
}
