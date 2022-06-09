package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Terms {

    private Boolean termsOfService;
    private Boolean privacyPolicy;
    private Boolean locationInfo;
    private Boolean marketing;
}
