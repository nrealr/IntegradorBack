package com.backend.mediConnect.config.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtProvider {

    private static final Key SECRET_KEY= new SecretKeySpec(("F@cturacionSR12023F@cturacionSR12023F" +
            "@cturacionSR12023").getBytes(), SignatureAlgorithm.HS512.getJcaName());
    private static final long EXPIRATION_TIME = 3600_000;

    public static String generateTokenJWT(String email){
        return Jwts.builder().setSubject(email)
                .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME))).signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateTokenJWT(String token){
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public static String getEmail(String token){
        JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
        return parser.parseClaimsJws(token).getBody().getSubject();
    }
}
