package HIEU.demo.repository;

import HIEU.demo.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Page<Project> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
