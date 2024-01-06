package ru.myapp.repository;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.myapp.model.Group;
import ru.myapp.model.PaidGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Override
    @EntityGraph(attributePaths = "users")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<Group> findById(@NonNull Integer groupId);

    @Query("SELECT pg FROM PaidGroup pg")
    List<PaidGroup> findAllPaidGroups();
}