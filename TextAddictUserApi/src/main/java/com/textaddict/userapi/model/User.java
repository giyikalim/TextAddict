package com.textaddict.userapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<DeniedUser> deniedUsers;
}
