package pl.wysockif.optimizer.items.pharmacies;

import java.util.Objects;

public class Pharmacy {

    private final int id;
    private final String name;
    private final int dailyDemand;

    public Pharmacy(int id, String name, int dailyDemand) {
        this.id = id;
        this.name = name;
        this.dailyDemand = dailyDemand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDailyDemand() {
        return dailyDemand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dailyRequirement=" + dailyDemand +
                '}';
    }
}
