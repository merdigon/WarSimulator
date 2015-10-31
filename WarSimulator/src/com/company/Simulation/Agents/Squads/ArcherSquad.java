package com.company.Simulation.Agents.Squads;

import com.company.Simulation.Agents.ArrowPhysic;
import com.company.Simulation.Agents.Soldiers.Archer;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArcherSquad extends Squad{

    public ArrowPhysic arrowPhysic = new ArrowPhysic();

    public ArcherSquad(Teams team){
        super(team);
    }

    @Override
    public void setSquad(int a){
        for(int i=0;i<a;i++)
            squadSoldiers.add(new Archer(this));
    }
}