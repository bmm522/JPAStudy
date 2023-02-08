package com.callbus.community.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class ClientUpdateBoardRequestDto {

    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 1, max = 500)
    @NotBlank
    private String content;

    private LocalDateTime updateDate;


    @Builder // Test용 생성자
    public ClientUpdateBoardRequestDto(String title, String content, LocalDateTime updateDate){
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }
}
