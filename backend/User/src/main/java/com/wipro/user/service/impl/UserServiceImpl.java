package com.wipro.user.service.impl;

import com.wipro.user.dao.UserRepository;
import com.wipro.user.dto.LoginDto;
import com.wipro.user.dto.UserDTO;
import com.wipro.user.entity.LoginMessage;
import com.wipro.user.entity.User;
import com.wipro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO addUser(UserDTO userDto) {
        User user=new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        String encode=passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encode);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public LoginMessage loginUser(LoginDto credentials) {
        String msg="";
        User user1=userRepository.findByEmail(credentials.getEmail());
        if(Objects.nonNull(user1)){
            String password = credentials.getPassword();
            String encodedPassword=user1.getPassword();
            Boolean isPwdRight=passwordEncoder.matches(password,encodedPassword);
            if(isPwdRight){
               Optional<User> userPresent=userRepository.findByEmailAndPassword(credentials.getEmail(), encodedPassword);
                if(userPresent.isPresent()) {
                    return new LoginMessage(userPresent.get().getName(),userPresent.get().getEmail(),"Login Success", true,userPresent.get().getRole());
                }
                else{
                    return new LoginMessage(null,credentials.getEmail(),"Login Failed", false,null);
                }
            }
            else{
                return new LoginMessage(null,credentials.getEmail(),"Password does not match", false,null);
            }
        }
        else{
            return new LoginMessage(null,credentials.getEmail(),"User does not exists", false,null);
        }

    }
}
