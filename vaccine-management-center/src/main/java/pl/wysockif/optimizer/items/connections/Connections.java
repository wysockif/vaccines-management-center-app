package pl.wysockif.optimizer.items.connections;

import pl.wysockif.optimizer.items.Items;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.pharmacies.Pharmacy;
import pl.wysockif.optimizer.items.producers.Producer;
import pl.wysockif.optimizer.items.producers.Producers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class Connections implements Items {
    private final int converter;
    private final Producers producers;
    private final Pharmacies pharmacies;
    private final Map<Integer, Connection> connectionByIndex;
    private final Map<ConnectionIdKey, Connection> connectionByKey;
    private int counter;

    public Connections(Producers producers, Pharmacies pharmacies) {
        this.producers = producers;
        this.pharmacies = pharmacies;
        connectionByIndex = new HashMap<>();
        connectionByKey = new HashMap<>();
        converter = 100;
    }

    @Override
    public void addNewElement(Object[] attributes) {
        int producerId = (int) attributes[0];
        int pharmacyId = (int) attributes[1];
        int maxNumberOVaccines = (int) attributes[2];
        int price = (int) attributes[3];
        Producer producer = producers.getProducerById(producerId);
        Pharmacy pharmacy = pharmacies.getPharmacyById(pharmacyId);

        Connection connection = new Connection(producer, pharmacy, maxNumberOVaccines, price);
        connectionByIndex.put(counter, connection);
        ConnectionIdKey connectionIdKey = new ConnectionIdKey(producerId, pharmacyId);
        connectionByKey.put(connectionIdKey, connection);
        counter++;
    }

    @Override
    public Object[] parseAttributes(String[] attributes) throws DataFormatException {
        Object[] convertedAttributes = new Object[attributes.length];

        try {
            convertedAttributes[0] = Integer.parseInt(attributes[0]);
            convertedAttributes[1] = Integer.parseInt(attributes[1]);
            convertedAttributes[2] = Integer.parseInt(attributes[2]);
            convertedAttributes[3] = Double.parseDouble(attributes[3]);
        } catch (NumberFormatException e) {
            String regex = Pattern.quote("For input string: ");
            String info = e.getMessage().replaceAll(regex, "");
            String message = "Nieudana konwersja danej: " + info;
            throw new DataFormatException(message);
        }
        return convertedAttributes;
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
        int producerId = (int) attributes[0];
        int pharmacyId = (int) attributes[1];
        int maxNumberOVaccines = (int) attributes[2];
        double price = (double) attributes[3];

        checkIfExist(producerId, pharmacyId);
        checkIfHasAlreadyContain(producerId, pharmacyId);
        validateNumber(maxNumberOVaccines);
        validateNumber(price);
        checkIfGreaterThanLimit(price);
        attributes[3] = (int) Math.round(converter * price);
    }

    @Override
    public Object[] convertAttributes(String[] attributes) throws DataFormatException {
        if (attributes.length != 4) {
            throw new DataFormatException("Niepoprawny format danych");
        }
        checkCorrectnessOfPrice(attributes[3]);
        return parseAttributes(attributes);
    }

    private void checkIfGreaterThanLimit(double price) throws DataFormatException {
        int limit = 999_999_999;

        if (price >= limit) {
            throw new DataFormatException("Cena nie może być większa od: " + limit);
        }
    }

    private void checkIfExist(int producerId, int pharmacyId) throws DataFormatException {
        if (!producers.alreadyContains(producerId)) {
            String message = "Nie istnieje producent o podanym id: " + producerId;
            throw new DataFormatException(message);
        }
        if (!pharmacies.alreadyContains(pharmacyId)) {
            String message = "Nie istnieje apteka o podanym id: " + pharmacyId;
            throw new DataFormatException(message);
        }
    }

    private void validateNumber(Number number) throws DataFormatException {
        if ((number instanceof Double && (double) number < 0) || (number instanceof Integer && (int) number < 0)) {
            String message = "Niepoprawny format danych. Ujemna wartość";
            throw new DataFormatException(message);
        }
    }

    private void checkIfHasAlreadyContain(int producerId, int pharmacyId) throws DataFormatException {
        ConnectionIdKey connectionIdKey = new ConnectionIdKey(producerId, pharmacyId);

        if (connectionByKey.containsKey(connectionIdKey)) {
            String message = "Nie można dodawać więcej niż jednego połączenia " +
                    "z tym samym id producenta i apteki";
            throw new DataFormatException(message);
        }
    }

    private void checkCorrectnessOfPrice(String price) throws DataFormatException {
        if (price.contains(".")) {
            String[] elements = price.split("\\.");
            if (elements[1].length() > 2) {
                String message = "Cena nie może mieć więcej niż 2 cyfry po kropce";
                throw new DataFormatException(message);
            }
        }
    }

    public int getSize() {
        return connectionByIndex.size();
    }

    public int getExpectedSize() {
        return producers.getNumberOfProducers() * pharmacies.getNumberOfPharmacies();
    }



    public Collection<Connection> getConnectionsCollection() {
        return connectionByIndex.values();
    }

    public boolean contain(int producerId, int pharmacyId) {
        ConnectionIdKey connectionIdKey = new ConnectionIdKey(producerId, pharmacyId);
        return connectionByKey.containsKey(connectionIdKey);
    }

    public static class ConnectionIdKey {
        private final int producerId;
        private final int pharmacyId;

        public ConnectionIdKey(int producerId, int pharmacyId) {
            this.producerId = producerId;
            this.pharmacyId = pharmacyId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConnectionIdKey connectionIdKey = (ConnectionIdKey) o;
            return producerId == connectionIdKey.producerId &&
                    pharmacyId == connectionIdKey.pharmacyId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(producerId, pharmacyId);
        }
    }
}
