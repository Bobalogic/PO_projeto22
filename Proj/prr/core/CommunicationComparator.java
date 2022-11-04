package prr.core;

import java.util.Comparator;

public class CommunicationComparator implements Comparator<Communication> {
    public int compare(Communication c1, Communication c2) {
        return c1.getId() - c2.getId();
    }
}
