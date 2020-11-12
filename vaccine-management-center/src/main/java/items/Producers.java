package items;

import java.util.ArrayList;
import java.util.List;

public class Producers {
    private final List<Producer> producers;

    public Producers() {
        producers = new ArrayList<>();
    }

    public void addANewProducer(Producer producer) {
        if (producers.contains(producer)) {
            throw new UnsupportedOperationException("Nie można dodawać producentów o tym samym id!");
        }

        if(producer.getDailyProduction() < 0 ){
            throw new IllegalArgumentException("Dziena produkcja nie może być ujemna!");
        }
        producers.add(producer);
    }

    public boolean contain(long id) {
        for (Producer p : producers) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfProducers(){
        return producers.size();
    }

    public Producer getProducer(long id){
        for(Producer p : producers){
            if(p.getId() == id)
                return p;
        }
        return null;
    }
}
