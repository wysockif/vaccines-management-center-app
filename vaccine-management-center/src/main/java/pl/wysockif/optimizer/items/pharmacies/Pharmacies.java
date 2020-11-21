package pl.wysockif.optimizer.items.pharmacies;

import org.jetbrains.annotations.NotNull;
import pl.wysockif.optimizer.items.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Pharmacies implements Items, Iterable<Pharmacy> {
    private final List<Pharmacy> pharmacies;

    public Pharmacies() {
        pharmacies = new ArrayList<>();
    }


    public boolean alreadyContains(long pharmacyId) {
        for (Pharmacy p : pharmacies) {
            if (p.getId() == pharmacyId) {
                return true;
            }
        }
        return false;
    }

//    public Pharmacy getPharmacy(long id) {
//        for (Pharmacy p : pharmacies) {
//            if (p.getId() == id)
//                return p;
//        }
//        return null;
//    }

    public int getNumberOfPharmacies() {
        return pharmacies.size();
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
        int dailyRequirement = (int) attributes[2];
        long id = (long) attributes[0];
        if (dailyRequirement < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca dzienne zapotrzebowanie";
            throw new DataFormatException(message);
        }
        if (alreadyContains(id)) {
            throw new DataFormatException("Nie można dodawać aptek o tym samym id");
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        long id = (long) attributes[0];
        String name = (String) attributes[1];
        int dailyRequirement = (int) attributes[2];
        Pharmacy pharmacy = new Pharmacy(id, name, dailyRequirement);
        pharmacies.add(pharmacy);
        System.out.println(pharmacy);
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return pharmacies.iterator();
    }
}
