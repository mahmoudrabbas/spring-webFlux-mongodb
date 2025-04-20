package com.app.service;

import com.app.model.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final String BASIC_URL = "https://jsonplaceholder.typicode.com/users/";

    WebClient client = WebClient.create(BASIC_URL);

    public Mono<User> getUserById(int id){
        return client
                .get()
                .uri("/"+id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Flux<User> getAll(){
        return client
                .get()
                .uri("/")
                .retrieve()
                .bodyToFlux(User.class);
    }

    public Flux<User> getByUsername(String username){
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/").queryParam("username", username).build())
                .retrieve()
                .bodyToFlux(User.class);
    }


    public Flux<User> getByName(String name){
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/").queryParam("name", name).build())
                .retrieve()
                .bodyToFlux(User.class);
    }

    public Mono<User> addUser(User user){
        return client
                .post()
                .uri("")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }
    public Mono<User> updateUser(User user){
        return client
                .put()
                .uri("")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<Void> deleteUser(int id){
        return client
                .delete()
                .uri(""+id)
                .retrieve()
                .bodyToMono(Void.class);
    }




}
