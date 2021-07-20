package com.example.restful.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // http://localhost:8088/jpa/users
    @GetMapping("/users")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserId(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not Found", id));
        }

        //Resource<User> resource = new Resource<>(user.get());

        return user.get();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> CreateUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not Found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> CreatePost(@PathVariable int id, @RequestBody Post post){
        Optional<User> user =  userRepository.findById(id);
        
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not Found", id));
        }
        //사용자 정보 추가
        post.setUser(user.get());
        
        Post savedpost = postRepository.save(post);

        // 현재 URI에 추가한 post의 id를 포함하여 리턴
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedpost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }




}
