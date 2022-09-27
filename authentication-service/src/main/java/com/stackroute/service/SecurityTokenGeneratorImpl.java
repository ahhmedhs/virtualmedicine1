package com.stackroute.service;


import com.stackroute.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityTokenGeneratorImpl implements SecurityTokenGenerator{


    /*Method to generate a JWT authentication token*/
    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken;
        jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"secretkey").compact();
        Map<String,String> map = new HashMap<>();
        map.put("jwtToken",jwtToken);
        map.put("message","login successfull");
        return map;
    }


    /*Method to encode the password*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
