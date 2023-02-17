package com.personal.community.service.dto.request;

import com.personal.community.repository.entity.Board;
import com.personal.community.repository.entity.util.Status;
import lombok.Getter;

@Getter
public class ServiceSaveRequestDto  {


    private String title;

    private String content;

    private Long memberId;

    private String accountType;


    public Board toEntity() {
        return  Board.builder()
                .title(title)
                .content(content)
                .status(Status.Y)
                .build();
    }
}
