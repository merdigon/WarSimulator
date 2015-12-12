package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Soldiers.Warrior;

/**
 * Created by Szymon on 2015-11-01.
 */
public class WarriorBehaviour extends SoldierBehaviour {

    Warrior warrior;

    public WarriorBehaviour(Warrior warr)
    {
        warrior = warr;
    }

    @Override
    public Soldier getSoldier() {
        return warrior;
    }

    @Override
    protected void attackFromCommand() {
        Coord enemyCoord = getSoldier().getCommand().getPossition();
        move((int)enemyCoord.getX(), (int)enemyCoord.getY());
    }
}
