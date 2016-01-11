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

    public int getNumberOfAlive(Teams color, SquadType type){
        int soldiers = 0;
        for(Squad squad : squads){
            if(squad.team == color && squad.squadType == type){
                for(Soldier sold : squad.getSoldiers())
                {
                    if(sold.getStatus())
                        soldiers++;
                }
            }
        }
        return soldiers;
    }

    protected void createBothSquads(){
        squads = new Squad[15];
        squads[4] = new ArcherSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[1] = new WarriorSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[5] = new ArcherSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[3] = new WarriorSquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[0] = new CavalrySquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[2] = new CavalrySquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[6] = new CavalrySquad(Teams.RED, terrainMap, this, commanders[1]);
        //squads[7] = new CavalrySquad(Teams.RED, terrainMap, this, commanders[1]);
        squads[7] = new CavalrySquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[8] = new CavalrySquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[9] = new CavalrySquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[10] = new CavalrySquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[11] = new CavalrySquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[12] = new WarriorSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[13] = new WarriorSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[14] = new WarriorSquad(Teams.BLUE, terrainMap, this, commanders[0]);
        squads[0].setSquad(5, 15, 20, 5);
        squads[1].setSquad(25,5,25,5);
        squads[2].setSquad(45, 15, 20, 5);
        squads[3].setSquad(70,5,5,5);
        squads[4].setSquad(5, 5, 15, 5);
        squads[5].setSquad(55, 5, 10, 5);
        squads[6].setSquad(75, 15, 5, 8);
        //squads[7].setSquad(75, 25, 5, 8);
        squads[7].setSquad(20, 60, 6, 5);
        squads[8].setSquad(31, 60, 6, 5);
        squads[9].setSquad(42, 60, 6, 5);
        squads[10].setSquad(53, 60, 6, 5);
        squads[11].setSquad(64, 60, 6, 5);
        squads[12].setSquad(20, 70, 15, 5);
        squads[13].setSquad(35, 70, 15, 5);
        squads[14].setSquad(50, 70, 20, 5);
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

        /*for (Squad squad : squads) {
            if (squad.squadType == SquadType.Cavalry) {
                for (Soldier sld : squad.getSoldiers()) {
                    ((Cavalry) sld).addTimeAfterLastThinking((System.nanoTime() - lastCycle) / 1000000);
                    if (((Cavalry) sld).getTimeAfterLastThinking() >= ((Cavalry) sld).getVelocity()) {
                        sld.executeBehaviours();
                        ((Cavalry) sld).setTimeAfterLastThinking(0);
                    }
                }
            }
        }*/
    }
    public Squad[] getSquads(){
        return this.squads;
    }
}