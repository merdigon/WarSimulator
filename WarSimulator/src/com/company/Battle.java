package com.company;

import com.company.Enviroment.Map;
import com.company.Gui.GuiCreator;
import com.company.Simulation.Agents.ArrowPhysic;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.ArcherSquad;
import com.company.Simulation.Agents.Squads.CavalrySquad;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.WarriorSquad;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Battle {

    Map terrainMap;
    public Commander[] commanders;
    GuiCreator gui;
    Squad[] squads;
    long lastCycle;


    public Battle(){
        terrainMap = new Map();
        commanders = new Commander[2];
        gui = new GuiCreator();
        lastCycle = System.nanoTime();
    }

    public void init(){
        commanders[0] = new Commander(Teams.BLUE, 2, terrainMap);
        commanders[1] = new Commander(Teams.RED, 2, terrainMap);
        createBothSquads();
    }

    protected void createBothSquads(){
        squads = new Squad[4];
        squads[0] = new ArcherSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[1] = new WarriorSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[2] = new ArcherSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[3] = new WarriorSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[0].setSquad(5,5,5,5);
        squads[1].setSquad(20,5,10,5);
        squads[2].setSquad(5,30,5,5);
        squads[3].setSquad(20,30,10,5);
    }

    public void start(){
        init();
        while(true){
            lifeCycle();
        }
    }

    public void lifeCycle(){
        if(((System.nanoTime()-lastCycle)/1000000) > 1000) {
            lastCycle = System.nanoTime();

            //for(Squad squad : squads) {
            //    squad.setCommand();
            //}

            for (Squad squad : squads) {
                squad.giveCommand();

                for (Soldier sold : squad.getSoldiers()) {
                    if(sold.getStatus())
                        sold.executeBehaviours();
                }
            }
            gui.changeGrid(squads[0].terrainMap);
        }

        for (Squad squad : squads)
            squad.executePhysic();
    }
}
