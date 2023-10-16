package edu.miu.cs545.simplespringsecurity.controllers;

import edu.miu.cs545.simplespringsecurity.model.User;
import edu.miu.cs545.simplespringsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    final private UserService _service;

    public AuthController(AuthenticationManager manager,
                          @Qualifier("userServiceImplementation") UserService service) {
        this.authenticationManager = manager;
        this._service = service;
    }
    @PostMapping("/login")
    public Authentication getLogin(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        return authentication;
        /*UserDetails details = (UserDetails) authentication.getPrincipal();
        return details;*/
    }

    @PostMapping("/register")
    public ResponseEntity<?> postAdd(@RequestBody(required = false) User user) {
        if(user == null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find data for the user to create";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        String emailAddress = user.getEmail();
        if(emailAddress == null || emailAddress.trim().isEmpty()) {
            Object result = new Object(){
                public int code = 1;
                public String message = "Please provide an email address for this user";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        emailAddress = emailAddress.trim();
        if(_service.getUserByEmailAddress(emailAddress) != null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "This email address cannot be used any more.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        user.setEmail(emailAddress);
        user.setUsername(emailAddress);

        String password = user.getPassword();
        if(password == null || password.isEmpty()) {
            Object result = new Object(){
                public int code = 1;
                public String message = "Please provide a password for this user. Make sure to use a strong password for your security.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        user = _service.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
