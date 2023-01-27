package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import com.example.ProducerConsumer.ProducerConsumer.Components.Time;

import java.util.List;

public abstract class AddingProducts implements Runnable
{
    private static AddingProducts myProduct;
    protected final int NumberOfProducts;
    protected final Manager manager;
    protected final Thread myThread;

    protected AddingProducts(Manager m, int numberOfProducts)
    {
        this.manager = m;
        this.NumberOfProducts = numberOfProducts;
        myThread = new Thread(this);
    }

    public void StartAddingProductsSimulation()
    {
        this.myThread.start();
    }

    public void PauseAddingProducts()
    {
        try {
            this.myThread.wait();
        }
        catch(Exception e)
        {
        }
    }

    public void Resume()
    {
        this.myThread.notify();
    }

    public void StopThread()
    {
        this.myThread.interrupt();
    }

    //@Override
    public abstract void run();

    public static AddingProducts SetAddingProducts(Manager m, int numberOfProducts)
    {
        AddingProducts.myProduct = new AddingProductsInRandom(m, numberOfProducts);
        return AddingProducts.myProduct;
    }

    public static AddingProducts SetAddingProducts(Manager m, int numberOfProducts, List<AddProductToRootRequest> Requests)
    {
        AddingProducts.myProduct = new AddingProductsInRestart(m, numberOfProducts, Requests);
        return AddingProducts.myProduct;
    }


    public static AddingProducts GetAddingProductsInstance()
    {
        return AddingProducts.myProduct;
    }
}
