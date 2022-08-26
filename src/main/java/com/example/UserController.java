package com.example;

import com.example.dto.UserDTO;
import com.example.form.UserForm;
import com.example.jwt.JwtTokenProvider;
import com.example.mapper.UserMapper;
import com.example.model.CustomUserDetails;
import com.example.model.User;
import com.example.payload.LoginRequest;
import com.example.payload.LoginResponse;
import com.example.payload.RandomStuff;
import com.example.repository.UserRepository;
import com.example.responseHandler.ResponseHandler;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> userList = userMapper.toUserDTOs(userService.findAll());
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<User>> findAll(Pageable pageable) {
        Page<User> users = userService.listAll(pageable);
        return ResponseEntity.ok(users);
    }
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserForm uf){
        User u = new User();
        u.setUsername(uf.getUsername());
        u.setBirthDate(uf.getBirthDate());
        u.setPassword(uf.getPassword());
        User savedUser = userRepository.save(u);
        UserDTO userDTO = userMapper.toUserDTO(savedUser);
        boolean result=true;
        return ResponseHandler.generateResponse(HttpStatus.OK, result, "User created successfully!", userDTO);
    }

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @GetMapping("/random")
    public RandomStuff randomStuff() {
        return new RandomStuff("Only valid jwt can see this message");
    }
}
