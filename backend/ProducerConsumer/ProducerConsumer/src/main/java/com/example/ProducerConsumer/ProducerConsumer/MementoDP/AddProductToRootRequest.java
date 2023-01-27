package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;
import com.example.ProducerConsumer.ProducerConsumer.Components.Time;

public class AddProductToRootRequest implements Runnable
{
    private Product product;
    private Long WaitingTime;
    private final Manager manager;

    public AddProductToRootRequest(Product p, Long watingTime, Manager manager)
    {
        this.product = p;
        this.WaitingTime = watingTime;
        this.manager = manager;
    }

    public static AddProductToRootRequest CreateRandomRequest(Manager manager)
    {
        Long time = Time.GetRandomTimeInMilliseconds();
        return new AddProductToRootRequest(null, time, manager);
    }

    public void RunRequest()
    {
        Thread two = new Thread(this);
        two.start();
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(WaitingTime);
        }
        catch (Exception e)
        {}
        if (this.product != null)
            manager.AddProduct(this.product);
        else
            manager.AddProduct();
    }
}
