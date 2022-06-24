package cmc.bobpossible.mission;

import java.util.Comparator;

public class RecommendPercentageComparator implements Comparator<Recommend> {
    @Override
    public int compare(Recommend o1, Recommend o2) {
        if (o1.getPercentage() > o2.getPercentage()) {
            return 1;
        } else if (o1.getPercentage() < o2.getPercentage()) {
            return -1;
        }
        return 0;
    }
}
