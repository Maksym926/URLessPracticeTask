package com.test.generator;

import com.test.exceptions.FailedToCreateUrlException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.Set;

public class SHA1Generator implements IdGenerator {

    Random random = new Random();

    @Override
    public String generate(String url, Set<String> collisions) {

        MessageDigest md;
        try{
          md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new FailedToCreateUrlException();
        }


        byte[] urlBytes = url.getBytes(StandardCharsets.UTF_8);
        boolean collision = false;


        do{
            if(collision){
                int randomByte = random.nextInt(urlBytes.length);
                urlBytes[randomByte] = (byte)random.nextInt();
            }
            byte[] hash = md.digest(urlBytes);
            String id = Base64.getUrlEncoder().encodeToString(hash).substring(0,6);

            if(collisions.contains(id)){
                collision = true;
            }
            else {
                return id;
            }

        }while (collision);

        throw new FailedToCreateUrlException();


    }
}
