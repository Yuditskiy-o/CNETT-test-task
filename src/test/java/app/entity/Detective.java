package app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Detective {

    public Integer MainId;
    public String firstName;
    public String lastName;
    public boolean violinPlayer;
    public ArrayList<Category> categories;

}
