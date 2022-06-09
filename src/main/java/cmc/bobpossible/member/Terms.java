package cmc.bobpossible.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Terms {

    private boolean termsOfService;
    private boolean privacyPolicy;
    private boolean locationInfo;
    private boolean marketing;
}
