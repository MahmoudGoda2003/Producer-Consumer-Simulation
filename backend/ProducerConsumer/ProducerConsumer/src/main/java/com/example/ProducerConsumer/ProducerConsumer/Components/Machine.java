package com.example.ProducerConsumer.ProducerConsumer.Components;

import com.example.ProducerConsumer.ProducerConsumer.Components.Node;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;

import java.util.concurrent.ThreadLocalRandom;

public class Machine extends Node implements Runnable
{
    private final Thread mythread;
    private Product myProduct;
    private long MachineTimeInMilliseconds;

    public Machine(String id, long machineTime)
    {
        super(id);
        this.MachineTimeInMilliseconds = machineTime;

        mythread = new Thread(this, "Thread " + this.toString());
    }

    public Machine(String id)
    {
        super(id);
        int Range = 10;
        this.MachineTimeInMilliseconds = Time.GetRandomTimeInMilliseconds(Range);

        mythread = new Thread(this);
    }

    public void SetProduct(Product product)
    {
        this.myProduct = product;
    }

    public void HandleRequest(Product product)
    {
        this.SetProduct(product);
        this.GiveSameColorAsProduct();

        Thread thread = this.mythread;
        thread.run();
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(this.MachineTimeInMilliseconds);
        }
        catch(Exception e)
        {}
        this.AfterOperationgOnProduct();
    }

    public void StopThread()
    {
        this.mythread.interrupt();
    }

    private void AfterOperationgOnProduct()
    {
        this.SendObjectToNextQueuer();
        this.ClearProductAndGetReady();
        this.NotifyObservers();
    }

    private void SendObjectToNextQueuer()
    {
        Queuer queuer = (Queuer) this.NextNodes.get(0);
        queuer.HandleProduct(this.myProduct);
    }

    public void NotifyObservers()
    {
        for (Node node : this.PreviousNodes)
        {
            Queuer queuer = (Queuer) node;
            if (queuer.HasProducts())
            {
                this.HandleRequest(queuer.GetProductFromQueue());
                break;
            }
        }
    }

    public boolean IsCurrentlyHandlingProduct()
    {
        return this.myProduct != null;
    }

    public void PrintHandlingObjectMessage(Product product)
    {
        System.out.println("Machine " + this.toString() + " Handling Product " + product.toString());
    }

    public void ClearProductAndGetReady()
    {
        this.myProduct = null;
    }

    private void GiveSameColorAsProduct()
    {
        String color = this.myProduct.GetColor();
        this.SetColor(color);
    }

    @Override
    public String toString()
    {
        return "M" + this.GetID();
    }
}