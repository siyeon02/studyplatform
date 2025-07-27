package com.project.studyplatform.domain.group;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.groupmember.GroupMember;
import com.project.studyplatform.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study_groups")
@Getter
@NoArgsConstructor
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member manager;

    @Column
    private Integer maxParticipants;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Builder
    public Group(Long id, String name, Member manager, Integer maxParticipants) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.maxParticipants = maxParticipants;
    }

    public void addGroupMember(Member member) {
        GroupMember gm = new GroupMember(this, member);
        this.groupMembers.add(gm);
        member.getGroupMembers().add(gm);
    }

    public void modify(String groupName, Integer maxParticipants) {
        this.name = groupName;
        this.maxParticipants = maxParticipants;
    }
}
