package HIEU.demo.service;

import HIEU.demo.model.Student;
import HIEU.demo.repository.ProjectRepository;
import HIEU.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    @Transactional
    public void deleteById(String id) {
        studentRepository.deleteById(id);
    }

    public Page<Student> findPaginated(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Student> searchStudents(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByIdContaining(keyword, pageable);
    }

}
