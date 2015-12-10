package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Enviroment.PointOfTerrain;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.SquadType;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;


import java.util.List;

/**
 * Created by Szymon on 2015-12-07.
 */
public class CommanderBehaviour extends CyclicBehaviour {

    Commander comm;

    public CommanderBehaviour(Commander comm){
        this.comm = comm;
    }

    @Override
    protected void action(){
        Squad[] squads = comm.getBattle().getSquads();
        for(Squad s:squads){
            if (s.getTeam() == comm.getTeam()) {
                if(s.squadType == SquadType.Archer) {

                    /////// move to the best (highest) position in range (10% of the map w and h)
                    double highestPos = 0;
                    int[] highestPosCoord = new int[2];

                    int moveRangeX = Double.valueOf(comm.getMap().X*0.1).intValue();
                    int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()-moveRangeX).intValue();
                    if(startRangeX < 0){
                        startRangeX = 0;
                    }

                    int moveRangeY = Double.valueOf(comm.getMap().Y*0.1).intValue();
                    int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()-moveRangeX).intValue();
                    if(startRangeY < 0){
                        startRangeY = 0;
                    }
                    for(int i = startRangeX; i < startRangeX + (2*moveRangeX); i++){
                        try{
                            PointOfTerrain[] pointOfTerrains = comm.getMap().Terrain[i];
                        } catch (IndexOutOfBoundsException e){
                            continue;
                        }
                        for(int j = startRangeY; j < startRangeY + (2*moveRangeY); j++) {
                            try{
                                double height = comm.getMap().Terrain[i][j].getHeight();
                                if(highestPos < height){
                                    highestPos = height;
                                    highestPosCoord[0] = i;
                                    highestPosCoord[1] = j;
                                }
                            } catch (IndexOutOfBoundsException e){
                                continue;
                            }
                        }
                    }
                    Command archerHighestPos = new Command(CommandType.MOVEMENT);
                    archerHighestPos.setCoordToMove(highestPosCoord[0], highestPosCoord[1]);
                    ///// END OF HIGHEST POS FOR ARCHER

                }
            }
            System.out.println(SquadHelper.getMiddlePointOfSquad(s));

        }
    }
}
