package com.personal.community.service.dto.request;

import com.personal.community.domain.Board;
import com.personal.community.domain.util.Status;
import lombok.Getter;

@Getter
public class ServiceSaveRequestDto implements ServiceRequestDto, com.personal.community.service.dto.request.ServiceSaveRequestDto {


    private String title;

    private String content;

    private Long memberId;

    private String accountType;


    @Override
    public Board toEntity() {
        return  Board.builder()
                .title(title)
                .content(content)
                .status(Status.Y)
                .build();
    }
}
