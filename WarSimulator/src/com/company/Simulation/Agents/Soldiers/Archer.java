package com.company.Simulation.Agents.Soldiers;

import com.company.Simulation.Agents.Squads.ArcherSquad;
import com.company.Simulation.Agents.Squads.CavalrySquad;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Behaviours.AgentsBehaviours.ArcherBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class Archer extends Soldier {

    protected ArcherSquad squad;

    public Archer(ArcherSquad _squad)
    {
        super();
        squad = _squad;
    }

    @Override
    protected void setup()
    {
        addBehaviour(new ArcherBehaviour(this));
    }

    @Override
    public ArcherSquad getSquad() {
        return squad;
    }


    @Override
    public void setSquad(Squad sq){
        squad = (ArcherSquad)sq;
    }
}
