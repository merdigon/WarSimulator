package com.company.Simulation.Agents.Soldiers;

import com.company.Simulation.Agents.Squads.CavalrySquad;
import com.company.Simulation.Behaviours.AgentsBehaviours.CavalryBehaviour;

/**
 * Created by Szymon on 2015-11-17.
 */
public class Cavalry extends Soldier {

    CavalrySquad squad;

    public Cavalry(CavalrySquad squad){
        this.squad = squad;
    }

    @Override
    public CavalrySquad getSquad() {
        return squad;
    }

    @Override
    protected void setup() {
        addBehaviour(new CavalryBehaviour(this));
    }
}
