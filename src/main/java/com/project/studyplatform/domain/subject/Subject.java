package com.project.studyplatform.domain.subject;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subjects")
@Getter
@NoArgsConstructor
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @Column
    private String name;

    @Builder
    public Subject(Long id, Member member, String name) {
        this.id = id;
        this.member = member;
        this.name = name;
    }

    public void modify(String subjectName) {
        this.name = subjectName;
    }
}
