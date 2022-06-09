package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Terms {

    private Boolean termsOfService;
    private Boolean privacyPolicy;
    private Boolean locationInfo;
    private Boolean marketing;

    public Terms() {
    }

    public Terms(Boolean termsOfService, Boolean privacyPolicy, Boolean locationInfo, Boolean marketing) {
        this.termsOfService = termsOfService;
        this.privacyPolicy = privacyPolicy;
        this.locationInfo = locationInfo;
        this.marketing = marketing;
    }
}
