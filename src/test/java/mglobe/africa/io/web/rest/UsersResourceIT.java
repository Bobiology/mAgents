package mglobe.africa.io.web.rest;

import mglobe.africa.io.MAgentsApp;
import mglobe.africa.io.domain.Users;
import mglobe.africa.io.repository.UsersRepository;
import mglobe.africa.io.service.UsersService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsersResource} REST controller.
 */
@SpringBootTest(classes = MAgentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ROLES = "AAAAAAAAAA";
    private static final String UPDATED_ROLES = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ASSIGNED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ASSIGNED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MockMvc restUsersMockMvc;

    private Users users;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createEntity() {
        Users users = new Users()
            .userID(DEFAULT_USER_ID)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .roles(DEFAULT_ROLES)
            .dateAssigned(DEFAULT_DATE_ASSIGNED);
        return users;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createUpdatedEntity() {
        Users users = new Users()
            .userID(UPDATED_USER_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .roles(UPDATED_ROLES)
            .dateAssigned(UPDATED_DATE_ASSIGNED);
        return users;
    }

    @BeforeEach
    public void initTest() {
        usersRepository.deleteAll();
        users = createEntity();
    }

    @Test
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();
        // Create the Users
        restUsersMockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUsers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUsers.getRoles()).isEqualTo(DEFAULT_ROLES);
        assertThat(testUsers.getDateAssigned()).isEqualTo(DEFAULT_DATE_ASSIGNED);
    }

    @Test
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setUserID(null);

        // Create the Users, which fails.


        restUsersMockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.save(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].roles").value(hasItem(DEFAULT_ROLES)))
            .andExpect(jsonPath("$.[*].dateAssigned").value(hasItem(DEFAULT_DATE_ASSIGNED.toString())));
    }
    
    @Test
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.save(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.roles").value(DEFAULT_ROLES))
            .andExpect(jsonPath("$.dateAssigned").value(DEFAULT_DATE_ASSIGNED.toString()));
    }
    @Test
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsers() throws Exception {
        // Initialize the database
        usersService.save(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        updatedUsers
            .userID(UPDATED_USER_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .roles(UPDATED_ROLES)
            .dateAssigned(UPDATED_DATE_ASSIGNED);

        restUsersMockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsers)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUsers.getRoles()).isEqualTo(UPDATED_ROLES);
        assertThat(testUsers.getDateAssigned()).isEqualTo(UPDATED_DATE_ASSIGNED);
    }

    @Test
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersService.save(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
