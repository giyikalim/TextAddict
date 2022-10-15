package com.textaddict.article.command.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity {

    public Article(){

    }

    @Size(max = 256, message = "Header can not be longer then 256 characters")
    @NotBlank(message = "Header can not be empty")
    private String header;

    //@NotBlank(message = "Text body can not be empty")

    @OneToMany(mappedBy = "article")
    private List<ArticlePage> pages;

    @Size(max = 512, message = "Description can not be longer then 512 characters")
    private String description;

    private Integer puplishNumber;
}
