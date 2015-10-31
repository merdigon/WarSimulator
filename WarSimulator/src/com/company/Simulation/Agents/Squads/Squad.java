package com.company.Simulation.Agents.Squads;

import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Squad {
    protected Command comm;
    protected ArrayList<Soldier> squadSoldiers;

    public Squad(){
        squadSoldiers = new ArrayList<Soldier>();
    }

    public void giveCommand()
    {
        if(comm!=null)
        {
            for(int i=0;i<squadSoldiers.size();i++)
            {
                squadSoldiers.get(i).setCommand(comm);
            }
        }
    }
}
