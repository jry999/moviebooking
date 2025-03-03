package com.wipro.user.service;

import com.wipro.user.dto.LoginDto;
import com.wipro.user.dto.UserDTO;
import com.wipro.user.entity.LoginMessage;
import com.wipro.user.entity.User;

public interface UserService {

    public UserDTO addUser(UserDTO userDto);

    public LoginMessage loginUser(LoginDto credentials);
}
