package ru.myapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.myapp.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @EntityGraph(attributePaths = "groups")
    Optional<User> findById(@NonNull Integer userId);
}
