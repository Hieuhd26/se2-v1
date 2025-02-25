package HIEU.demo.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String name;
    private String year;
    private String course;
    private List<StudentResponse> students;
    private List<ImageResponse> images;

}
