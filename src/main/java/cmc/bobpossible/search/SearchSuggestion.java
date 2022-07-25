package cmc.bobpossible.search;

import lombok.Data;

@Data
public class SearchSuggestion {
    private String suggest;

    public SearchSuggestion(String suggest) {
        this.suggest = suggest;
    }
}
