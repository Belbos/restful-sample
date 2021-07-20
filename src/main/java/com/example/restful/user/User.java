package com.example.restful.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
//@JsonFilter("UserInfo")
@NoArgsConstructor // 디폴트 생성자 어노테이션
@ApiModel(description = "사용자 상제 정보를 위한 도메인 객체")
@Entity // h2를 통하여 자동적으로 테이블 설정
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Past
    private Date joinDate;
    @Size(min=2, message = "Name은 2이상의 값을 입력해 주십시오.")
    private String name;
//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;

    // 1:N의 관계
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, Date joinDate, String name, String password, String ssn) {
        this.id = id;
        this.joinDate = joinDate;
        this.name = name;
        this.password = password;
        this.ssn = ssn;
    }
}
