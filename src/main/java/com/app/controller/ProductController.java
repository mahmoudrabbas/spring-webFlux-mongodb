package com.app.controller;

import com.app.model.Product;
import com.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "", produces = "text/event-stream")
    public Flux<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping(value = "/{id}", produces = "text/event-stream")
    public Mono<ResponseEntity<Product>> getById(@PathVariable String id){
        return productService.getById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "", produces = "text/event-stream")
    public Mono<ResponseEntity<Product>> add(@RequestBody Product product){
        return productService.insert(product).map(createdProduct -> ResponseEntity.status(HttpStatus.CREATED).body(createdProduct));
    }

    @PutMapping(value = "", produces = "text/event-stream")
    public Mono<ResponseEntity<Product>> edit(@RequestBody Product product){
        return productService.update(product).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = "text/event-stream")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return productService.getById(id)
                .flatMap(existedProduct-> productService.delete(id).then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
