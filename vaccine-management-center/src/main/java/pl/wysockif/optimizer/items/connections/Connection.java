package pl.wysockif.optimizer.items.connections;

import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;

import java.util.Objects;

public class Connection {
    private final Producer producer;
    private final Pharmacy pharmacy;
    private final int maxNumberOVaccines;
    private final int price;

    public Connection(Producer producer, Pharmacy pharmacy, int maxNumberOVaccines, int price) {
        this.producer = producer;
        this.pharmacy = pharmacy;
        this.maxNumberOVaccines = maxNumberOVaccines;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(producer, that.producer) &&
                Objects.equals(pharmacy, that.pharmacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, pharmacy);
    }

    public Producer getProducer() {
        return producer;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public int getMaxNumberOVaccines() {
        return maxNumberOVaccines;
    }

    public int getPrice() {
        return price;
    }

}
