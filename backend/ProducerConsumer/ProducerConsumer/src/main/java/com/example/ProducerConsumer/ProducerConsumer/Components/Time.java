package com.example.ProducerConsumer.ProducerConsumer.Components;

import java.util.concurrent.ThreadLocalRandom;

public class Time
{
    public static long GetRandomTimeInMilliseconds()
    {
        int rangeInSeconds = 6;
        int randomNumInSeconds = ThreadLocalRandom.current().nextInt(1, rangeInSeconds);
        long randomTime = randomNumInSeconds * 1000;
        return randomTime;
    }
}
