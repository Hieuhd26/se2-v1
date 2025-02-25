package HIEU.demo.controller;

import HIEU.demo.model.Student;
import HIEU.demo.model.StudentExcel;
import HIEU.demo.repository.StudentRepository;
import HIEU.demo.service.StudentExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private StudentExcelService studentExcelService;

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload-student";
    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        List<Student> students = studentExcelService.readExcelFile(file);
        model.addAttribute("students",students);
        return "student-list";
    }
}
