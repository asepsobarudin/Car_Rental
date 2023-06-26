package com.example.carrental;

import java.util.Random;

public class RandomText {
    public int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public char getRandomCharacter() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'a');
    }
}



