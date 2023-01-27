package com.example.ProducerConsumer.ProducerConsumer.Components;

import java.util.ArrayList;
import java.util.List;

public abstract class Node
{
    private String ID;
    private String Color;

    protected List<Node> NextNodes = new ArrayList<>();
    protected List<Node> PreviousNodes = new ArrayList<>();

    public Node(){}
    public Node(String id)
    {
        this.ID = id;
        this.SetDefultColor();
    }

    protected abstract void SetDefultColor();

    public abstract int GetProductsNo();

    public String GetID()
    {
        return this.ID;
    }

    public void SetColor(String c)
    {
        this.Color = c;
    }

    public String GetColor()
    {
        return this.Color;
    }

    public void AddInEdge(Node node)
    {
        this.PreviousNodes.add(node);
    }

    public void AddOutEdge(Node node)
    {
        this.NextNodes.add(node);
    }

    public void RemoveInEdge(Node node)
    {
        this.PreviousNodes.remove(node);
    }

    public void RemoveOutEdge(Node node)
    {
        this.NextNodes.remove(node);
    }
}


