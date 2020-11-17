package pl.wysockif.optimizer.items.pharmacies;

import java.util.Objects;

public class Pharmacy {
    private long id;
    private String name;
    private int dailyRequirement;

    public Pharmacy(long id, String name, int dailyRequirement) {
        this.id = id;
        this.name = name;
        this.dailyRequirement = dailyRequirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id;
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

    public int getDailyRequirement() {
        return dailyRequirement;
    }

    public void setDailyRequirement(int dailyRequirement) {
        this.dailyRequirement = dailyRequirement;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dailyRequirement=" + dailyRequirement +
                '}';
    }
}
