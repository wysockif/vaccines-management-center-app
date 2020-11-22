package pl.wysockif.optimizer.items.producers;

import java.util.Objects;

public class Producer {
    private long id;
    private String name;
    private int dailyProduction;

    public Producer(long id, String name, int dailyProduction) {
        this.id = id;
        this.name = name;
        this.dailyProduction = dailyProduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return id == producer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(int dailyProduction) {
        this.dailyProduction = dailyProduction;
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
