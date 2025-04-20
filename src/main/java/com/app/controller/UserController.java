package com.app.controller;

import com.app.model.dto.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = "text/event-stream")
    public Flux<User> getUsers(){
        return userService.getAll();
    }

    @GetMapping(value = "/{id}", produces = "text/event-stream")
    public Mono<ResponseEntity<User>> getById(@PathVariable int id){
        return userService.getUserById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{name}", produces = "text/event-stream")
    public Flux<ResponseEntity<User>> getByName(@PathVariable String name){
        return userService.getByName(name).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{username}", produces = "text/event-stream")
    public Flux<ResponseEntity<User>> getByUsername(@PathVariable String username){
        return userService.getByUsername(username).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "", produces = "text/event-stream")
    public Mono<ResponseEntity<User>> addUser(@RequestBody User user){
        return userService.addUser(user).map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser));
    }

    @PutMapping(value = "", produces = "text/event-stream")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody User user){
        return userService.updateUser(user).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "", produces = "text/event-stream")
    public Mono<ResponseEntity<Void>> delete(@PathVariable int id){
        return userService.getUserById(id)
                .flatMap(user -> userService.deleteUser(id).then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
