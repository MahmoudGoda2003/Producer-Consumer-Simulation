package com.example.ProducerConsumer.ProducerConsumer;

public class Product
{
    private int ID;
    private String Color;

    public Product(int id)
    {
        this.ID = id;
        this.Color = MyColor.GetRandomColor();
    }

    public int GetID()
    {
        return this.ID;
    }

    public String GetColor()
    {
        return this.Color;
    }

    @Override
    public String toString()
    {
        return "P" + this.GetID();
    }
}
