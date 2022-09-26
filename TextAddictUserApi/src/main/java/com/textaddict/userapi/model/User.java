package com.textaddict.userapi.model;

import com.textaddict.model.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true)
    private String email;
    private Date birthDate;
    private String profession;
    private String site;
    private String nickName;
}
