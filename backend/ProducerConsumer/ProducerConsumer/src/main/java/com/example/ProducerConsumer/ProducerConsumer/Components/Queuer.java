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
    public synchronized String toString()
    {
        return "Q" + this.GetID();
    }

    public synchronized void HandleProduct(Product product)
    {
        if (this.productQueue.isEmpty() == false)
        {
            this.AddToQeue(product);
            return;
        }

        if (CheckIfAMachineIsFreeAndMakeItHandleProduct(product) == false)
            this.AddToQeue(product);
    }

    public synchronized Product CheckQueueNotEmptyAndGetProduct()
    {
        if (this.productQueue.isEmpty()) return null;
        return this.GetProductFromQueue();
    }

    public synchronized void ClearQueue()
    {
        this.productQueue.clear();
    }

    private synchronized boolean CheckIfAMachineIsFreeAndMakeItHandleProduct(Product product)
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
    protected synchronized void SetDefaultColor(){
        this.SetColor(MyColor.GetDefaultColorForQ());
    }

    @Override
    public synchronized int GetProductsNo() {
        return this.productQueue.size();
    }

    private synchronized void AddToQeue(Product Product)
    {
        this.productQueue.add(Product);
    }

    public synchronized Product GetProductFromQueue()
    {
        return this.productQueue.poll();
    }

    public synchronized boolean HasProducts()
    {
        return this.productQueue.size() != 0;
    }
}
