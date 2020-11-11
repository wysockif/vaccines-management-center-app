package items;

import java.util.ArrayList;
import java.util.List;

public class Connections {
    private final List<Connection> connections;
    private final Producers producers;
    private final Pharmacies pharmacies;

    public Connections(Producers producers, Pharmacies pharmacies) {
        this.producers = producers;
        this.pharmacies = pharmacies;
        connections = new ArrayList<>();
    }

    public void addANewConnection(Connection connection) {
        if (connections.contains(connection)) {
            throw new UnsupportedOperationException("Nie można dodawać więcej niż jednego połączenia " +
                    "z tym samym id producenta i apteki!");
        }

        if (!producers.contain(connection.getProducerId())) {
            throw new IllegalArgumentException("Producent o podanym id nie istnieje!");
        }

        if (!pharmacies.contain(connection.getPharmacyId())) {
            throw new IllegalArgumentException("Apteka o podanym id nie istnieje!");
        }
        connections.add(connection);
    }

}
