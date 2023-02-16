package com.personal.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ServiceGetBoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private String createDate;
    private String updateDate;
    private String writer;
    private Long memberId;

    private Integer hit;

    private int likeCount;


    private String targetMemberModificationPermission;

    private String targetMemberIsLike;

    @Builder
    public ServiceGetBoardResponseDto(Long boardId, String title, String content, String createDate, String updateDate, String writer, Long memberId, Integer hit, int likeCount, String targetMemberModificationPermission, String targetMemberIsLike) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.writer = writer;
        this.memberId = memberId;
        this.hit = hit;
        this.likeCount = likeCount;
        this.targetMemberModificationPermission = targetMemberModificationPermission;
        this.targetMemberIsLike = targetMemberIsLike;
    }


}
