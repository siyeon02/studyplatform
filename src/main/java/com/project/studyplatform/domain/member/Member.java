package com.project.studyplatform.domain.member;


import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.groupmember.GroupMember;
import com.project.studyplatform.domain.studyroom.StudyRoomUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(columnDefinition = "VARCHAR(20)")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(60)")
    private String password;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50)
    private Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoomUser> studyRoomUsers = new ArrayList<>();

    @Builder
    public Member(String name, String nickname, String password, String email, Status status) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.status = status;

    }

    public void editProfile(String name, String email, String password, String nickname, Status status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
    }
}
