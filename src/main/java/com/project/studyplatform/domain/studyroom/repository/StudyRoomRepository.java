package com.project.studyplatform.domain.studyroom.repository;

import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.studyroom.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    @Query("SELECT sr FROM StudyRoomUser sru JOIN sru.studyRoom sr WHERE sru.member = :member")
    List<StudyRoom> findStudyRoomsByMember(@Param("member") Member member);

}
