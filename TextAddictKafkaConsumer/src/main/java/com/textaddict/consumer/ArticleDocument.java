package com.textaddict.consumer;

import com.textaddict.dto.ArticlePageDto;
import com.textaddict.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Getter
@Setter
public class ArticleDocument {
    @Id
    private String id;
    private String uuid;
    private String text;
    private String header;
    private String description;
    private List<String> keys;
    private List<UserDto> contributors;
    private List<ArticlePageDto> pages;
    private Date createdDate;
    private Date modifiedDate;
    private UserDto creator;

}
