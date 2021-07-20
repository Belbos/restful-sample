package com.example.restful.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // 해당 정보가 메인이라는 표시
    // User 데이터가 1개이상 와야함
    // N:1 이라는 의미 N이 Post
    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

}
