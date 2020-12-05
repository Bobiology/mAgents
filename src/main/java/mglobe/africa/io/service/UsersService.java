package mglobe.africa.io.service;

import mglobe.africa.io.domain.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Users}.
 */
public interface UsersService {

    /**
     * Save a users.
     *
     * @param users the entity to save.
     * @return the persisted entity.
     */
    Users save(Users users);

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Users> findAll(Pageable pageable);


    /**
     * Get the "id" users.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Users> findOne(String id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
