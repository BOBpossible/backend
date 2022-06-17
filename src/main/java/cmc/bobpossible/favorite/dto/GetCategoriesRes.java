package cmc.bobpossible.favorite.dto;

import cmc.bobpossible.favorite.Category;
import lombok.Data;

@Data
public class GetCategoriesRes {

    private Long id;
    private String name;

    public GetCategoriesRes(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
