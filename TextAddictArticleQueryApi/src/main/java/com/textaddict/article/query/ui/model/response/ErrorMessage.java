package com.textaddict.article.query.ui.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class ErrorMessage {
    private Date timestamp;
    private String message;
}
