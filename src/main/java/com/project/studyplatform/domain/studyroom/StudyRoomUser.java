package com.project.studyplatform.domain.studyroom;

import com.project.studyplatform.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "studyroom_user")
public class StudyRoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyroom_id")
    private StudyRoom studyRoom;

    @Builder
    public StudyRoomUser(Member member, StudyRoom studyRoom){
        this.member = member;
        this.studyRoom = studyRoom;
    }

}
