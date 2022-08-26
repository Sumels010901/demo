package com.example.service;

import com.example.model.CustomUserDetails;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    @Transactional
    public UserDetails loadUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return new CustomUserDetails(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(Integer Id){
        return userRepository.findById(Id);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
    public Page<User> listAll(Pageable pageable) {
        int pageSize = 5;
        pageable = PageRequest.of(0, pageSize);
        return userRepository.findAll(pageable);
    }
}
