package com.example.notion.domain.user.entitiy;

import com.example.notion.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String profileUrl;

    @Enumerated(EnumType.STRING) //Enum 값을 문자열로 저장하는게 안전한다.
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String name, String profileUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = Role.USER;
    }
}
