package com.example.ProducerConsumer.ProducerConsumer;

public class AddingProducts implements Runnable
{
    private static AddingProducts myproduct;
    private final int NumberOfProducts;
    private final Manager manager;
    private final Thread mythread;

    private AddingProducts(Manager m, int numberOfProducts)
    {
        this.manager = m;
        this.NumberOfProducts = numberOfProducts;
        mythread = new Thread(this);
    }

    public void StartAddingProductsSimulation()
    {
        this.mythread.start();
    }

    public void PauseAddingProducts()
    {
        try {
            this.mythread.wait();
        }
        catch(Exception e)
        {
        }
    }

    public void Resume()
    {
        this.mythread.notify();
    }


    @Override
    public void run()
    {
        for (int i = 0; i < this.NumberOfProducts; i++)
        {
            this.manager.AddProduct();
            long time = Time.GetRandomTimeInMilliseconds(6);
            try
            {
                this.mythread.sleep(time);
            }
            catch(Exception e)
            {
            }
        }
    }

    public static AddingProducts SetAddingProducts(Manager m, int numberOfProducts)
    {
        AddingProducts.myproduct = new AddingProducts(m, numberOfProducts);
        return AddingProducts.myproduct;
    }

    public static AddingProducts GetAddingProductsInstance()
    {
        return AddingProducts.myproduct;
    }
}
