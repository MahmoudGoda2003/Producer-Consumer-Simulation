package com.example.ProducerConsumer.ProducerConsumer.Components;

import com.example.ProducerConsumer.ProducerConsumer.Components.Node;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;

import java.util.concurrent.ThreadLocalRandom;

public class Machine extends Node implements Runnable, SubjectOfObserver
{
    private Thread mythread;
    private Product myProduct;
    private final long MachineTimeInMilliseconds;

    public Machine(String id, long machineTime)
    {
        super(id);
        this.MachineTimeInMilliseconds = machineTime;

        mythread = new Thread(this, "Thread " + this.toString());
    }

    public Machine(String id)
    {
        super(id);
        //this.MachineTimeInMilliseconds = Time.GetRandomTimeInMilliseconds();
        this.MachineTimeInMilliseconds = 3000;

        mythread = new Thread(this);
    }

    public synchronized void SetProduct(Product product)
    {
        this.myProduct = product;
    }

    public synchronized void HandleRequest(Product product)
    {
        System.out.println(product);
        this.SetProduct(product);
        this.GiveSameColorAsProduct();

        this.mythread = new Thread(this);
        Thread thread = this.mythread;
        System.out.println(this.myProduct);
        thread.run();
        thread.interrupt();
    }

    @Override
    public void run()
    {
        this.PrintStartingObjectMessage();
        try
        {
            Thread.sleep(this.MachineTimeInMilliseconds);
        }
        catch(Exception e)
        {}

        System.out.println(this.myProduct);

        this.PrintFinishingObjectMessage();
        this.AfterOperationgOnProduct();
    }

    public synchronized void StopThread()
    {
        this.mythread.interrupt();
    }

    private synchronized void AfterOperationgOnProduct()
    {
        this.SendObjectToNextQueuer();
        this.ClearProductAndGetReady();
        this.NotifyObservers();
    }

    private synchronized void SendObjectToNextQueuer()
    {
        Queuer queuer = (Queuer) this.NextNodes.get(0);
        queuer.HandleProduct(this.myProduct);
    }

    @Override
    public synchronized void NotifyObservers()
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

    public synchronized boolean IsCurrentlyHandlingProduct()
    {
        return this.myProduct != null;
    }

    public synchronized void PrintStartingObjectMessage()
    {
        System.out.printf("Machine %s Starting to Handle Product %s time%n", this.toString(), this.myProduct.toString());
    }

    public synchronized void PrintFinishingObjectMessage()
    {
        System.out.printf("Machine %s Finished Handling Product %s time %s%n", this.toString(), this.myProduct.toString(), this.MachineTimeInMilliseconds);
    }

    public synchronized void ClearProductAndGetReady()
    {
        this.myProduct = null;
    }

    private synchronized void GiveSameColorAsProduct()
    {
        String color = this.myProduct.GetColor();
        this.SetColor(color);
    }

    @Override
    public synchronized String toString()
    {
        return "M" + this.GetID();
    }
}
