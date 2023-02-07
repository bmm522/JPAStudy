package com.callbus.community.domain;

import com.callbus.community.common.DateFormatter;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.controller.dto.response.BoardUpdateRespDto;
import com.callbus.community.domain.util.BaseTimeEntity;
import com.callbus.community.domain.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
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


    private LocalDateTime deleteDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Heart> heart;

    @OneToMany(mappedBy = "board")
    private List<Reply> reply;

//    @Builder
//    public Board(Long boardId, String title, String content, Integer hit, LocalDateTime createDate,LocalDateTime updateDate,LocalDateTime deleteDate, Status status) {
//        this.boardId = boardId;
//        this.title = title;
//        this.content = content;
//        this.hit = hit;
//        super.createDate = createDate;
//        super.updateDate = updateDate;
//        this.deleteDate = deleteDate;
//        this.status = status;
//    }
    @Builder
    public Board(Long boardId,String title, String content, Status status){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    @Builder
    public Board(Long boardId, String title, String content, Integer hit,Status status) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.deleteDate = null;
        this.status = status;
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
                .build();

    }

    public BoardUpdateRespDto toUpdateDto(){
        return BoardUpdateRespDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .nickname(this.getMember().getNickname())
                .memberId(this.getMember().getMemberId())
                .createDate(createDate)
                .updateDate(updateDate)
                .build();

    }

    public Board update(String title, String content, LocalDateTime updateDate){
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
        return this;
    }
}
