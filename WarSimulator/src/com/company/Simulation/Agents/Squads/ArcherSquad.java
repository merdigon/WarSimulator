package com.company.Simulation.Agents.Squads;

import com.company.Battle;
import com.company.Enviroment.Map;
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.ArrowPhysic;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Archer;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;
import com.company.Simulation.Teams;

import java.util.ArrayList;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArcherSquad extends Squad{

    public ArrowPhysic arrowPhysic;

    public ArcherSquad(Teams team, Map map, Battle battle, Commander commander){
        super(team, map, battle, commander);
        arrowPhysic = new ArrowPhysic(map);
    }

    @Override
    public void executePhysic() {
        arrowPhysic.executeBehaviours();
    }
    /*
    @Override
    public void setCommand() {
        if(team == Teams.BLUE){
            comm = null;
            for(Soldier sold : battle.squads[3].getSoldiers()){
                if(sold.getStatus())
                {
                    comm = new Command(CommandType.ATTACK);
                    comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                    break;
                }
            }
            if(comm==null){
                for(Soldier sold : battle.squads[2].getSoldiers()){
                    if(sold.getStatus())
                    {
                        comm = new Command(CommandType.ATTACK);
                        comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                        break;
                    }
                }
            }

        }
        if(team == Teams.RED){
            comm = null;
            for(Soldier sold : battle.squads[1].getSoldiers()){
                if(sold.getStatus()) {
                    comm = new Command(CommandType.ATTACK);
                    comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                    break;
                }
            }
            if(comm==null)
            {
                for(Soldier sold : battle.squads[0].getSoldiers()){
                    if(sold.getStatus())
                    {
                        comm = new Command(CommandType.ATTACK);
                        comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                        break;
                    }
                }
            }
        }
    }*/

    @Override
    public ArcherSquad setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY){
        Archer sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Archer(this);
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
        return this;
    }
}