package vn.techmaster.mp3.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileReturn {
    private List<String> files;
    private String totalPage;
}

