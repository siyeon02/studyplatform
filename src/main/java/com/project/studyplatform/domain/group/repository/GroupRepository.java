package com.project.studyplatform.domain.group.repository;

import com.project.studyplatform.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
