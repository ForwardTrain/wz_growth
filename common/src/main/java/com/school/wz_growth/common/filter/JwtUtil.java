package com.school.wz_growth.common.filter;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    static final String SECRET = "info.key";

    /**
     * type 1：admin 2:person
     * @param u_name
     * @param uId
     * @param   role_type
     * @return
     */
    public static String generateToken(String u_name,Long uId,String role_type) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("uId", uId);
        map.put("u_name",u_name);
        map.put("role_type",role_type);
        org.apache.commons.codec.binary.Base64 bs = new org.apache.commons.codec.binary.Base64();
        String SECRET2=new String(bs.encode(SECRET.getBytes()));
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))// 1000 hour
                .signWith(SignatureAlgorithm.HS512,SECRET2)
                .compact();
        return "Bearer "+jwt; //jwt前面一般都会加Bearer
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> body;
        try {
            org.apache.commons.codec.binary.Base64 bs = new org.apache.commons.codec.binary.Base64();
            String SECRET2=new String(bs.encode(SECRET.getBytes()));
            // parse the token.
            body = Jwts.parser()
                    .setSigningKey(SECRET2)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody();
        }catch (Exception e){
            throw new IllegalStateException("Invalid Token. ");
        }
        return body;
    }
}
