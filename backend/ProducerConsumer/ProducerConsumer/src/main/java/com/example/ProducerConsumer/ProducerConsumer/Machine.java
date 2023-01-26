package com.example.ProducerConsumer.ProducerConsumer;

import java.util.concurrent.ThreadLocalRandom;

public class Machine extends Node implements Runnable
{
    private Thread mythread;
    private Product myProduct;
    private long MachineTimeInMilliseconds;

    public Machine(int id, long machineTime)
    {
        super(id);
        this.MachineTimeInMilliseconds = machineTime;

        mythread = new Thread(this);
    }

    public Machine(int id)
    {
        super(id);
        int Range = 10;
        this.MachineTimeInMilliseconds = this.GetRandomTimeInMilliseconds(Range);

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

    public long GetRandomTimeInMilliseconds(int rangeInSeconds)
    {
        int randomNumInSeconds = ThreadLocalRandom.current().nextInt(0, rangeInSeconds);
        long randomtime = randomNumInSeconds * 1000;
        return randomtime;
    }

    @Override
    public String toString()
    {
        return "M" + this.GetID();
    }
}
