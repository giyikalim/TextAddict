package com.textaddict.userapi.ui.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class UserDetailResponse {
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String profession;
    private String site;
    private String nickName;
    private List<ArticleResponse> articles;
}
