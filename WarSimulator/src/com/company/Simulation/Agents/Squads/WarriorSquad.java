package com.company.Simulation.Agents.Squads;

import com.company.Battle;
import com.company.Enviroment.Map;
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Soldiers.Warrior;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-31.
 */
public class WarriorSquad extends Squad {

    public WarriorSquad(Teams team, Map terrain, Battle battle) {
        super(team, terrain, battle);
    }

    @Override
    public void executePhysic() {

    }

    @Override
    public void setCommand(){

        if(team == Teams.BLUE){
            comm = null;
            for(Soldier sold : battle.squads[3].getSoldiers()){
                if(sold.getStatus())
                {
                    comm = new Command(CommandType.MOVEMENT);
                    comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                    break;
                }
            }
            if(comm==null){
                for(Soldier sold : battle.squads[2].getSoldiers()){
                    if(sold.getStatus())
                    {
                        comm = new Command(CommandType.MOVEMENT);
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
                    comm = new Command(CommandType.MOVEMENT);
                    comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                    break;
                }
            }
            if(comm==null)
            {
                for(Soldier sold : battle.squads[0].getSoldiers()){
                    if(sold.getStatus())
                    {
                        comm = new Command(CommandType.MOVEMENT);
                        comm.setPossition(new Coord(sold.getCoord().getX(), sold.getCoord().getY()));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY){
        Warrior sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Warrior(this);
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
    }
}
