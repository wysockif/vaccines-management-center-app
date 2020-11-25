package pl.wysockif.optimizer.items.connections;

import org.jetbrains.annotations.NotNull;
import pl.wysockif.optimizer.items.Items;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Connections implements Items, Iterable<Connection> {
    private final List<Connection> connections;
    private final Producers producers;
    private final Pharmacies pharmacies;

    public Connections(Producers producers, Pharmacies pharmacies) {
        this.producers = producers;
        this.pharmacies = pharmacies;
        connections = new ArrayList<>();
    }

    @Override
    public Object[] parseAttributes(String[] attributes) throws DataFormatException {
        Object[] convertedAttributes = new Object[attributes.length];
        try {
            convertedAttributes[0] = Long.parseLong(attributes[0]);
            convertedAttributes[1] = Long.parseLong(attributes[1]);
            convertedAttributes[2] = Integer.parseInt(attributes[2]);
            convertedAttributes[3] = Double.parseDouble(attributes[3]);
        } catch (NumberFormatException e) {
            String info = e.getMessage().replaceAll("For input string: ", "");
            String message = "Nieudana konwersja danej: " + info;
            throw new DataFormatException(message);
        }
        return convertedAttributes;
    }

    @Override
    public Object[] convertAttributes(String[] attributes) throws DataFormatException {
        if (attributes.length != 4) {
            throw new DataFormatException("Niepoprawny format danych");
        }
        return parseAttributes(attributes);
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
        long producerId = (long) attributes[0];
        long pharmacyId = (long) attributes[1];
        checkWhetherExist(producerId, pharmacyId);
        CheckWhetherHasAlreadyContain(producerId, pharmacyId);

        int maxNumberOVaccines = (int) attributes[2];
        double price = (double) attributes[3];
        validateNumber(maxNumberOVaccines);
        validateNumber(price);
        attributes[3] = (int) Math.round(100 * price);
    }

    private void checkWhetherExist(long producerId, long pharmacyId) throws DataFormatException {
        if (!producers.alreadyContains(producerId)) {
            String message = "Nie istnieje producent o podanym id: " + producerId + ",";
            throw new DataFormatException(message);
        }
        if (!pharmacies.alreadyContains(pharmacyId)) {
            String message = "Nie istnieje apteka o podanym id: " + pharmacyId + ",";
            throw new DataFormatException(message);
        }
    }

    private void validateNumber(Number number) throws DataFormatException {
        if ((number instanceof Double && (double) number < 0) || (number instanceof Integer && (int) number < 0)) {
            String message = "Niepoprawny format danych. Ujemna wartość";
            throw new DataFormatException(message);
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        long producerId = (long) attributes[0];
        long pharmacyId = (long) attributes[1];
        int maxNumberOVaccines = (int) attributes[2];
        int price = (int) attributes[3];
        Producer producer = producers.getProducerById(producerId);
        Pharmacy pharmacy = pharmacies.getPharmacyById(pharmacyId);

        Connection connection = new Connection(producer, pharmacy, maxNumberOVaccines, price);
        connections.add(connection);
    }

    private void CheckWhetherHasAlreadyContain(long producerId, long pharmacyId) throws DataFormatException {
        for (Connection connection : connections) {
            if (connection.getProducer().getId() == producerId && connection.getPharmacy().getId() == pharmacyId) {
                String message = "Nie można dodawać więcej niż jednego połączenia " +
                        "z tym samym id producenta i apteki";
                throw new DataFormatException(message);
            }
        }
    }

    public int getSize() {
        return connections.size();
    }

    public int getExpectedSize() {
        return producers.getNumberOfProducers() * pharmacies.getNumberOfPharmacies();
    }

    public boolean contain(long producerId, long pharmacyId) {
        for (Connection connection : connections) {
            if (connection.getProducer().getId() == producerId && connection.getPharmacy().getId() == pharmacyId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull Iterator<Connection> iterator() {
        return connections.iterator();
    }

    public int getNumberOfConnections() {
        return connections.size();
    }

    public Connection getConnectionByIndex(int index) {
        return connections.get(index);
    }
}
