package com.project.studyplatform.domain.subject;

import com.project.studyplatform.domain.BaseEntity;
import com.project.studyplatform.domain.user.User;
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
    private User user;

    @Column
    private String name;

    @Builder
    public Subject(Long id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }
}
