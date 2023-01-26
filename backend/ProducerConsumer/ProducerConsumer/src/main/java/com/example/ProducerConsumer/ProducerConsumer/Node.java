package com.example.ProducerConsumer.ProducerConsumer;

import java.util.ArrayList;
import java.util.List;

public abstract class Node
{
    private int ID;
    private String Color;

    protected List<Node> NextNodes = new ArrayList<>();
    protected List<Node> PreviousNodes = new ArrayList<>();

    public Node(){}
    public Node(int id)
    {
        this.ID = id;
        this.Color = MyColor.GetDefaultColor();
    }

    public int GetID()
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


