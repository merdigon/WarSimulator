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

    private void ifDecideToNotMove(){
        soldier.setVelocity(500);
    }

    private void ifDecideToMove(){
        double vel = soldier.getVelocity() - soldier.getVelocityStep();
        if(vel < soldier.getMaxVelocity())
            soldier.setVelocity(soldier.getMaxVelocity());
        else
            soldier.setVelocity(vel);
    }
}
