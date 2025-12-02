package org.bf.userservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name="p_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String email;

    private String password;

    private String nickname;

    private String username;

    @Builder
    public User(String email, String password, String nickname, String username) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.username = username;
    }
}
