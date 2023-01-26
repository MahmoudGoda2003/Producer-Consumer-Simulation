package com.example.ProducerConsumer.ProducerConsumer;

import java.util.HashMap;

public class Manager
{
    private boolean SimulationGoing = false;
    private int NodeIDCounter = 0;
    private int ProductIDCounter = 0;
    private HashMap<Integer, Node> Map = new HashMap();

    public void AddProduct()
    {
        int ProductID = this.GetAndIncreamentProcutID();
        Product product = new Product(ProductID);
    }

    public void AddQueuer()
    {
        int id = this.GetAndIncreamentNodeID();
        Queuer queuer = new Queuer(id);
        this.AddNodeToMap(queuer);
    }

    public void AddMachine()
    {
        int id = this.GetAndIncreamentNodeID();
        Machine machine = new Machine(id);
        this.AddNodeToMap(machine);
    }

    public void AddEdge(String FirstNodeString, String SecondNodeString)
    {
        Node FirstNode = this.Map.get(FirstNodeString);
        Node SecondNode = this.Map.get(SecondNodeString);

        FirstNode.AddOutEdge(SecondNode);
        SecondNode.AddInEdge(FirstNode);
    }

    public void RemoveEdge(String FirstNodeString, String SecondNodeString)
    {
        Node FirstNode = this.Map.get(FirstNodeString);
        Node SecondNode = this.Map.get(SecondNodeString);

        FirstNode.RemoveOutEdge(SecondNode);
        SecondNode.RemoveInEdge(FirstNode);
    }

    public void AddNodeToMap(Node node)
    {
        int id = node.GetID();
        this.Map.put(id, node);
    }

    public void RemoveNodeFromMap(Node node)
    {
        int id = node.GetID();
        this.Map.remove(id);
    }

    private int GetAndIncreamentNodeID()
    {
        return this.NodeIDCounter++;
    }

    private int GetAndIncreamentProcutID()
    {
        return this.ProductIDCounter++;
    }

    public void StartSimulation()
    {
        this.SimulationGoing = true;
    }
}