package prr.core;

import java.util.*;

public class TerminalComparator implements Comparator<Terminal> {
    public int compare(Terminal t1, Terminal t2) {
        return t1.getId().compareToIgnoreCase(t2.getId());
    }
}
