package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @JsonProperty("CategoryID")
    public Integer categoryID;
    @JsonProperty("CategoryName")
    public String categoryName;
    public Extra extra;
}
