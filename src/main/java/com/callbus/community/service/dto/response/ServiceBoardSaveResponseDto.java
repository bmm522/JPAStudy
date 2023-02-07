package com.callbus.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ServiceBoardSaveResponseDto {

    private Long boardId;
    private String title;
    private String content;

    private LocalDateTime createDate;
    private String nickname;
    private Long memberId;

    @Builder
    public ServiceBoardSaveResponseDto(Long boardId, String title, String content, String nickname, Long memberId, LocalDateTime createDate){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.memberId = memberId;
        this.createDate = createDate;
    }
}
