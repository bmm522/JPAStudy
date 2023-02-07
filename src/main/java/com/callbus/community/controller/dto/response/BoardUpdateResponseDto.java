package com.callbus.community.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BoardUpdateResponseDto {

    private Long boardId;
    private String title;
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
    private String nickname;
    private Long memberId;

    @Builder
    public BoardUpdateResponseDto(Long boardId, String title, String content, String nickname, Long memberId, LocalDateTime createDate, LocalDateTime updateDate){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.memberId = memberId;
        this.createDate = createDate;
        this.updateDate = updateDate;

    }
}
