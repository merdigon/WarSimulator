package com.company.Simulation.Agents.Soldiers;

/**
 * Created by Szymon on 2015-10-16.
 */
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Command;
import com.company.Simulation.Behaviours.SoldierBehaviour;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class Soldier extends Agent {

    protected Command comm;
    protected Squad hisSquad;

    public Soldier(Squad squad){
        hisSquad = squad;
    }

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
