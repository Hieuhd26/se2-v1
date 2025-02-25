package HIEU.demo.controller;

import HIEU.demo.dto.response.ProjectHomeResponse;
import HIEU.demo.dto.response.ProjectResponse;
import HIEU.demo.model.Project;
import HIEU.demo.service.ProjectService;
import HIEU.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public String getProjectById(@PathVariable Long id, Model model) {
        ProjectResponse project = projectService.getProjectResponseById(id);
        model.addAttribute("project", project);
        return "project-detail";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("students", studentService.findAll());
        return "project-form";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(@RequestParam("name") String name,
                                @RequestParam("year") String year,
                                @RequestParam("course") String course,
                                @RequestParam("studentIds") List<String> studentIds,
                                @RequestParam("images") List<MultipartFile> images) throws IOException {
        ProjectResponse project = projectService.createProject(name, year, course, studentIds, images);
        return "redirect:/projects";

    }

    @GetMapping()
    public String getAllProject(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            @RequestParam(value = "sortBy", defaultValue = "year") String sortBy,
            @RequestParam(value = "direction", defaultValue = "desc") String direction,
            Model model
    ) {
        Page<ProjectHomeResponse> projects = projectService.getAllProjects(keyword, page, size, sortBy, direction);
        model.addAttribute("projects", projects.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", projects.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("keyword", keyword);
        return "projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
