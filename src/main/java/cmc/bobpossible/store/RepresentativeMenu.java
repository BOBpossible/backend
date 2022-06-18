package cmc.bobpossible.store;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class RepresentativeMenu {
    @Column(name = "menu_name")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String representativeMenuImage1;

    @Column(columnDefinition = "TEXT")
    private String representativeMenuImage2;

    @Column(columnDefinition = "TEXT")
    private String representativeMenuImage3;

    public RepresentativeMenu(String representativeMenuName, List<String> representativeMenuImages) {
        this.name = representativeMenuName;
        if (representativeMenuImages.size() > 0) {
            this.representativeMenuImage1 = representativeMenuImages.get(0);
        }
        if (representativeMenuImages.size() > 1) {
            this.representativeMenuImage1 = representativeMenuImages.get(1);
        }
        if (representativeMenuImages.size() > 2) {
            this.representativeMenuImage1 = representativeMenuImages.get(2);
        }
    }
}
