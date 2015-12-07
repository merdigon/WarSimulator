package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;

/**
 * Created by Szymon on 2015-12-07.
 */
public class CommanderBehaviour extends CyclicBehaviour {

    Commander comm;

    public CommanderBehaviour(Commander comm){
        this.comm = comm;
    }

    @Override
    protected void action(){
        //Tu pakujesz myœlenie genera³a
    }
}
