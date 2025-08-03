package com.project.studyplatform.domain.group.repository;

import com.project.studyplatform.domain.group.Group;
import com.project.studyplatform.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("""
            SELECT DISTINCT g FROM GroupMember gm
            JOIN gm.group g
            JOIN FETCH g.manager
            LEFT JOIN FETCH g.groupMembers gm2
            LEFT JOIN FETCH gm2.member
            WHERE gm.member = :member
            """)
    List<Group> findAllGroupsByMember(@Param("member") Member member);

    List<Group> findByNameContaining(String name);
}
