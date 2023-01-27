package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;
import com.example.ProducerConsumer.ProducerConsumer.Components.Time;

import java.util.List;

public class AddingProductsInRandom extends AddingProducts implements Runnable
{
    public AddingProductsInRandom(Manager m, int numberOfProducts)
    {
        super(m, numberOfProducts);
    }

    @Override
    public void run()
    {
        for (int i = 0; i < this.NumberOfProducts; i++)
        {
            long time = Time.GetRandomTimeInMilliseconds();
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
}
