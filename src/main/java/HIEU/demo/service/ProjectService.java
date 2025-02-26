package HIEU.demo.service;

import HIEU.demo.dto.response.ImageResponse;
import HIEU.demo.dto.response.ProjectHomeResponse;
import HIEU.demo.dto.response.ProjectResponse;
import HIEU.demo.dto.response.StudentResponse;
import HIEU.demo.exception.AppException;
import HIEU.demo.model.Image;
import HIEU.demo.model.Project;
import HIEU.demo.model.Student;
import HIEU.demo.repository.ImageRepository;
import HIEU.demo.repository.ProjectRepository;
import HIEU.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;

    public ProjectResponse createProject(String name, String year, String course, List<String> studentIds, List<MultipartFile> images) throws IOException {
        Project project = Project.builder()
                .name(name)
                .year(year)
                .course(course)
                .build();

        List<Student> students = studentRepository.findAllById(studentIds);
        for (Student student : students) {
            student.getProjectList().add(project);
        }
        project.setStudents(students);
        project = projectRepository.save(project);
        List<Image> imageList = imageService.saveImages(images, project);

        project.setImages(imageList);
        return convertToProjectResponse(project);
    }


    public Page<ProjectHomeResponse> getAllProjects(String keyword, int page, int size, String sort, String direction) {
        Sort orders = Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort);
        //tao pageable
        Pageable pageable = PageRequest.of(page, size, orders);

        Page<Project> projectPage;
        if (keyword != null && !keyword.isEmpty()) {
            projectPage = projectRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            projectPage = projectRepository.findAll(pageable);
        }
        return projectPage.map(ProjectHomeResponse::fromEntity);
    }

    public ProjectResponse getProjectResponseById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException("Project Not found"));
        return convertToProjectResponse(project);

    }

    private ProjectResponse convertToProjectResponse(Project project) {
        List<StudentResponse> studentDTOs = project.getStudents().stream()
                .map(student -> StudentResponse.builder().className(student.getClassName()).firstName(student.getFirstName()).lastName(student.getLastName()).id(student.getId()).build())
                .collect(Collectors.toList());

        List<ImageResponse> imageDTOs = project.getImages().stream()
                .map(image -> ImageResponse.builder().id(image.getId()).url(image.getUrl()).build())
                .collect(Collectors.toList());

        return ProjectResponse.builder()
                .id(project.getId())
                .course(project.getCourse())
                .year(project.getYear())
                .name(project.getName())
                .images(imageDTOs)
                .students(studentDTOs)
                .build();
    }


    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException("Project not found"));

        // Xóa ảnh trước khi xóa Project
        imageService.deleteImagesByProject(project);

        // Xóa Project nhưng giữ nguyên Student
        for (Student student : project.getStudents()) {
            student.getProjectList().remove(project);
        }
        projectRepository.delete(project);
    }
}
