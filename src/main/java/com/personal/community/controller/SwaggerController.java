package com.personal.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {

    @GetMapping("/api/community")
    public String moveSwaggerForm(){
        return "redirect:/swagger-ui.html";
    }
}
