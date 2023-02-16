package com.personal.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ServiceDeleteBoardResponseDto {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime deleteDate;

    private String nickname;

    private Long memberId;

    @Builder
    public ServiceDeleteBoardResponseDto(Long boardId, String title, String content, LocalDateTime createDate, LocalDateTime deleteDate, String nickname, Long memberId) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.nickname = nickname;
        this.memberId = memberId;
    }
}
