package pl.wysockif.optimizer.algorithms;

public class Deal {
    private String producerName;
    private String pharmacyName;
    private int amount;
    private double price;

    public Deal(String producerName, String pharmacyName, int amount, double price) {
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

    public double getPrice() {
        return price;
    }

//    public void setPrice(double price) {
//        this.price = price;
//    }
}
