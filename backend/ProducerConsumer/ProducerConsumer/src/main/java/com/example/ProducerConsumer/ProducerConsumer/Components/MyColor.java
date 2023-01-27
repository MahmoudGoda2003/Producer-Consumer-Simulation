package com.example.ProducerConsumer.ProducerConsumer.Components;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyColor
{
    private static int index = 0;
    private static String DefaultColorForM = "grey";
    private static String DefaultColorForQ = "yellow";
    private static List<String> colors = Arrays.asList("red", "blue", "pink", "orange", "cyan", "purple", "green",
            "magento");

    public static String GetRandomColor()
    {
        //int randomNum = ThreadLocalRandom.current().nextInt(0, MyColor.colors.size());
        String color = MyColor.colors.get(index);
        index++;
        index %= MyColor.colors.size();
        return color;
    }

    public static String GetDefaultColorForM()
    {
        return MyColor.DefaultColorForM;
    }

    public static String GetDefaultColorForQ()
    {
        return MyColor.DefaultColorForQ;
    }
}
