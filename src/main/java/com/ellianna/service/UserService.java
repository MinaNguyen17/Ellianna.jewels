package com.ellianna.service;

import com.ellianna.DTO.CustomUserDetail;
import com.ellianna.DTO.LoginDTO;
import com.ellianna.DTO.RegisterDTO;
import com.ellianna.config.JWTConfig;
import com.ellianna.exception.BadRequest;
import com.ellianna.model.AccountResponse;
import com.ellianna.model.User;
import com.ellianna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomUserDetail loadUserByUsername (String username){
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()){
            Map<String, Object> error = new HashMap<>();
            error.put("Message", "User not found!");
            throw new BadRequest(error);
        }
        User user = userOptional.get();
        return new CustomUserDetail(user.getUserName(), user.getPassword(), user.getRole(), user.getId());
    }

    public boolean match(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    public String login(LoginDTO loginDTO){
        Optional<User> userOptional = userRepository.findByUserName(loginDTO.getUserName());
        if (userOptional.isEmpty()){
            Map<String, Object> error = new HashMap<>();
            error.put("Message", "User not found!");
            throw new BadRequest(error);
        }
        User user = userOptional.get();
        if (match(loginDTO.getPassword(),user.getPassword())){
            return JWTConfig.generateToken(user);
        }
        else {
            throw new BadRequest("Incorrect password");
        }
    }

    public AccountResponse register(RegisterDTO registerDTO) throws Exception{
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());

        User user = new User();

        user.setUserName(registerDTO.getUserName());
        user.setPassword(hashPassword);
        user.setFullName(registerDTO.getFullName());
        user.setRole(registerDTO.getRole());
        userRepository.save(user);
        return new AccountResponse(user.getId(), user.getUserName(), user.getFullName());
    }
}
