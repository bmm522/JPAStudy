package com.callbus.community.controller.dto.response;

import com.callbus.community.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardSaveRespDto {

    private String title;
    private String content;
    private String nickname;
    private Long memberId;

    @Builder
    public BoardSaveRespDto(String title, String content, String nickname, Long memberId){
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.memberId = memberId;
    }
}
