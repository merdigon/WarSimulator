package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Enviroment.PointOfTerrain;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.SquadType;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

import java.util.LinkedList;
import java.util.List;

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

                    ///// ARCHER RUN AWAY FROM ENEMY (10% of map w and h)
                    Squad runFrom = archerRunAwayFromEnemy(s);
                    if(runFrom != null) {
                        Command archerRunAway = new Command(CommandType.BACK);
                        archerRunAway.setSquad(runFrom);
                        s.setCommand(archerRunAway);
                        continue;
                    }
                    ///// END OF ARCHER RUN AWAY FROM ENEMY

                    ///// ARCHER MERGE
                    int HPOfSquad = SquadHelper.getAverageHPofSquad(s);
                    if(HPOfSquad < 15 && HPOfSquad > 0) {
                        Squad archerMerge = regroup(s);
                        if (archerMerge != null) {
                            Command archerMergeCommand = new Command(CommandType.MERGE);
                            archerMergeCommand.setSquad(s);
                            s.setCommand(archerMergeCommand);
                            continue;
                        }
                    }
                    ///// END OF ARCHER MERGE

                    /////// move to the best (highest) position in range (10% of the map w and h)
                    int[] highestPosCoord = lookForHighestPos(s);
                    Command archerHighestPos = new Command(CommandType.MOVEMENT);
                    archerHighestPos.setCoordToMove(highestPosCoord[0], highestPosCoord[1]);
                    s.setCommand(archerHighestPos);
                    ///// END OF HIGHEST POS FOR ARCHER

                    ///// ARCHER ATTACK (50% of map w and h)
                    Squad toAttack = archerAttack(s);
                    if(toAttack != null) {
                        Command archerAttackCommand = new Command(CommandType.ATTACK);
                        archerAttackCommand.setSquad(toAttack);
                        s.setCommand(archerAttackCommand);
                        continue;
                    }
                    ///// END OF ARCHER ATTACK
                }
            }
        }
    }

    private int[] lookForHighestPos(Squad s){
        double highestPos = 0;
        int[] highestPosCoord = new int[2];

        int moveRangeX = Double.valueOf(comm.getMap().X*0.1).intValue();
        int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()-moveRangeX).intValue();
        if(startRangeX < 0){
            startRangeX = 0;
        }

        int moveRangeY = Double.valueOf(comm.getMap().Y*0.1).intValue();
        int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()-moveRangeY).intValue();
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
        return highestPosCoord;
    }

    private Squad archerAttack(Squad s){
        int attackRangeX = Double.valueOf(comm.getMap().X*0.5).intValue();
        int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()-attackRangeX).intValue();
        int stopRangeX = startRangeX + (2*attackRangeX);
        if(startRangeX < 0){
            startRangeX = 0;
        }

        int attackRangeY = Double.valueOf(comm.getMap().Y*0.5).intValue();
        int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()-attackRangeY).intValue();
        int stopRangeY = startRangeX + (2*attackRangeX);
        if(startRangeY < 0){
            startRangeY = 0;
        }
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToAttack = new LinkedList<>();
        for(Squad enemySquad:squads){
            if(enemySquad.getTeam() != s.getTeam()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                if(enemyX - startRangeX <= 0 && enemyX - stopRangeX >= 0 && enemyY - startRangeY >=0 && enemyY - stopRangeY <= 0) {
                    squadToAttack.add(enemySquad);
                }
            }
        }
        return getClosest(squadToAttack, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad archerRunAwayFromEnemy(Squad s){
        int runRangeX = Double.valueOf(comm.getMap().X*0.1).intValue();
        int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()-runRangeX).intValue();
        int stopRangeX = startRangeX + (2*runRangeX);
        if(startRangeX < 0){
            startRangeX = 0;
        }

        int attackRangeY = Double.valueOf(comm.getMap().Y*0.1).intValue();
        int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()-attackRangeY).intValue();
        int stopRangeY = startRangeX + (2*runRangeX);
        if(startRangeY < 0){
            startRangeY = 0;
        }
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToRunAway = new LinkedList<>();
        for(Squad enemySquad:squads){
            if(enemySquad.getTeam() != s.getTeam()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                if(enemyX - startRangeX <= 0 && enemyX - stopRangeX >= 0 && enemyY - startRangeY >=0 && enemyY - stopRangeY <= 0) {
                    squadToRunAway.add(enemySquad);
                }
            }
        }
        return getClosest(squadToRunAway, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad regroup(Squad s){
        int regroupRangeX = Double.valueOf(comm.getMap().X*0.1).intValue();
        int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()-regroupRangeX).intValue();
        int stopRangeX = startRangeX + (2*regroupRangeX);
        if(startRangeX < 0){
            startRangeX = 0;
        }

        int regroupRangeY = Double.valueOf(comm.getMap().Y*0.1).intValue();
        int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()-regroupRangeY).intValue();
        int stopRangeY = startRangeX + (2*regroupRangeX);
        if(startRangeY < 0){
            startRangeY = 0;
        }
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToMerge = new LinkedList<>();
        for(Squad alliedSquad:squads){
            if(alliedSquad.getTeam() == s.getTeam() && alliedSquad != s && alliedSquad.squadType == s.squadType) {
                double allyX = SquadHelper.getMiddlePointOfSquad(alliedSquad).getX();
                double allyY = SquadHelper.getMiddlePointOfSquad(alliedSquad).getY();
                if(allyX - startRangeX <= 0 && allyX - stopRangeX >= 0 && allyY - startRangeY >=0 && allyY - stopRangeY <= 0) {
                    squadToMerge.add(alliedSquad);
                }
            }
        }
        return getClosest(squadToMerge, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad getClosest(List<Squad> squadList, int X, int Y) {
        if(squadList.size() == 0){
            return null;
        }
        Squad closestSquad = null;
        double closest = 999999999;
        for(Squad s:squadList) {
            int cY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue();
            int cX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue();
            double c = Math.sqrt((cX - X)*(cX - X) + (cY - Y)*(cY - Y));
            if(closest > c) {
                closest = c;
                closestSquad = s;
            }
        }
        return closestSquad;
    }
}
