package mglobe.africa.io.repository;

import mglobe.africa.io.domain.Users;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
}
