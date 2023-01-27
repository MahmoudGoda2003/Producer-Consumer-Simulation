package com.example.ProducerConsumer.ProducerConsumer.Components;

import java.util.LinkedList;
import java.util.Queue;

public class Queuer extends Node
{
    private Queue<Product> productQueue = new LinkedList<>();

    public Queuer(String id)
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
        if (this.productQueue.isEmpty() == false)
        {
            this.AddToQeue(product);
            return;
        }

        if (CheckIfAMachineIsFreeAndMakeItHandleProduct(product) == false)
            this.AddToQeue(product);
    }

    public void ClearQueue()
    {
        this.productQueue.clear();
    }

    private boolean CheckIfAMachineIsFreeAndMakeItHandleProduct(Product product)
    {
        for (Node node : this.NextNodes)
        {
            Machine machine = (Machine) node;
            if (machine.IsCurrentlyHandlingProduct() == false)
            {
                machine.HandleRequest(product);
                return true;
            }
        }
        return false;
    }

    private void AddToQeue(Product Product)
    {
        if (Product == null)
        {
            int z = 0;
            z++;
        }
        this.productQueue.add(Product);
    }

    public Product GetProductFromQueue()
    {
        return this.productQueue.poll();
    }

    public boolean HasProducts()
    {
        return this.productQueue.size() != 0;
    }
}
