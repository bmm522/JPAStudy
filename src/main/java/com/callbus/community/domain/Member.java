package com.callbus.community.domain;

import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, length = 50)
    private String accountId;

    @Enumerated(EnumType.STRING)
    private STATUS quit;


    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replies = new ArrayList<>();




    @Builder
    public Member(long id, String nickname, AccountType accountType, STATUS status){
        this.memberId = id;
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountId = accountType.getAccountType() + " " + id;
        this.quit = status;
    }


}