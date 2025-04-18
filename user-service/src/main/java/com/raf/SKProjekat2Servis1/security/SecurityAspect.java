package com.raf.SKProjekat2Servis1.security;

import com.raf.SKProjekat2Servis1.services.implementations.TokenServiceImpl;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private TokenServiceImpl tokenServiceImpl;

    public SecurityAspect(TokenServiceImpl tokenServiceImpl) {
        this.tokenServiceImpl = tokenServiceImpl;
    }
//    public Object extractTokenDetails(String token){
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        String token = null;
//        for(int i = 0; i < signature.getParameterNames().length; i++) {
//            if(signature.getParameterNames()[i].equals("authorization")) {
//                if (joinPoint.getArgs()[i].toString().startsWith("Bearer")) {
//                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
//                }
//            }
//        }
//        Claims claims = tokenServiceImpl.parse(token);
//        return claims;
//    }




    @Around("@annotation(com.raf.SKProjekat2Servis1.security.CheckSecurity)")
    public Object checkSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String token = null;
        for(int i = 0; i < signature.getParameterNames().length; i++) {
            if(signature.getParameterNames()[i].equals("authorization")) {
               if (joinPoint.getArgs()[i].toString().startsWith("Bearer")) {
                   token = joinPoint.getArgs()[i].toString().split(" ")[1];
               }
            }
        }

        if(token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Claims claims = tokenServiceImpl.parse(token);

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        String role = claims.get("role").toString();
        if (Arrays.asList(checkSecurity.roles()).contains(role)) {
            return joinPoint.proceed();
        }

       return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
