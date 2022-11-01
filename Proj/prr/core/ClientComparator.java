package prr.core;

import java.util.*;

public class ClientComparator implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        return c1.getKey().compareToIgnoreCase(c2.getKey());
    }
}
