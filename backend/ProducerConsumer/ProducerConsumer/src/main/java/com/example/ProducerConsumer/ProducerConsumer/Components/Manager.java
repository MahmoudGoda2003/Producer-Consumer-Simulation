package com.example.ProducerConsumer.ProducerConsumer.Components;

import com.example.ProducerConsumer.ProducerConsumer.MementoDP.AddProductToRootRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Manager
{
    private static Manager myManager;
    private int ProductIDCounter = 0;
    private HashMap<String, Node> Map = new HashMap();
    private List<AddProductToRootRequest> Requests = new ArrayList<>();
    private Queuer Root;
    private Date StartingDate;
    private int NumberOfProducts;

    private Manager(int numberOfProducts)
    {
        this.StartingDate = new Date();
        this.NumberOfProducts = numberOfProducts;
        AddingProducts.SetAddingProducts(this, this.NumberOfProducts);
    }

    public static Manager SetManager(int numberOfProducts)
    {
        Manager.ClearProgramThreadsAndQueuesAndGetRequest();
        Manager.myManager = new Manager(numberOfProducts);
        return Manager.myManager;
    }

    public static Manager getManger()
    {
        return Manager.myManager;
    }

    public void StartSimulation()
    {
        AddingProducts.GetAddingProductsInstance().StartAddingProductsSimulation();
    }

    public void PauseAddingProducts()
    {
        AddingProducts.GetAddingProductsInstance().PauseAddingProducts();
    }

    public void ResumeAddingProducts()
    {
        AddingProducts.GetAddingProductsInstance().Resume();
    }

    public void RestartSimulation()
    {
        List<AddProductToRootRequest> OldRequest = Manager.ClearProgramThreadsAndQueuesAndGetRequest();
        for (AddProductToRootRequest request : OldRequest) {
            request.RunRequest();
        }
    }

    private static List<AddProductToRootRequest> ClearProgramThreadsAndQueuesAndGetRequest()
    {
        Manager manager = Manager.getManger();
        if (manager == null) return new ArrayList<>();
        manager.StopAllMachineThread();
        manager.ClearAllQueues();
        AddingProducts.GetAddingProductsInstance().StopThread();
        List<AddProductToRootRequest> temp = manager.CopyRequest();
        manager.ClearAllRequests();
        return temp;
    }

    private List<AddProductToRootRequest> CopyRequest(){
        List<AddProductToRootRequest> temp = new ArrayList<>();
        this.Requests.forEach((request)->{
            temp.add(request);
        });
        return temp;
    }

    private void StopAllMachineThread()
    {
        Map.forEach((i, node) -> {
            if (node instanceof Queuer) return;
            Machine machine = (Machine) node;
            machine.StopThread();
        });
    }

    private void ClearAllQueues()
    {
        Map.forEach((i, node) -> {
            if (node instanceof Machine) return;
            Queuer queuer = (Queuer) node;
            queuer.ClearQueue();
        });
    }

    private void ClearAllRequests()
    {
        this.Requests.clear();
    }

    public void AddProduct(Product product)
    {
        this.CreateRequestAndPutInRequestList(product);
        this.Root.HandleProduct(product);
    }

    public void AddProduct()
    {
        int ProductID = this.GetAndIncreamentProcutID();
        Product product = new Product(ProductID);
        this.AddProduct(product);
    }

    private void CreateRequestAndPutInRequestList(Product product)
    {
        Date currentDate = new Date();
        long timeInMillisecond = currentDate.getTime() - this.StartingDate.getTime();

        AddProductToRootRequest request = new AddProductToRootRequest(product, timeInMillisecond, this);
        this.Requests.add(request);
    }

    public Queuer AddQueuer(String id)
    {
        Queuer queuer = new Queuer(id);
        this.AddNodeToMap(queuer);
        return queuer;
    }

    public void AddMachine(String id)
    {
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

    public void SetRoot(Queuer queuer)
    {
        this.Root = queuer;
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
        String id = node.GetID();
        this.Map.put(id, node);
    }

    public void RemoveNodeFromMap(Node node)
    {
        String id = node.GetID();
        this.Map.remove(id);
    }

    private int GetAndIncreamentProcutID()
    {
        return this.ProductIDCounter++;
    }
}