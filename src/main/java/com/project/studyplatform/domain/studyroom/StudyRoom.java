package com.project.studyplatform.domain.studyroom;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.member.Member;
import com.project.studyplatform.domain.member.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studyrooms")
@Getter
@NoArgsConstructor
public class StudyRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member manager;

    @Column
    private String zoomLink;

    @Column
    private String password;

    @Column(length =255)
    private String description;

    @Column
    private Integer maxParticipants;

    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoomUser> participants = new ArrayList<>();

    @Builder
    public StudyRoom(String name, Member manager, String zoomLink, String password, String description, Integer maxParticipants) {
        this.name = name;
        this.manager = manager;
        this.zoomLink = zoomLink;
        this.password = password;
        this.description = description;
        this.maxParticipants = maxParticipants;

    }

}
