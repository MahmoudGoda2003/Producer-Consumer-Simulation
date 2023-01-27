package com.example.ProducerConsumer.ProducerConsumer;

import com.example.ProducerConsumer.ProducerConsumer.Components.BuildCircuit;
import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Produce")
public class Controller
{
    
    @PostMapping("/Simulate")
    @ResponseBody
    public void Simulate(@RequestBody String data)
    {
        BuildCircuit builder = new BuildCircuit();
        builder.build(data);
        Manager.getManger().StartSimulation();
    }
}
