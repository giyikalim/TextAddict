package com.textaddict.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ArticleDto {
    private String uuid;
    private String header;
    private List<ArticlePageDto> pages;
    private String description;
    private Integer puplishNumber;
    private Date createdDate;
    private UserDto creator;
    private Date modifiedDate;
    private boolean reader;
    private boolean admin;
    private boolean author;
}
