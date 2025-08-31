package com.enterprise.controller;

import com.enterprise.config.JwtService;
import com.enterprise.dto.authDTO.AuthenticationResponseDto;
import com.enterprise.dto.authDTO.LoginUserDto;
import com.enterprise.dto.authDTO.RegisterUserDto;
import com.enterprise.dto.authDTO.ResetPasswordMessageDto;
import com.enterprise.service.AuthenticationService;
import com.enterprise.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
public final AuthenticationService authenticationService;
    final UserDetailsService userDetailsService;
    final JwtService jwtService;
    @Autowired
    public EmailService emailService;
@Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserDetailsService userDetailsService, JwtService jwtService, EmailService emailService) {
    this.authenticationService = authenticationService;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.emailService = emailService;
    }





    @GetMapping("/currentEmail")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication object: " + authentication);
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails.getUsername(); // assuming email is stored as the username
            }
        }
        return null;
    }
    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticated() {
        String jwt = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getHeader("Authorization")
                .substring(7);
        boolean isExpired = jwtService.isTokenExpired(jwt);
        return ResponseEntity.ok(!isExpired);
    }


@PostMapping("/register")
public ResponseEntity<AuthenticationResponseDto> signup(@RequestBody RegisterUserDto registerUserDto){
    AuthenticationResponseDto registeredUser = authenticationService.signup(registerUserDto);

    return ResponseEntity.ok(registeredUser);
}
    @PostMapping("/login")
public ResponseEntity<AuthenticationResponseDto> Login(@RequestBody LoginUserDto loginUserDto){
    AuthenticationResponseDto authenticationResponseDto = authenticationService.login(loginUserDto);
        return ResponseEntity.ok(authenticationResponseDto);

}
    @PostMapping("/s")
    public ResponseEntity<Object> s(@RequestBody Object registerUserDto){


        return ResponseEntity.ok(registerUserDto);
    }
    @PostMapping("/resetPassord/{encodedId}")
    public ResponseEntity<ResetPasswordMessageDto> resetPassword(@PathVariable String encodedId, @RequestBody HashMap<String,String> password){
        return authenticationService.resetPassword(encodedId,password);
    }


}
