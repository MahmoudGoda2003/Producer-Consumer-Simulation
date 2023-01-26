package com.example.ProducerConsumer.ProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;

public class Queuer extends Node
{
    private Queue<Product> productList = new LinkedList<>();

    public Queuer(int id)
    {
        super(id);
    }

    @Override
    public String toString()
    {
        return "Q" + this.GetID();
    }

    public void HandleProduct(Product product)
    {
        if (this.productList.isEmpty() == false)
        {
            this.AddToQeue(product);
            return;
        }

        if (CheckMachinesAndHandleProduct(product) == false)
            this.AddToQeue(product);
    }

    private boolean CheckMachinesAndHandleProduct(Product product)
    {
        for (Node node : this.NextNodes)
        {
            Machine machine = (Machine) node;
            if (machine.IsCurrentlyHandlingProduct())
            {
                machine.HandleRequest(product);
                return true;
            }
        }
        return false;
    }

    private void AddToQeue(Product Product)
    {
        this.productList.add(Product);
    }

    public Product GetProductFromQueue()
    {
        return this.productList.poll();
    }

    public boolean HasProducts()
    {
        return this.productList.size() != 0;
    }
}
