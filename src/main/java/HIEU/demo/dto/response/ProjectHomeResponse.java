package HIEU.demo.dto.response;

import HIEU.demo.model.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProjectHomeResponse {
    private Long id;
    private String name;
    private String year;
    private String course;
    private String firstImageUrl;

    public static ProjectHomeResponse fromEntity(Project project) {
        String firstImageUrl = project.getImages().isEmpty() ? "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/07/anh-thien-nhien-dep-3d-22.jpg" : project.getImages().get(0).getUrl();
        return ProjectHomeResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .year(project.getYear())
                .course(project.getCourse())
                .firstImageUrl(firstImageUrl).build();
    }
}
