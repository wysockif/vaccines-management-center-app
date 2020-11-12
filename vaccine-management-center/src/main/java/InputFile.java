import items.Connection;
import items.Connections;
import items.Pharmacies;
import items.Pharmacy;
import items.Producer;
import items.Producers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class InputFile {
    private int numberOfLine = 0;
    private Scanner scanner;
    private Producers producers;
    private Pharmacies pharmacies;
    private Connections connections;

    public InputFile(String path) {
        File file = new File(path);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Nie mogę znaleźć pliku: " + path);
            exit(-1);
        }
    }

    public void loadDataFromFile() {
        producers = loadProducers();
        pharmacies = loadPharmacies();
        connections = loadConnections();
    }

    private Connections loadConnections() {
        int numberOfConnections = 0;
        Connections connections = new Connections(producers, pharmacies);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            numberOfLine++;
            if (!line.startsWith("#")) {
                String[] args = line.split(Pattern.quote(" | "));
                if (args.length != 4) {
                    System.err.println("Niepoprawny format danych w linii " + numberOfLine);
                    exit(-1);
                }
                try {
                    long producerId = Long.parseLong(args[0]);
                    long pharmacyId = Long.parseLong(args[1]);
                    int maxNumberOfVaccines = Integer.parseInt(args[2]);
                    double price = Double.parseDouble(args[3]);

                    Connection connection = new Connection(producerId, pharmacyId, maxNumberOfVaccines, price);
                    try {
                        connections.addANewConnection(connection);
                        numberOfConnections++;
                    } catch (IllegalArgumentException | UnsupportedOperationException e){
                        System.err.println("Nastąpił błąd w pliku wejściowym. Linia nr: " + numberOfLine  + " " + e.getMessage());
                        exit(-1);
                    }
                    System.out.println(connection);

                } catch (NumberFormatException e) {
                    System.err.println("Niepoprawny format danych w linii " + numberOfLine);
                    exit(-1);
                }
            } else
                break;
        }

        if(numberOfConnections != producers.getNumberOfProducers() * pharmacies.getNumberOfPharmacies()){
            System.err.println("Brakuje połączeń między producentami a aptekami!");
            exit(-1);
        }
        return connections;
    }

    private Pharmacies loadPharmacies() {
        Pharmacies pharmacies = new Pharmacies();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            numberOfLine++;
            if (!line.startsWith("#")) {
                String[] args = line.split(Pattern.quote(" | "));
                if (args.length != 3) {
                    System.err.println("Niepoprawny format danych w linii " + numberOfLine);
                    exit(-1);
                }
                try {
                    long id = Long.parseLong(args[0]);
                    String name = args[1];
                    int dailyRequirement = Integer.parseInt(args[2]);
                    Pharmacy pharmacy = new Pharmacy(id, name, dailyRequirement);
                    try {
                        pharmacies.addANewPharmacy(pharmacy);
                    } catch (IllegalArgumentException | UnsupportedOperationException e){
                        System.err.println("Nastąpił błąd w pliku wejściowym. Linia nr: " + numberOfLine  + " " + e.getMessage());
                        exit(-1);
                    }
                    System.out.println(pharmacy);

                } catch (NumberFormatException e) {
                    System.err.println("Niepoprawny format danych w linii " + numberOfLine);
                    exit(-1);
                }
            } else
                break;
        }
        return pharmacies;
    }

    private Producers loadProducers() {
        if (scanner.hasNext()) {
            String headline = scanner.nextLine();
            numberOfLine++;
            if (!headline.startsWith("#")) {
                System.err.println("Nieprawny nagłówek w linii: " + numberOfLine);
                exit(-1);
            }
        }
        Producers producers = new Producers();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            numberOfLine++;
            if (!line.startsWith("#")) {
                String[] args = line.split(Pattern.quote(" | "));
                if (args.length != 3) {
                    System.err.println("Niepoprawny format danych! Linia: " + numberOfLine);
                    exit(-1);
                }
                try {
                    long id = Long.parseLong(args[0]);
                    String name = args[1];
                    int dailyProduction = Integer.parseInt(args[2]);
                    Producer producer = new Producer(id, name, dailyProduction);
                    System.out.println(producer);
                    try {
                        producers.addANewProducer(producer);
                    } catch(UnsupportedOperationException | IllegalArgumentException e){
                        System.err.println("Niepoprawny format pliku. Linia: " + numberOfLine + ". " + e.getMessage());
                        exit(-1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Niepoprawny format danych w linii " + numberOfLine);
                    exit(-1);
                }
            } else
                break;
        }


        return producers;
    }


    public Producers getProducers() {
        return producers;
    }

    public Pharmacies getPharmacies() {
        return pharmacies;
    }

    public Connections getConnections() {
        return connections;
    }
}
