package com.callbus.community.domain;

import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.domain.util.BaseTimeEntity;
import com.callbus.community.domain.util.STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Integer hit = 0;


//    @CreatedDate
//    private LocalDateTime createDate;
//
//    @LastModifiedDate
//    private LocalDateTime updateDate;

    private LocalDateTime deleteDate;

    private STATUS status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Heart> heart;

    @OneToMany(mappedBy = "board")
    private List<Reply> reply;

    @Builder
    public Board(Long boardId,String title, String content){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    @Builder
    public Board(Long boardId, String title, String content, Integer hit) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.deleteDate = null;
        this.status = STATUS.Y;
    }

    public void addMember(Member member){
        this.member = member;
        member.addBoard(this);
    }


    public BoardSaveRespDto toSaveDto(){
        return BoardSaveRespDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .nickname(this.getMember().getNickname())
                .memberId(this.getMember().getMemberId())
                .createDate(createDate)
                .updateDate(updateDate)
                .build();

    }

    public Board update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }
}
