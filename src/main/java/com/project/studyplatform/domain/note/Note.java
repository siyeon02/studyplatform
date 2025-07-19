package com.project.studyplatform.domain.note;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notes")
@Getter
@NoArgsConstructor
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @Builder
    public Note(Long id, String title, String content, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
