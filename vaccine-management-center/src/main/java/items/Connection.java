package items;

import java.util.Objects;

public class Connection {

    private long producerId;
    private long pharmacyId;
    private int maxNumberOVaccines;
    private double price;

    public Connection(long producerId, long pharmacyId, int maxNumberOVaccines, double price) {
        this.producerId = producerId;
        this.pharmacyId = pharmacyId;
        this.maxNumberOVaccines = maxNumberOVaccines;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return producerId == that.producerId &&
                pharmacyId == that.pharmacyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(producerId, pharmacyId);
    }

    public long getProducerId() {
        return producerId;
    }

    public void setProducerId(long producerId) {
        this.producerId = producerId;
    }

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public int getMaxNumberOVaccines() {
        return maxNumberOVaccines;
    }

    public void setMaxNumberOVaccines(int maxNumberOVaccines) {
        this.maxNumberOVaccines = maxNumberOVaccines;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "producerId=" + producerId +
                ", pharmacyId=" + pharmacyId +
                ", maxNumberOVaccines=" + maxNumberOVaccines +
                ", price=" + price +
                '}';
    }
}
