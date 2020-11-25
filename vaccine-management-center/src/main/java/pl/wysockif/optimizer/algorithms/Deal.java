package pl.wysockif.optimizer.algorithms;

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

}
