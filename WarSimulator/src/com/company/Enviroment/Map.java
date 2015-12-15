package com.company.Enviroment;


import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.SquadType;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Map {
    //public static int WidthX = 200;
    //public static int HeightY = 200;
    public PointOfTerrain[][] Terrain;
    public int X;
    public int Y;
    TerrainTranslator terrTrans;

    public Map(){
        terrTrans = new TerrainTranslator();
        terrTrans.countTerrainData();
        double[][] terrainHeight =  terrTrans.terrainData;
        X = terrTrans.imgWidth;
        Y = terrTrans.imgHeight;
        Terrain = new PointOfTerrain[X][Y];
        for(int i=0;i<terrTrans.imgWidth;i++)
        {
            for(int j=0;j<terrTrans.imgHeight;j++)
            {
                Terrain[i][j] = new PointOfTerrain(terrainHeight[i][j], KindOfTerrain.NORMAL);
            }
        }
    }

    public synchronized boolean moveSoldier(int x1, int y1, int x2, int y2)
    {
        if(x2 >= X || x1 >= X || y1 >= Y || y2 >= Y || x2 < 0 ||  x1 < 0 || y1 < 0 || y2 < 0)
            return false;

        if(Terrain[x1][y1].getSoldier() == null)
            return false;

        if(Terrain[x2][y2].getSoldier()!=null){
            if(Terrain[x1][y1].getSoldier().getSquad().squadType == SquadType.Cavalry){
                Cavalry cav = (Cavalry)Terrain[x1][y1].getSoldier();
                Soldier aim = Terrain[x2][y2].getSoldier();
                if(aim.getSquad().squadType != SquadType.Cavalry) {
                    if (aim.getSquad().getTeam() != cav.getSquad().getTeam()) {
                        if (cav.getVelocity() < 400) {
                            double hpToTake = (110 * (400 - cav.getVelocity())) / 275;
                            if (aim.getHp() < hpToTake) {
                                cav.setVelocity((aim.getHp() * 275) / 110);
                                aim.setHp(-10);
                                aim.killSoldier();
                                changeSoldierPoss(x1, y1, x2, y2);
                                return true;
                            } else {
                                aim.changeHp((int) hpToTake);
                                cav.setVelocity(500);
                            }
                        }
                    }
                }
            }
            return false;
        }

        changeSoldierPoss(x1, y1, x2, y2);

        return true;
    }

    private synchronized void changeSoldierPoss(int x1, int y1, int x2, int y2)
    {
        Soldier tmpSold;
        tmpSold = Terrain[x1][y1].getSoldier();
        Terrain[x2][y2].setSoldier(tmpSold);
        Terrain[x1][y1].setSoldier(null);
        tmpSold.setCoord(new Coord(x2, y2));
    }

    public Soldier getSoldierOnPosition(int x, int y){
        return Terrain[x][y].getSoldier();
    }

    public void clearPosition(int x, int y){
        Terrain[x][y].setSoldier(null);
    }

    public void putSoldierOnPosition(Soldier sold, int x, int y) {
        Terrain[x][y].setSoldier(sold);
        sold.setCoord(new Coord(x,y));
    }
}

