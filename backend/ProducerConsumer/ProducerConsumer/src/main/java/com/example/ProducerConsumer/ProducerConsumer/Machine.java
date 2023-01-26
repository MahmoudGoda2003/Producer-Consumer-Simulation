package com.example.ProducerConsumer.ProducerConsumer;

import java.util.concurrent.ThreadLocalRandom;

public class Machine extends Node implements Runnable
{
    private Thread mythread;
    private Product myProduct;

    public Machine(int id)
    {
        super(id);
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
        long randomtime = this.GetRandomTime();
        try
        {
            Thread.sleep(randomtime);
        }
        catch(Exception e)
        {}
        this.AfterOperationgOnProduct();
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

    public long GetRandomTime()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        long randomtime = randomNum * 1000;
        return randomtime;
    }

    @Override
    public String toString()
    {
        return "M" + this.GetID();
    }
}
