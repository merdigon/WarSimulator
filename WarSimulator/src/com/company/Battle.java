package com.company;

import com.company.Enviroment.Map;
import com.company.Gui.GuiCreator;
import com.company.Simulation.Agents.ArrowPhysic;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.*;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Battle {

    Map terrainMap;
    public Commander[] commanders;
    GuiCreator gui;
    Squad[] squads;
    long lastSoldiersCycle;
    long lastCycle;


    public Battle(){
        terrainMap = new Map();
        commanders = new Commander[2];
        gui = new GuiCreator();
        lastSoldiersCycle = System.nanoTime();
        lastCycle = System.nanoTime();
    }

    public void init(){
        commanders[0] = new Commander(Teams.BLUE, terrainMap, this);
        commanders[1] = new Commander(Teams.RED, terrainMap, this);
        createBothSquads();
    }

    protected void createBothSquads(){
        squads = new Squad[5];
        squads[0] = new ArcherSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[1] = new WarriorSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[2] = new ArcherSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[3] = new WarriorSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[4] = new CavalrySquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[0].setSquad(5,5,5,5);
        squads[1].setSquad(20,5,9,5);
        squads[2].setSquad(5,30,5,5);
        squads[3].setSquad(20,30,9,5);
        squads[4].setSquad(31,30,9,5);
    }

    public void start(){
        init();
        while(true){
            lifeCycle();
        }
    }

    public void lifeCycle() {
        lastCycle = System.nanoTime();

        for (Commander comm : commanders) {
            comm.executeBehaviours();
        }

        for (Squad squad : squads) {
            squad.executeBehaviours();
            if(squad.squadType == SquadType.Archer)
                ((ArcherSquad)squad).executePhysic();
        }

        if (((System.nanoTime() - lastSoldiersCycle) / 1000000) > 1000) {
            lastSoldiersCycle = System.nanoTime();

            for (Squad squad : squads) {

                for (Soldier sold : squad.getSoldiers()) {
                    if (sold.getStatus())
                        sold.executeBehaviours();
                }
            }
            gui.changeGrid(squads[0].terrainMap);
        }

        for (Squad squad : squads) {
            if (squad.squadType == SquadType.Cavalry) {
                for (Soldier sld : squad.getSoldiers()) {
                    ((Cavalry) sld).addTimeAfterLastThinking((System.nanoTime() - lastCycle) / 1000000);
                    if (((Cavalry) sld).getTimeAfterLastThinking() >= ((Cavalry) sld).getVelocity()) {
                        sld.executeBehaviours();
                        ((Cavalry) sld).setTimeAfterLastThinking(0);
                    }
                }
            }
        }
    }
    public Squad[] getSquads(){
        return this.squads;
    }
}
