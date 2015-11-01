package com.company.Simulation.Agents.Soldiers;

import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.WarriorSquad;

/**
 * Created by Szymon on 2015-10-31.
 */
public class Warrior extends Soldier {

    protected WarriorSquad squad;

    public Warrior(WarriorSquad squad) {
        super(squad);
    }

    @Override
    public WarriorSquad getSquad()
    {
        return squad;
    }
}
