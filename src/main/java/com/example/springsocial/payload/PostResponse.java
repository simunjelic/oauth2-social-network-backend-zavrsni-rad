package com.example.springsocial.payload;

import javax.validation.constraints.NotBlank;
public class PostResponse {
    @NotBlank
    private Long id;
    @NotBlank
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
