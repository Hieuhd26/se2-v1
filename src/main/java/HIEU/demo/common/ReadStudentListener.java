package HIEU.demo.common;

import HIEU.demo.model.Student;
import HIEU.demo.model.StudentExcel;
import HIEU.demo.repository.StudentRepository;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReadStudentListener extends AnalysisEventListener<StudentExcel> {

    private final List<Student> studentList = new ArrayList<>();
    private final StudentRepository studentRepository;
    private static final int START_ROW = 4;
    private int rowIndex = 0;
    private boolean stopReading = false;

    @Override
    public void invoke(StudentExcel studentExcel, AnalysisContext context) {
        if (stopReading) return;
        if (rowIndex++ < START_ROW) {
            return;
        }
        if (studentExcel.getId() == null || studentExcel.getId().trim().isEmpty()) {
            stopReading = true;
            return;
        }
        Student student =Student.builder()
                .id(studentExcel.getId())
                .className(studentExcel.getClassName())
                .firstName(studentExcel.getFirstName())
                .lastName(studentExcel.getLastName())
                .build();
        studentList.add(student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (!studentList.isEmpty()) {
            studentRepository.saveAll(studentList);
            log.info("Saved {} students into database", studentList.size());
        } else {
            log.info("No data");
        }
        studentList.clear();
        rowIndex = 0;
        stopReading = false;
    }
    public List<Student> getStudentList() {
        return studentList;
    }
}
