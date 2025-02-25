package HIEU.demo.service;


import HIEU.demo.common.ReadStudentListener;
import HIEU.demo.model.Student;
import HIEU.demo.model.StudentExcel;
import HIEU.demo.repository.StudentRepository;
import com.alibaba.excel.EasyExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StudentExcelService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ReadStudentListener readStudentListener;

    public List<Student> readExcelFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, StudentExcel.class, readStudentListener).sheet().doRead();
            return studentRepository.findAll();

    }

}
