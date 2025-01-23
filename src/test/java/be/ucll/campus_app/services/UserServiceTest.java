package be.ucll.campus_app.services;

import be.ucll.campus_app.models.User;
import be.ucll.campus_app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
    }

    @Test
    void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.addUser(testUser);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }
}