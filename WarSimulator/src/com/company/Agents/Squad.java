package com.company.Agents;

import java.util.List;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Squad extends Agent {
    Command comm;
    List<Soldier> squadSoldiers;

    public Squad(){
        squadSoldiers = new List<Soldier>();
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
