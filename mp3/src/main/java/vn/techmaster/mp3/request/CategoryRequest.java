package vn.techmaster.mp3.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.mp3.model.SongCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    String id;
    String name;
    String avatar;
    List<SongCategory> songCategories = new ArrayList<>();
}
