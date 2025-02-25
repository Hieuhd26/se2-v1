package HIEU.demo.repository;

import HIEU.demo.model.Image;
import HIEU.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProject(Project project);
}
