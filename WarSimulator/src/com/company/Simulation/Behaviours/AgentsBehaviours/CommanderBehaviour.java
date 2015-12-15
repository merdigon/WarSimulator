package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Enviroment.PointOfTerrain;
import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Agents.Squads.SquadType;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;
import com.company.Simulation.Teams;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommanderBehaviour extends CyclicBehaviour {

    Commander comm;

    public CommanderBehaviour(Commander comm) {
        this.comm = comm;
    }

    @Override
    protected void action() {
        Squad[] squads = comm.getBattle().getSquads();
        for (Squad s : squads) {
            if (s.getTeam() == comm.getTeam() && s.checkIfAlive()) {

                if (s.squadType == SquadType.Archer) {

                    ///// ARCHER RUN AWAY FROM ENEMY (10% of map w and h)
                    Squad runFrom = archerRunAwayFromEnemy(s);
                    if (runFrom != null) {
                        Command archerRunAway = new Command(CommandType.BACK);
                        archerRunAway.setSquad(runFrom);
                        s.setCommand(archerRunAway);
                        continue;
                    }
                    ///// END OF ARCHER RUN AWAY FROM ENEMY

                    ///// ARCHER MERGE
                    int HPOfSquad = SquadHelper.getAverageHPofSquad(s);
                    if (HPOfSquad < 15 && HPOfSquad > 0) {
                        Squad archerMerge = regroup(s);
                        if (archerMerge != null) {
                            Command archerMergeCommand = new Command(CommandType.MERGE);
                            archerMergeCommand.setSquad(s);
                            s.setCommand(archerMergeCommand);
                            continue;
                        }
                    }
                    ///// END OF ARCHER MERGE

                    ///// move to the best (highest) position in range (10% of the map w and h)
                    if (!isEnemyNear(s, 0.3, 0.3)) {
                        int[] highestPosCoord = lookForHighestPos(s);
                        if (highestPosCoord != null) {
                            if (!isInTheSamePos(highestPosCoord, s)) {
                                Command archerHighestPos = new Command(CommandType.MOVEMENT);
                                archerHighestPos.setCoordToMove(highestPosCoord[0], highestPosCoord[1]);
                                s.setCommand(archerHighestPos);
                                continue;
                            }
                        }
                    }
                    ///// END OF HIGHEST POS FOR ARCHER

                    ///// ARCHER ATTACK (50% of map w and h), when detected calvary charging -> focus fire on it
                    Squad toAttack = archerAttack(s);
                    if (toAttack != null) {
                        Command archerAttackCommand = new Command(CommandType.ATTACK);
                        archerAttackCommand.setSquad(toAttack);
                        s.setCommand(archerAttackCommand);
                        continue;
                    }
                    ///// END OF ARCHER ATTACK


                } else if (s.squadType == SquadType.Warrior) {
                    ///// WHEN CALVARY CHARGES
                    if (warriorCalvaryCharge(s)) {
                        Command warriorCalvaryChargeCommand = new Command(CommandType.HOLD_POSSITION);
                        s.setCommand(warriorCalvaryChargeCommand);
                        continue;
                    }
                    ///// END WHEN CALVARY CHARGES

                    ///// WARRIOR ATTACK
                    Squad toAttack = warriorAttack(s);
                    if (toAttack != null) {
                        Command warriorAttack = new Command(CommandType.ATTACK);
                        warriorAttack.setSquad(toAttack);
                        s.setCommand(warriorAttack);
                        continue;
                    }
                    ///// END WARRIOR ATTACK

                    ///// WARRIOR MERGE
                    int HPOfSquad = SquadHelper.getAverageHPofSquad(s);
                    if (HPOfSquad < 15 && HPOfSquad > 0) {
                        Squad warriorMerge = regroup(s);
                        if (warriorMerge != null) {
                            Command warriorMergeCommand = new Command(CommandType.MERGE);
                            warriorMergeCommand.setSquad(s);
                            s.setCommand(warriorMergeCommand);
                            continue;
                        }
                    }
                    ///// END WARRIOR MERGE
                } else if (s.squadType == SquadType.Cavalry) {
                    ///// CAVALRY MERGE
                    int HPOfSquad = SquadHelper.getAverageHPofSquad(s);
                    if (HPOfSquad < 15 && HPOfSquad > 0) {
                        Squad cavalryMerge = regroup(s);
                        if (cavalryMerge != null) {
                            Command cavalryMergeCommand = new Command(CommandType.MERGE);
                            cavalryMergeCommand.setSquad(s);
                            s.setCommand(cavalryMergeCommand);
                            continue;
                        }
                    }
                    ///// END CAVALRY MERGE

                    ///// CAVALRY CHARGE
                    Squad squadToCharge = cavalryChargeAt(s);
                    Command cavalryChargeCommand = new Command(CommandType.CHARGE);
                    cavalryChargeCommand.setSquad(squadToCharge);
                    s.setCommand(cavalryChargeCommand);
                    ///// END OF CAVALRY CHARGE
                }
            }
        }
    }

    private int[] lookForHighestPos(Squad s) {
        double highestPos = 99999999;
        int[] highestPosCoord = {99999999, 99999999};

        int[] range = startStopRange(s, 0.1, 0.1);
        for (int i = range[0]; i < range[1]; i++) {
            try {
                PointOfTerrain[] pointOfTerrains = comm.getMap().Terrain[i];
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            for (int j = range[2]; j < range[3]; j++) {
                try {
                    double height = comm.getMap().Terrain[i][j].getHeight();
                    if (highestPos > height) {
                        highestPos = height;
                        highestPosCoord[0] = i;
                        highestPosCoord[1] = j;
                    }
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        if (highestPosCoord[0] == 99999999 && highestPosCoord[1] == 99999999)
            return null;
        return highestPosCoord;
    }

    private Squad archerAttack(Squad s) {
        int[] range = startStopRange(s, 0.5, 0.5);
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToAttack = new LinkedList<>();
        for (Squad enemySquad : squads) {
            if (enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                if (enemyX - range[0] >= 0 && enemyX - range[1] <= 0 && enemyY - range[2] >= 0 && enemyY - range[3] <= 0) {
                    if (enemySquad.squadType == SquadType.Cavalry && enemySquad.getCommand().getCommType() == CommandType.CHARGE) {
                        return enemySquad;
                    }
                    squadToAttack.add(enemySquad);
                }
            }
        }
        return getClosest(squadToAttack, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private boolean isEnemyNear(Squad s, double x, double y) {
        int[] range = startStopRange(s, x, y);
        Squad[] squads = comm.getBattle().getSquads();
        for (Squad enemySquad : squads) {
            if (enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                if (enemyX - range[0] >= 0 && enemyX - range[1] <= 0 && enemyY - range[2] >= 0 && enemyY - range[3] <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private Squad archerRunAwayFromEnemy(Squad s) {
        int[] range = startStopRange(s, 0.1, 0.1);
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToRunAway = new LinkedList<>();
        for (Squad enemySquad : squads) {
            if (enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                if (enemyX - range[0] >= 0 && enemyX - range[1] <= 0 && enemyY - range[2] >= 0 && enemyY - range[3] <= 0) {
                    squadToRunAway.add(enemySquad);
                }
            }
        }
        return getClosest(squadToRunAway, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad regroup(Squad s) {
        int[] range = startStopRange(s, 0.05, 0.05);
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToMerge = new LinkedList<>();
        for (Squad alliedSquad : squads) {
            if (alliedSquad.getTeam() == s.getTeam() && alliedSquad != s && alliedSquad.squadType == s.squadType
                    && alliedSquad.checkIfAlive()) {
                double allyX = SquadHelper.getMiddlePointOfSquad(alliedSquad).getX();
                double allyY = SquadHelper.getMiddlePointOfSquad(alliedSquad).getY();
                if (allyX - range[0] >= 0 && allyX - range[1] <= 0 && allyY - range[2] >= 0 && allyY - range[3] <= 0) {
                    squadToMerge.add(alliedSquad);
                }
            }
        }
        return getClosest(squadToMerge, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private boolean warriorCalvaryCharge(Squad s) {
        Squad[] squads = comm.getBattle().getSquads();
        for (Squad enemySquad : squads) {
            if(enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                double enemyX = SquadHelper.getMiddlePointOfSquad(enemySquad).getX();
                double enemyY = SquadHelper.getMiddlePointOfSquad(enemySquad).getY();
                int[] range = startStopRange(s, 0.2, 0.2);
                if (enemyX - range[0] >= 0 && enemyX - range[1] <= 0 && enemyY - range[2] >= 0 && enemyY - range[3] <= 0) {
                    try {
                        if (enemySquad.squadType == SquadType.Cavalry &&
                                enemySquad.getCommand().getCommType() == CommandType.CHARGE
                                && enemySquad.getCommand().getSquad() == s
                                && getAvgSpeed(enemySquad) > 300) {
                            return true;
                        }
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    private Squad warriorAttack(Squad s) {
        Squad[] squads = comm.getBattle().getSquads();
        List<Squad> squadToAttack = new LinkedList<>();
        for (Squad enemySquad : squads) {
            if (enemySquad.getTeam() != s.getTeam() && enemySquad.squadType == SquadType.Warrior && enemySquad.checkIfAlive()) {
                squadToAttack.add(enemySquad);
            } // TODO: what if nearest warrior squad is behind calvary?
        }
        if (squadToAttack.size() == 0) {
            for (Squad enemySquad : squads) {
                if (enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                    squadToAttack.add(enemySquad);
                }
            }
        }
        return getClosest(squadToAttack, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad cavalryChargeAt(Squad s) {
        List<Squad> returnSquad = new LinkedList<>();
        //int count_squad = 0;
        Squad[] squads = comm.getBattle().getSquads();
        for (Squad enemySquad : squads) {
            if (enemySquad.getTeam() != s.getTeam() && enemySquad.squadType == SquadType.Warrior
                    && enemySquad.getCommand().getCommType() == CommandType.ATTACK &&
                    enemySquad.getCommand().getSquad().squadType == SquadType.Warrior && enemySquad.checkIfAlive()) {
                returnSquad.add(enemySquad);
            }
        }
        if (returnSquad.size() == 0) {
            for (Squad enemySquad : squads) {
                if (enemySquad.getTeam() != s.getTeam() && enemySquad.squadType == SquadType.Warrior && enemySquad.checkIfAlive()) {
                    returnSquad.add(enemySquad);
                }
            }
        }
        if (returnSquad.size() == 0) {
            for (Squad enemySquad : squads) {
                if (enemySquad.getTeam() != s.getTeam() && enemySquad.checkIfAlive()) {
                    returnSquad.add(enemySquad);
                }
            }
        }
        return getFurthest(returnSquad, Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue(),
                Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue());
    }

    private Squad getClosest(List<Squad> squadList, int X, int Y) {
        if (squadList == null || squadList.size() == 0) {
            return null;
        }
        Squad closestSquad = null;
        double closest = 999999999;
        for (Squad s : squadList) {
            if (s.checkIfAlive()) {
                int cY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue();
                int cX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue();
                double c = Math.sqrt((cX - X) * (cX - X) + (cY - Y) * (cY - Y));
                if (closest > c) {
                    closest = c;
                    closestSquad = s;
                }
            }
        }
        return closestSquad;
    }

    private Squad getFurthest(List<Squad> squadList, int X, int Y) {
        if (squadList == null || squadList.size() == 0) {
            return null;
        }
        Squad furthestSquad = null;
        double furthest = 0;
        for (Squad s : squadList) {
            if (s.checkIfAlive()) {
                int cY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY()).intValue();
                int cX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX()).intValue();
                double c = Math.sqrt((cX - X) * (cX - X) + (cY - Y) * (cY - Y));
                if (furthest < c) {
                    furthest = c;
                    furthestSquad = s;
                }
            }
        }
        return furthestSquad;
    }


    private int[] startStopRange(Squad s, double xRange, double yRange) {
        int attackRangeX = Double.valueOf(comm.getMap().X * xRange).intValue();
        int startRangeX = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getX() - attackRangeX).intValue();
        int stopRangeX = startRangeX + (2 * attackRangeX);
        if (startRangeX < 0) {
            startRangeX = 0;
        }

        int attackRangeY = Double.valueOf(comm.getMap().Y * yRange).intValue();
        int startRangeY = Double.valueOf(SquadHelper.getMiddlePointOfSquad(s).getY() - attackRangeY).intValue();
        int stopRangeY = startRangeY + (2 * attackRangeY);
        if (startRangeY < 0) {
            startRangeY = 0;
        }
        return new int[]{startRangeX, stopRangeX, startRangeY, stopRangeY};
    }

    private int countSoldiersInSquad(Squad s) {
        int count = 0;
        for (Soldier so : s.getSoldiers()) {
            if (so.getHp() > 0) {
                count++;
            }
        }
        return count;
    }

    private double getAvgSpeed(Squad s) {
        int count = 0;
        int sum = 0;
        for(Soldier sol:s.getSoldiers()){
            if(sol.getHp() > 0) {
                count++;
                sum+=((Cavalry)sol).getVelocity();
            }
        }
        if(count == 0){
            return 500;
        } else return (sum/count);
    }

    private boolean isInTheSamePos(int [] coords, Squad s) {
        return coords[0] - 3 <= SquadHelper.getMiddlePointOfSquad(s).getX() && coords[0] + 3 >= SquadHelper.getMiddlePointOfSquad(s).getX()
                && coords[1] - 3 <= SquadHelper.getMiddlePointOfSquad(s).getY() && coords[1] + 3 >= SquadHelper.getMiddlePointOfSquad(s).getY();
    }
}
