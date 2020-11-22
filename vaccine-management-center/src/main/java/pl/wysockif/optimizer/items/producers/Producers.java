package pl.wysockif.optimizer.items.producers;

import org.jetbrains.annotations.NotNull;
import pl.wysockif.optimizer.items.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Producers implements Items, Iterable<Producer> {
    private final List<Producer> producers;

    public Producers() {
        producers = new ArrayList<>();
    }

    public boolean alreadyContains(long id) {
        for (Producer p : producers) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfProducers() {
        return producers.size();
    }


    @Override
    public Object[] convertAttributes(String[] attributes) throws DataFormatException {
        if (attributes.length != 3) {
            throw new DataFormatException("Niepoprawny format danych");
        }
        return parseAttributes(attributes);
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
        int dailyProduction = (int) attributes[2];
        long id = (long) attributes[0];
        if (dailyProduction < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca dzienną produkcję";
            throw new DataFormatException(message);
        }
        if (alreadyContains(id)) {
            String message = "Nie można dodawać producentów o tym samym id";
            throw new DataFormatException(message);
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        long id = (long) attributes[0];
        String name = (String) attributes[1];
        int dailyProduction = (int) attributes[2];
        Producer producer = new Producer(id, name, dailyProduction);
        producers.add(producer);
    }

    @NotNull
    @Override
    public Iterator<Producer> iterator() {
        return producers.iterator();
    }

    public Producer getProducerByIndex(int index) {
        return producers.get(index);
    }

    public int getProducerIndexById(long producerId) {
        return producers.indexOf(new Producer(producerId, "",0));
    }

    public Producer getProducerById(long producerId) {
        return producers.get(getProducerIndexById(producerId));
    }
}
