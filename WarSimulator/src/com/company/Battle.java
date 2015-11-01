package com.company;

import com.company.Enviroment.Map;
import com.company.Gui.GuiCreator;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.ArcherSquad;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.WarriorSquad;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Battle {

    Map terrainMap;
    Squad[] squads;
    GuiCreator gui;

    public Battle(){
        terrainMap = new Map();
        squads = new Squad[4];
        gui = new GuiCreator();
    }

    public void init(){
        squads[0] = new ArcherSquad(Teams.BLUE, terrainMap);
        squads[1] = new WarriorSquad(Teams.BLUE, terrainMap);
        squads[2] = new ArcherSquad(Teams.RED, terrainMap);
        squads[3] = new WarriorSquad(Teams.RED, terrainMap);
        squads[0].setSquad(5,5,5,5);
        squads[1].setSquad(20,5,10,5);
        squads[2].setSquad(5,40,5,5);
        squads[3].setSquad(20,40,10,5);
    }

    public void start(){
        init();
        while(true){
            lifeCycle();
        }
    }

    public void lifeCycle(){
        for(Squad squad : squads)
        {
            for(Soldier sold : squad.getSoldiers())
            {
                sold.executeBehaviours();
            }
        }


    }
}
