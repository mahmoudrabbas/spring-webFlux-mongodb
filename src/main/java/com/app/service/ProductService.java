package com.app.service;

import com.app.model.Product;
import com.app.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Flux<Product> getAll(){
        return productRepo.findAll();
    }

    public Mono<Product> getById(String id){
        return productRepo.findById(id);
    }

    public Mono<Product> insert(Product entity){
        return productRepo.insert(entity);
    }

    public Mono<Product> update(Product entity){
        return productRepo.findById(entity.getId()).flatMap(author -> {
            author.setProductName(entity.getProductName());
            author.setPrice(entity.getPrice());


            return productRepo.save(entity);
        });
    }

    public Mono<Void> delete(String id){
        return productRepo.deleteById(id);
    }


}
