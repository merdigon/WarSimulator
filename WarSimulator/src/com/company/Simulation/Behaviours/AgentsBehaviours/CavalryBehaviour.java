package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;

/**
 * Created by Szymon on 2015-11-17.
 */
public class CavalryBehaviour extends SoldierBehaviour {

    Cavalry soldier;

    public CavalryBehaviour(Cavalry sold){
        soldier = sold;
    }

    @Override
    public Cavalry getSoldier() {
        return soldier;
    }
}
