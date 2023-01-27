package com.example.ProducerConsumer.ProducerConsumer.Components;

public class AddingProducts implements Runnable
{
    private static AddingProducts myProduct;
    private final int NumberOfProducts;
    private final Manager manager;
    private final Thread myThread;

    private AddingProducts(Manager m, int numberOfProducts)
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

    @Override
    public void run()
    {
        for (int i = 0; i < this.NumberOfProducts; i++)
        {
            long time = Time.GetRandomTimeInMilliseconds(6);
            try
            {
                this.myThread.sleep(time);
            }
            catch(Exception e)
            {
            }

            this.manager.AddProduct();
        }
    }

    public static AddingProducts SetAddingProducts(Manager m, int numberOfProducts)
    {
        if (AddingProducts.myProduct != null)
            return AddingProducts.myProduct;

        AddingProducts.myProduct = new AddingProducts(m, numberOfProducts);
        return AddingProducts.myProduct;
    }

    public static AddingProducts GetAddingProductsInstance()
    {
        return AddingProducts.myProduct;
    }
}
