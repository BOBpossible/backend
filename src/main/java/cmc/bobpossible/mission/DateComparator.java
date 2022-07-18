package cmc.bobpossible.mission;

import java.util.Comparator;

public class DateComparator  implements Comparator<Mission> {

    @Override
    public int compare(Mission o1, Mission o2) {
        if(o1.getUpdateAt().isBefore(o2.getUpdateAt())) {
            return 1;
        } else if (o1.getUpdateAt().isAfter(o2.getUpdateAt())) {
            return -1;
        }
        return 0;
    }
}
