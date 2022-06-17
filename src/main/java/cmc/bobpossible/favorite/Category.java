package cmc.bobpossible.favorite;

import cmc.bobpossible.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Where(clause = "status='ACTIVE'")
@Getter
@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @Column(length = 40)
    private String name;

    public static Category create(String name) {
        Category category = new Category();
        category.init(name);
        return category;
    }

    private void init(String name) {
        this.name = name;
    }
}
