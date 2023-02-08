package com.callbus.community.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientSaveBoardRequestDto {

    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 1, max = 500)
    @NotBlank
    private String content;


    @Builder // Test용 생성자
    public ClientSaveBoardRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }

}
