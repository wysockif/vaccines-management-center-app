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

    private void saveLineToFile(String line) {
        printWriter.println(line);
    }

    private String concatenateLine(int length, String producerName, String pharmacyName, int amount, double price) {
        producerName = rightPad(producerName, length);
        double totalCost = (double) amount * price / 100;
        return producerName + " -> " + pharmacyName +
                " [Koszt = " + amount + " * " + price / 100 + " = " + totalCost + " zł]";
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
        if (!deals.isEmpty()) {
            longestNameLength = findLongestNameLength(deals);
        }
        while (!deals.isEmpty()) {
            totalCost = saveSingleDeal(deals, longestNameLength, totalCost);
        }
        saveTheSummary(totalCost);
        printWriter.close();
    }

    private int saveSingleDeal(List<Deal> deals, int longestNameLength, int totalCost) {
        Deal deal = deals.remove(0);
        int price = deal.getPrice();
        int amount = deal.getAmount();
        totalCost += price * amount;
        String line = concatenateLine(longestNameLength, deal.getProducerName(), deal.getPharmacyName(), deal.getAmount(), deal.getPrice());
        saveLineToFile(line);
        return totalCost;
    }

    private void saveTheSummary(int totalCost) {
        double convertedTotalCost = (double) totalCost / 100.0;
        String line = "Opłaty całkowite: " + convertedTotalCost + " zł";
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
