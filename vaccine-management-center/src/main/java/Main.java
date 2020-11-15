import items.Connections;
import items.Item;
import items.Pharmacies;
import items.Producers;

public class Main {
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;


    public Main(){
//        InputFile inputFile = new InputFile("src/main/resources/data.txt");
//        inputFile.loadDataFromFile();
//        producers = inputFile.getProducers();
//        pharmacies = inputFile.getPharmacies();
//        connections = inputFile.getConnections();
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/data.txt");
        OutputFileWriter outputFileWriter = new OutputFileWriter("src/main/resources/output.txt");


    }

    public static void main(String[] args) {
        Main main = new Main();
    }

}
