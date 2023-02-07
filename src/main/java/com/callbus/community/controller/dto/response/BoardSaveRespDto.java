package com.callbus.community.controller.dto.response;

import com.callbus.community.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
public class BoardSaveRespDto {

    private Long boardId;
    private String title;
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String nickname;
    private Long memberId;

    @Builder
    public BoardSaveRespDto(Long boardId,String title, String content, String nickname, Long memberId, LocalDateTime createDate, LocalDateTime updateDate){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.memberId = memberId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
