package com.test.generator;

import com.test.exceptions.FailedToCreateCollectionException;

import java.util.Random;
import java.util.Set;

public class StringIdGenerator implements IdGenerator {

    @Override
    public String generate(String url, Set<String> collisions) {
        boolean collision = false;
        StringBuilder res = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(chars.length());
            res.append(chars.charAt(index));
        }

        do{
            if(collision){
               int randomPosition = random.nextInt(res.length());
               int randomChar = random.nextInt(chars.length());
               res.setCharAt(randomPosition, chars.charAt(randomChar));
            }

            if(collisions.contains(res)){
                collision = true;
            }
            else{
                return res.toString();
            }

        }while (collision);

        throw new FailedToCreateCollectionException();

    }
}
