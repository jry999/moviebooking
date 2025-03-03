package com.wipro.user.controller;

import com.wipro.user.dto.LoginDto;
import com.wipro.user.dto.UserDTO;
import com.wipro.user.entity.LoginMessage;
import com.wipro.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // Given
        UserDTO userDto = new UserDTO();
        UserDTO savedUserDto = new UserDTO();
        when(userService.addUser(userDto)).thenReturn(savedUserDto);

        // When
        ResponseEntity<UserDTO> responseEntity = userController.registerUser(userDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(savedUserDto, responseEntity.getBody());
        verify(userService, times(1)).addUser(userDto);
    }

    @Test
    public void testLoginUser_Success() {
        // Given
        LoginDto loginDto = new LoginDto();
        LoginMessage loginMessage = new LoginMessage("test","test@gmail","Login Success", true,"admin");
        when(userService.loginUser(loginDto)).thenReturn(loginMessage);

        // When
        ResponseEntity<?> responseEntity = userController.loginUser(loginDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(loginMessage, responseEntity.getBody());
        verify(userService, times(1)).loginUser(loginDto);
    }

    @Test
    public void testLoginUser_Failure() {
        // Given
        LoginDto loginDto = new LoginDto();
        LoginMessage loginMessage = new LoginMessage("null","null","Login Failed", false,"null");
        when(userService.loginUser(loginDto)).thenReturn(loginMessage);

        // When
        ResponseEntity<?> responseEntity = userController.loginUser(loginDto);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(loginMessage, responseEntity.getBody());
        verify(userService, times(1)).loginUser(loginDto);
    }
}
