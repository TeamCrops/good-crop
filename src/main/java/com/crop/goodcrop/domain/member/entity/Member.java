package com.crop.goodcrop.domain.member.entity;

import com.crop.goodcrop.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String nickname;
    private LocalDate birth;

    public void modify(String password, String nickname, LocalDate birth){
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
    }

}
