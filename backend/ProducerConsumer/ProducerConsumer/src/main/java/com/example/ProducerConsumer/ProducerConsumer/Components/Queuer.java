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

    @Override
    protected void SetDefultColor(){
        this.SetColor(MyColor.GetDefaultColorForQ());
    }

    @Override
    public int GetProductsNo() {
        return this.productQueue.size();
    }

    private void AddToQeue(Product Product)
    {
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
