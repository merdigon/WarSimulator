package com.company.Simulation.Agents.Squads;

import com.company.Simulation.Agents.Soldiers.Warrior;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-31.
 */
public class WarriorSquad extends Squad {

    public WarriorSquad(Teams team) {
        super(team);
    }

    @Override
    public void setSquad(int a){
        for(int i=0;i<a;i++)
            squadSoldiers.add(new Warrior(this));
    }
}
