package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;
import com.example.ProducerConsumer.ProducerConsumer.Components.ProductCreator;
import com.example.ProducerConsumer.ProducerConsumer.Components.Time;

import java.util.ArrayList;
import java.util.List;

public class AddRequest implements Runnable
{
    private long WaitingTime;
    private Thread mythread;

    public AddRequest(long waitingTime)
    {
        this.WaitingTime = waitingTime;
        mythread = new Thread(this);
    }

    public static List<AddRequest> GetListOfRandomRequests(int size)
    {
        List<AddRequest> list = new ArrayList<>();
        long totaltime = 0;

        for (int i = 0; i < size; i++)
        {
            long randomtime = Time.GetRandomTimeInMilliseconds();
            totaltime += randomtime;
            list.add(new AddRequest(totaltime));
        }
        return list;
    }

    public void RunRequest()
    {
        this.mythread.start();
    }

    public void StopRequest()
    {
        this.mythread.interrupt();
    }

    @Override
    public void run()
    {
        Product product = ProductCreator.GetProductCreator().CreateProduct();
        try
        {
            Thread.sleep(this.WaitingTime);
        }
        catch (Exception e)
        {
        }
        Manager.getManger().AddProduct(product);
    }
}
