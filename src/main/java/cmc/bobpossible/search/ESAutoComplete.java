package cmc.bobpossible.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Document(indexName = "auto_complete")
public class ESAutoComplete implements Persistable<Integer> {

    @Id
    @Field(type = FieldType.Long)
    Integer id;

    @NotNull
    @Field(type = FieldType.Text, name = "search_string")
    String searchString;

    @NotNull
    @Field(type = FieldType.Long, name = "subId")
    Long subId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
