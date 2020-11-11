import items.Connections;
import items.Pharmacies;
import items.Producers;

public class Main {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Main(){
        InputFile inputFile = new InputFile("src/main/resources/data.txt");
        inputFile.loadDataFromFile();
        producers = inputFile.getProducers();
        pharmacies = inputFile.getPharmacies();
        connections = inputFile.getConnections();
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

}
