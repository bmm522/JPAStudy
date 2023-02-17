package com.personal.community.service.dto.request;

import com.personal.community.domain.Board;
import com.personal.community.domain.util.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceSaveBoardSaveRequestDto implements ServiceSaveRequestDto {

    private String title;
    private String content;
    private Long memberId;
    private String accountType;

    @Builder
    public ServiceSaveBoardSaveRequestDto(String title, String content, Long memberId, String accountType) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.accountType = accountType;
    }


    public class ServiceSaveBoardRequestDto {

        public Board toEntity(){
            return  Board.builder()
                    .title(title)
                    .content(content)
                    .status(Status.Y)
                    .build();
        }
    }



}
