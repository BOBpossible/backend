package cmc.bobpossible.category.dto;

import cmc.bobpossible.category.Category;
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
