package com.mariemetay.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mariemetay.model.User;
import com.mariemetay.model.dto.UserLoginDTO;
import com.mariemetay.model.dto.UserRegisterDTO;
import com.mariemetay.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public Boolean isUserAlreadyExists(UserRegisterDTO user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean canConnect(UserLoginDTO user) {
        User userRegistered = userRepository.findByEmail(user.getEmail());
        return (userRegistered.getPassword().equals(user.getPassword()));
    }

    public User register(UserRegisterDTO userDTO) {
        User user = registerDtoToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    private User registerDtoToEntity(UserRegisterDTO userRegisterDTO) {
        return modelMapper.map(userRegisterDTO, User.class);
    }

}
