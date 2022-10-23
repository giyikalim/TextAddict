package com.textaddict.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticlePageDto {
    private String text;
    private Date createdDate;
    private Date modifiedDate;
    private String uuid;
}
