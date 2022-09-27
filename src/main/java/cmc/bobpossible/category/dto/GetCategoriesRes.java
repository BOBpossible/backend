package cmc.bobpossible.category.dto;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.member_category.MemberCategory;
import lombok.Data;

@Data
public class GetCategoriesRes {

    private Long id;
    private String name;

    public GetCategoriesRes(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }


    public GetCategoriesRes(MemberCategory memberCategory) {
        this.id = memberCategory.getCategory().getId();
        this.name = memberCategory.getCategory().getName();
    }
}
