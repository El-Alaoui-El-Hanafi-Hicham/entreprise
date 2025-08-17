package com.enterprise.service;

import com.enterprise.config.AppConfig;
import com.enterprise.config.JwtService;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dto.AuthenticationResponseDto;
import com.enterprise.dto.LoginUserDto;
import com.enterprise.dto.RegisterUserDto;
import com.enterprise.dto.ResetPasswordMessageDto;
import com.enterprise.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class AuthenticationService {
    private final JwtService jwtService;
    private final AppConfig appConfig;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public ResponseEntity<ResetPasswordMessageDto> resetPassword(String encodedId, HashMap<String,String> password) {
        String decodedString = new String(Base64.getDecoder().decode(encodedId));
        Long id=Long.parseLong(decodedString);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            return ResponseEntity.badRequest().body(ResetPasswordMessageDto.builder().Message("User not found").Status(false).build());

        }else{

            employee.get().setPassword(passwordEncoder.encode(password.get("password")));
            employeeRepository.save(employee.get());
            return ResponseEntity.ok(ResetPasswordMessageDto.builder().Message("Password Changed Successfully!! ").Status(true).build());
        }
    }

    public AuthenticationResponseDto signup(RegisterUserDto registerUserDto) {
        Employee user =  Employee.builder().firstName(registerUserDto.getfirstName())
                .lastName(registerUserDto.getlastName())
                .email(registerUserDto.getEmail())
        .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .build();
        employeeRepository.save(user);
    appConfig.authenticationProvider();
        String jwtToken= jwtService.generateToken(user);
        return AuthenticationResponseDto.builder().JwtKey(jwtToken).build();
    }

    public AuthenticationResponseDto login(LoginUserDto loginUserDto) {
        var  user = employeeRepository.findByEmail(loginUserDto.getEmail());
        if(user.isEmpty()){
            return  AuthenticationResponseDto.builder().Message("USER NOT FOUND").Status(false).build();
        }
        System.out.println("============This is the Credentials================");
        System.out.println(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()).getCredentials());
        System.out.println("============END CREDENTIALS==================");
        Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        if(auth.isAuthenticated()){
            String jwtToken = jwtService.generateToken(user.get());

            return AuthenticationResponseDto.builder().JwtKey(jwtToken).Status(true).Message("Logged In Successfully").build();
        }else{
            return  AuthenticationResponseDto.builder().Message("USER NOT FOUND").Status(false).build();
        }


    }
}
