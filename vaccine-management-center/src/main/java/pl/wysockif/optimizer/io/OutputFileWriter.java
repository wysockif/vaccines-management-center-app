package pl.wysockif.optimizer.io;

import pl.wysockif.optimizer.algorithms.Deal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class OutputFileWriter {
    public static String fileName;
    private final PrintWriter printWriter;

    public OutputFileWriter(String path) {
        File file = new File(path);
        fileName = file.getName();
        printWriter = createPrintWriterIfFileExists(file);
    }

    public void saveLineToFile(String line) {
        printWriter.println(line);
    }

    public String concatenateLine(int length, String producerName, String pharmacyName, int amount, double price) {
        producerName = rightPad(producerName, length);
        return producerName + " -> " + pharmacyName +
                " [Koszt = " + amount + " * " + (double) price / 100 + " = " + (double) amount * price / 100 + " zł]";
    }

    private String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private PrintWriter createPrintWriterIfFileExists(File outputFile) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            String message = "[Plik wyjściowy: " + fileName + "]. Plik nie został znaleziony.";
            ErrorsHandler.handleTheError(ErrorsHandler.OUTPUT_FILE_NOT_FOUND, message);
        }
        return printWriter;
    }


    public void saveDeals(List<Deal> deals) {
        int longestNameLength = 0;
        int totalCost = 0;
        if (!deals.isEmpty())
            longestNameLength = findLongestNameLength(deals);

        while (!deals.isEmpty()) {
            Deal deal = deals.remove(0);
            int price = deal.getPrice();
            int amount = deal.getAmount();
            totalCost += price * amount;
            String line = concatenateLine(longestNameLength, deal.getProducerName(), deal.getPharmacyName(), deal.getAmount(), deal.getPrice());
            saveLineToFile(line);
        }

        saveTheSummary(totalCost);
        printWriter.close();
    }

    private void saveTheSummary(int totalCost) {
        String line = "Opłaty całkowite: " + (double) totalCost / 100;
        System.out.println((double) totalCost / 100);
        saveLineToFile(line);
    }

    private int findLongestNameLength(List<Deal> deals) {
        int longestName = deals.get(0).getProducerName().length();

        for (Deal deal : deals) {
            if (deal.getProducerName().length() > longestName) {
                longestName = deal.getProducerName().length();
            }
        }
        return longestName;
    }
}
