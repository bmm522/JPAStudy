package com.callbus.community.controller.dto.request;

import com.callbus.community.domain.Board;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BoardSaveRespDto {

    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 1, max = 500)
    @NotBlank
    private String content;

    public Board toEntity(){
        return  Board.builder()
                .title(title)
                .content(content)
                .build();

    }

}
