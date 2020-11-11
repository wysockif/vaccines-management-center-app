package items;

import java.util.ArrayList;
import java.util.List;

public class Pharmacies {
    private final List<Pharmacy> pharmacies;

    public Pharmacies() {
        pharmacies = new ArrayList<>();
    }

    public void addANewPharmacy(Pharmacy pharmacy) {
        if (pharmacies.contains(pharmacy)) {
            throw new UnsupportedOperationException("Nie można dodawać aptek o tym samym id!");
        }

        if (pharmacy.getDailyRequirement() < 0) {
            throw new IllegalArgumentException("Dzienne zapotrzebowanie nie może być ujemne!");
        }
        pharmacies.add(pharmacy);
    }

    public boolean contain(long pharmacyId) {
        for (Pharmacy p : pharmacies) {
            if (p.getId() == pharmacyId) {
                return true;
            }
        }
        return false;
    }

    public Pharmacy getPharmacy(long id) {
        for (Pharmacy p : pharmacies) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public int getNumberOfPharmacies() {
        return pharmacies.size();
    }
}
