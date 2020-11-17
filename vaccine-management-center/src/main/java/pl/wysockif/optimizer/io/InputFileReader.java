package pl.wysockif.optimizer.io;

import pl.wysockif.optimizer.items.connections.Connections;
import pl.wysockif.optimizer.items.Item;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class InputFileReader {
    private int lineNumber;
    private final String fileName;
    private final Scanner scanner;
    private final Producers producers;
    private final Pharmacies pharmacies;
    private final Connections connections;

    public InputFileReader(String path) {
        File inputFile = new File(path);
        fileName = inputFile.getName();
        lineNumber = 0;
        scanner = createScannerIfTheSpecifiedFileExists(inputFile);
        producers = loadProducersFromFile(scanner);
        pharmacies = loadPharmaciesFromFile(scanner);
        connections = loadConnectionsFromFile(scanner, producers, pharmacies);
    }

    private Producers loadProducersFromFile(Scanner scanner) {
        checkHeadline();
        return (Producers) readDataFromFile(scanner, new Producers());
    }

    private Pharmacies loadPharmaciesFromFile(Scanner scanner) {
        return (Pharmacies) readDataFromFile(scanner, new Pharmacies());
    }

    private Connections loadConnectionsFromFile(Scanner scanner, Producers producers, Pharmacies pharmacies) {
        Connections connections = (Connections) readDataFromFile(scanner, new Connections(producers, pharmacies));
        checkNumberOfConnections(connections);
        return connections;
    }

    private void checkNumberOfConnections(Connections connections) {
        if (connections.getSize() != connections.getExpectedSize()) {
            String missing = findMissingConnection(connections);
            String message = "[Plik wejściowy: " + fileName + "]. Nie znaleziono wystarczającej liczby połączeń." +
                    " Brakujące połączenie: " + missing + ".";
            Errors.handleTheError(Errors.INSUFFICIENT_DATA, message);
        }
    }

    private String findMissingConnection(Connections connections) {
        String missing = "";
        for (Producer producer : producers) {
            for (Pharmacy pharmacy : pharmacies) {
                if (!connections.contain(producer.getId(), pharmacy.getId())) {
                    missing = "producent o id: " + producer.getId() + ", apteka o id: " + pharmacy.getId();
                    return missing;
                }
            }
        }
        return missing;
    }

    public Item readDataFromFile(Scanner scanner, Item item) {
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lineNumber++;
            if (isHeadlineCorrect(line)) {
                break;
            }
            loadSingleItem(item, line);
        }
        return item;
    }

    private void loadSingleItem(Item item, String line) {
        String[] attributes = line.split(Pattern.quote(" | "));
        try {
            Object[] convertedAttributes = item.convertAttributes(attributes);
            item.validateAttributes(convertedAttributes);
            item.addNewElement(convertedAttributes);
        } catch (DataFormatException e) {
            String message = "[Plik wejściowy: " + fileName + ", nr linii: " + lineNumber + "]. " + e.getMessage() +".";
            Errors.handleTheError(Errors.INCORRECT_FORMAT, message);
        }
    }

    private void checkHeadline() {
        String headline = null;
        if (scanner.hasNext()) {
            headline = scanner.nextLine();
            lineNumber++;
        }
        if (headline == null || !isHeadlineCorrect(headline)) {
            String message = "[Plik wejściowy: " + fileName + ", nr linii: " + lineNumber + "]. " + "Niepoprawny nagłówek.";
            Errors.handleTheError(Errors.INCORRECT_HEADLINE, message);
        }
    }

    private Scanner createScannerIfTheSpecifiedFileExists(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            String message = "[Plik wejściowy: " + fileName + "]. Plik nie został znaleziony.";
            Errors.handleTheError(Errors.INPUT_FILE_NOT_FOUND, message);
        }
        return scanner;
    }

    private boolean isHeadlineCorrect(String headline) {
        return headline.startsWith("#");
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
