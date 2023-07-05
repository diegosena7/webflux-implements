package br.com.dsena7.webflux.repository;

import br.com.dsena7.webflux.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Mono<UserEntity> save(UserEntity user){
        return mongoTemplate.save(user);
    }

    public Mono<UserEntity> findOneUserById(String id) {
        return mongoTemplate.findById(id, UserEntity.class);
    }
}
