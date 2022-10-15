package com.textaddict.article.command.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticlePage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="article_id", nullable=false)
    private Article article;

    @NotBlank(message = "Page content can not be empty")
    @Size(max = 1024, message = "Content lenght can not be longer then 1024 characters in a one page")
    private String text;
}
