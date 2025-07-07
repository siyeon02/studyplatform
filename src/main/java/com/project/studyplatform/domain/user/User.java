package com.project.studyplatform.domain.user;


import com.project.studyplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

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
    private Status status;


    @Builder
    public User(String name, String nickname, String password, String email, Status status) {
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
