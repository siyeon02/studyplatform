package com.project.studyplatform.domain.group.repository;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT gm.group FROM GroupMember gm WHERE gm.member = :member")
    List<Group> findGroupsByMember(@Param("member") Member member);
}
