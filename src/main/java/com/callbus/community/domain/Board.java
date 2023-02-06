package com.callbus.community.domain;

import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Integer hit = 0;


    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Heart> heart;

    @OneToMany(mappedBy = "board")
    private List<Reply> reply;

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Builder
    public Board(Long boardId, String title, String content, Integer hit, Timestamp createDate, Timestamp updateDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public void addMember(Member member){
        this.member = member;
        member.addBoard(this);
    }


    public BoardSaveRespDto toSaveDto(){
        return BoardSaveRespDto.builder()
                .title(title)
                .content(content)
                .nickname(this.getMember().getNickname())
                .memberId(this.getMember().getMemberId())
                .build();

    }
}
