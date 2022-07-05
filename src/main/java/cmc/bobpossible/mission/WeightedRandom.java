package cmc.bobpossible.mission;

import cmc.bobpossible.store.Store;

import java.util.ArrayList;
import java.util.List;

public class WeightedRandom {


    public static Recommend getRandom(List<Recommend> recommends) {

        final double pivot = Math.random();

        double acc = 0;
        for (Recommend recommend : recommends) {
            acc += recommend.getPercentage();

            if (pivot <= acc) {
                return recommend;
            }
        }
        return null;
    }
}
