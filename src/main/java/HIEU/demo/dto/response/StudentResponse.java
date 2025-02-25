package HIEU.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StudentResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String className;
}
