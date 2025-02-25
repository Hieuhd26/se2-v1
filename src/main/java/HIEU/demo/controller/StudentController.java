package HIEU.demo.controller;

import HIEU.demo.model.Student;
import HIEU.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @GetMapping()
    public String listStudents(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String keyword) {
        if (page < 0) page = 0;
        if (size < 1) size = 10;
        Page<Student> studentPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            studentPage = studentService.searchStudents(keyword, page, size);
        } else {
            studentPage = studentService.findPaginated(page, size);
        }
        int totalPages = Math.max(1, studentPage.getTotalPages());
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        return "student-list";
    }


    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }

}
