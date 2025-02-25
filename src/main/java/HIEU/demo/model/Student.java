package HIEU.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    private String className;
    @ManyToMany
    @JoinTable(
            name = "student_project",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projectList;
}
