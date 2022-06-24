package cmc.bobpossible.mission;

import cmc.bobpossible.member_category.MemberCategory;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.utils.DistanceCalculator;
import lombok.Data;

import java.util.List;

@Data
public class Recommend {

    private  Store store;

    private  double percentage;

    private double distance;

    private double distanceRes;

    public static double distanceSum =0;

    public static double distanceResSum =0;

    public static double preferenceResSum=0;


    public Recommend(Store s, double x, double y) {
        this.store = s;
        this.percentage = 0;
        this.distance = DistanceCalculator.distance(x, y , s.getAddress().getX(), s.getAddress().getY());
        Recommend.distanceSum += distance;
    }

    public void distancePercentage() {
        double res = distanceSum / distance;

        //제곱
        distanceRes = Math.pow(res, 2);
        Recommend.distanceResSum += distanceRes;
    }

    public void preference(List<MemberCategory> memberCategories, int size) {
        double res = 0;
        for (MemberCategory memberCategory : memberCategories) {
            if (memberCategory.getCategory().getName().equals(this.store.getCategory().getName())) {
                res = distanceResSum/ size;
                distanceRes = res;
            }
        }
        if(res ==0){
            res = distanceResSum/ size * 0.5;
            distanceRes = res;
        }
        Recommend.preferenceResSum += res;
    }

    public void calculatePercentage() {
        this.percentage = distanceRes / (distanceResSum + preferenceResSum);
    }
}
