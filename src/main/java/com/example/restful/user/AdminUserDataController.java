package com.example.restful.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/admin")
public class AdminUserDataController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/")
    public String serverSate(){
        return "Hellow Admin Server Ok";
    }

    @GetMapping("/users")
    public MappingJacksonValue findAll(){

        List<User> users = service.findAll();

        // 필터 셋팅
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate","password");
        // 필터 프로바이더 생성
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
        // 유저 정보 전달
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        // 유저정보에 필터 적용
        mapping.setFilters(filters);
        return mapping;
    }

//    @GetMapping("/v1/users/{id}") // URI로 버전관리
//    @GetMapping(value = "/users/{id}", params = "version=1") // params 으로 버전관리
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") // headers 로 버전관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") // media type
    public MappingJacksonValue findUserV1(@PathVariable int id){
        User user = service.findUser(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // 필터 셋팅
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate","password", "ssn");
        // 필터 프로바이더 생성
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
        // 유저 정보 전달
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        // 유저정보에 필터 적용
        mapping.setFilters(filters);
        return mapping;
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue findUserV2(@PathVariable int id){
        User user = service.findUser(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // user -> User2
        UserV2 userv2 = new UserV2();
        BeanUtils.copyProperties(user, userv2);
        userv2.setGrade("VIPs");
        // 필터 셋팅
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate","grade");
        // 필터 프로바이더 생성
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);
        // 유저 정보 전달
        MappingJacksonValue mapping = new MappingJacksonValue(userv2);
        // 유저정보에 필터 적용
        mapping.setFilters(filters);
        return mapping;
    }




}
