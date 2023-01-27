package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Manager;
import com.example.ProducerConsumer.ProducerConsumer.Product;
import com.example.ProducerConsumer.ProducerConsumer.Queuer;
import com.sun.source.tree.TryTree;

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

    public void RunRequest()
    {
        Thread thread = new Thread(this);
        thread.run();
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
        manager.AddProduct();
    }
}