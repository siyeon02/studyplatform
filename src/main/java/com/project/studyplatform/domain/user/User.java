package com.project.studyplatform.domain.user;


import com.project.studyplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String nickname;

    @Column(length = 60)
    private String password;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
