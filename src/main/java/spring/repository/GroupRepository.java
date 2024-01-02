package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
