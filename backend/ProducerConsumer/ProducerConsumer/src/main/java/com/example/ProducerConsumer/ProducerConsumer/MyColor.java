package com.example.ProducerConsumer.ProducerConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyColor
{
    private static String Defaultcolor = "grey";
    private static List<String> colors = Arrays.asList("red", "blue", "black", "white", "cyan", "purple");

    public static String GetRandomColor()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, MyColor.colors.size());
        return MyColor.colors.get(randomNum);
    }

    public static String GetDefaultColor()
    {
        return MyColor.GetDefaultColor();
    }
}
