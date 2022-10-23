package com.textaddict.userapi.ui.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.textaddict.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class UserDetailResponse extends UserDto {
    @Override
    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getBirthDate() {
        return super.getBirthDate();
    }
}
