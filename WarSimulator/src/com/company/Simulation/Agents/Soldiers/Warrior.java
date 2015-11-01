package com.company.Simulation.Agents.Soldiers;

import com.company.Simulation.Agents.Squads.WarriorSquad;
import com.company.Simulation.Behaviours.AgentsBehaviours.WarriorBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class Warrior extends Soldier {

    protected WarriorSquad squad;

    public Warrior(WarriorSquad _squad) {
        super();
        squad = _squad;
    }

    @Override
    public WarriorSquad getSquad()
    {
        return squad;
    }

    @Override
    protected void setup() {
        addBehaviour(new WarriorBehaviour(this));
    }
}
