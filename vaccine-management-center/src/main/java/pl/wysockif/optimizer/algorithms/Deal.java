package pl.wysockif.optimizer.algorithms;

import java.util.Objects;

public class Deal {
    private final String producerName;
    private final String pharmacyName;
    private final int amount;
    private final int price;

    public Deal(String producerName, String pharmacyName, int amount, int price) {
        this.producerName = producerName;
        this.pharmacyName = pharmacyName;
        this.amount = amount;
        this.price = price;
    }

    public String getProducerName() {
        return producerName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deal deal = (Deal) o;
        return amount == deal.amount &&
                price == deal.price &&
                Objects.equals(producerName, deal.producerName) &&
                Objects.equals(pharmacyName, deal.pharmacyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producerName, pharmacyName, amount, price);
    }

    @Override
    public String toString() {
        return "Deal{" +
                "producerName='" + producerName + '\'' +
                ", pharmacyName='" + pharmacyName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
