package HIEU.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudentExcel {
    @ExcelProperty(index = 1)
    private String id;
    @ExcelProperty(index = 2)
    private String lastName;
    @ExcelProperty(index = 3)
    private String firstName;
    @ExcelProperty(index = 4)
    private String className;
}
