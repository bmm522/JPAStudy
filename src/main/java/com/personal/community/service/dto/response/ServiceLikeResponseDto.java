package com.personal.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceLikeResponseDto {

    private Long boardId;
    private Integer likeCount;

    @Builder
    public ServiceLikeResponseDto(Long boardId, int likeCount){
        this.boardId = boardId;
        this.likeCount = likeCount;
    }
}
