package cmc.bobpossible.favorite.dto;

import cmc.bobpossible.favorite.Favorite;
import lombok.Data;

@Data
public class GetFavoritesRes {

    private Long id;
    private String name;

    public GetFavoritesRes(Favorite favorite) {
        this.id = favorite.getId();
        this.name = favorite.getName();
    }
}
