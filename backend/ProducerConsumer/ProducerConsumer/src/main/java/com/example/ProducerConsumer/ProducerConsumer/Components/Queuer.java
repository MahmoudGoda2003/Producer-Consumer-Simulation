package com.example.ProducerConsumer.ProducerConsumer.Components;

import com.example.ProducerConsumer.ProducerConsumer.Components.Machine;
import com.example.ProducerConsumer.ProducerConsumer.Components.Node;
import com.example.ProducerConsumer.ProducerConsumer.Components.Product;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Queuer extends Node
{
    private Queue<Product> productList = new LinkedList<>();

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
        if (this.productList.isEmpty() == false)
        {
            this.AddToQeue(product);
            return;
        }

        if (CheckMachinesAndHandleProduct(product) == false)
            this.AddToQeue(product);
    }

    public void InitializeQueuer(List<Product> list)
    {
        for (Product product : list)
            this.AddToQeue(product);
    }

    public void ClearQueue()
    {
        this.productList.clear();
    }

    public void StartSimulation()
    {
        Queue<Product> temp = new LinkedList<>();
        while (!productList.isEmpty())
            temp.add(productList.poll());

        for (Product product : temp)
            this.HandleProduct(product);
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
