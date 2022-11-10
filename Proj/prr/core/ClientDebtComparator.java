package prr.core;

import java.util.Comparator;

public class ClientDebtComparator implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        if(c1.getDebts() == c2.getDebts())
            return c1.getKey().compareToIgnoreCase(c2.getKey());
        return (int) (c2.getDebts() - c1.getDebts());
    }
}
