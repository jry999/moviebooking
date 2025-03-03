package com.wipro.user.controller;

import com.wipro.user.dto.LoginDto;
import com.wipro.user.dto.UserDTO;
import com.wipro.user.entity.LoginMessage;
import com.wipro.user.entity.User;
import com.wipro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;
    @PostMapping(path="/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto){
        return new ResponseEntity(service.addUser(userDto), HttpStatus.OK);
    }
    @PostMapping(path="/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto credentials){

        LoginMessage message=service.loginUser(credentials);
        if(message.getMessage()=="Login Success") {
        return new ResponseEntity(message,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity(message,HttpStatus.NOT_FOUND);
        }
    }

}
