package com.company.Simulation.Agents;

/**
 * Created by Szymon on 2015-10-16.
 */
import com.company.Simulation.Command;
import com.company.Simulation.Behaviours.SoldierBehaviour;
import jade.core.Agent;

public class Soldier extends Agent {
    Command comm;

    @Override
    protected void setup()
    {
        addBehaviour(new SoldierBehaviour());
    }

    public void setCommand(Command comm)
    {
        this.comm = comm;
    }
}
