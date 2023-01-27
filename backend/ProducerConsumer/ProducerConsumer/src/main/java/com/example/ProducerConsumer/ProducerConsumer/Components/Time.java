package com.example.ProducerConsumer.ProducerConsumer.Components;

import java.util.concurrent.ThreadLocalRandom;

public class Time
{
    public static long GetRandomTimeInMilliseconds(int rangeInSeconds)
    {
        int randomNumInSeconds = ThreadLocalRandom.current().nextInt(0, rangeInSeconds);
        long randomtime = randomNumInSeconds * 1000;
        return randomtime;
    }
}
