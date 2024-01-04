package ru.myapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.myapp.model.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Override
    @EntityGraph(attributePaths = "users")
    Optional<Group> findById(@NonNull Integer groupId);
}
