package com.example.ProducerConsumer.ProducerConsumer.Components;

public class ProductCreator
{
    private static ProductCreator myProductCreator;
    private int ProductIDCounter = 0;

    private int GetAndIncreamentProcutID()
    {
        return this.ProductIDCounter++;
    }

    public static ProductCreator GetProductCreator()
    {
        if (myProductCreator == null)
            myProductCreator = new ProductCreator();
        return myProductCreator;
    }

    public Product CreateProduct()
    {
        int id = this.GetAndIncreamentProcutID();
        Product product = new Product(id);
        return product;
    }
}
