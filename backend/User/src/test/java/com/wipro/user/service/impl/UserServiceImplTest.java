package com.wipro.user.service.impl;

import com.wipro.user.dao.UserRepository;
import com.wipro.user.dto.LoginDto;
import com.wipro.user.dto.UserDTO;
import com.wipro.user.entity.LoginMessage;
import com.wipro.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser_Success() {
        // Given
        UserDTO userDto = new UserDTO();
        userDto.setId(1);
        userDto.setName("John Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setRole("USER");
        userDto.setPassword("password");

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserDTO savedUserDto = userService.addUser(userDto);

        // Then
        assertNotNull(savedUserDto);
        assertEquals(userDto.getName(), savedUserDto.getName());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(userDto.getPassword());
    }

    @Test
    public void testLoginUser_Success() {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("johndoe@example.com");
        loginDto.setPassword("password");

        User user = new User();
        user.setEmail("johndoe@example.com");
        user.setPassword("encodedPassword");
        user.setName("John Doe");
        user.setRole("USER");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
        when(userRepository.findByEmailAndPassword(loginDto.getEmail(), user.getPassword())).thenReturn(Optional.of(user));

        // When
        LoginMessage loginMessage = userService.loginUser(loginDto);

        // Then
        assertNotNull(loginMessage);
        assertEquals("Login Success", loginMessage.getMessage());
        assertEquals("John Doe", loginMessage.getName());
        assertEquals("johndoe@example.com", loginMessage.getEmail());
        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), user.getPassword());
    }

    @Test
    public void testLoginUser_PasswordMismatch() {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("johndoe@example.com");
        loginDto.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("johndoe@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        // When
        LoginMessage loginMessage = userService.loginUser(loginDto);

        // Then
        assertNotNull(loginMessage);
        assertEquals("Password does not match", loginMessage.getMessage());
        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), user.getPassword());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("unknown@example.com");
        loginDto.setPassword("password");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(null);

        // When
        LoginMessage loginMessage = userService.loginUser(loginDto);

        // Then
        assertNotNull(loginMessage);
        assertEquals("User does not exists", loginMessage.getMessage());
        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    }
}
