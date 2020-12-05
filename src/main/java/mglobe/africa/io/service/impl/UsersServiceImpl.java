package mglobe.africa.io.service.impl;

import mglobe.africa.io.service.UsersService;
import mglobe.africa.io.domain.Users;
import mglobe.africa.io.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users save(Users users) {
        log.debug("Request to save Users : {}", users);
        return usersRepository.save(users);
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable);
    }


    @Override
    public Optional<Users> findOne(String id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}
