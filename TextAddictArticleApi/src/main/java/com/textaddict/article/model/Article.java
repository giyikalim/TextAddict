package com.textaddict.article.model;

import com.textaddict.model.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Article extends BaseEntity {
    public Article(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public Article(){

    }

    private String header;
    private String text;
}
