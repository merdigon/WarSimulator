package com.company.Enviroment;


import com.company.Simulation.Agents.Soldiers.Soldier;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Map {
    //public static int WidthX = 200;
    //public static int HeightY = 200;
    public PointOfTerrain[][] Terrain;
    TerrainTranslator terrTrans;

    public synchronized boolean moveSoldier(int x1, int y1, int x2, int y2)
    {
        if(Terrain[x2][y2].getSoldier()!=null)
            return false;

        Soldier tmpSold;
        tmpSold = Terrain[x1][y1].getSoldier();
        Terrain[x2][y2].setSoldier(tmpSold);
        return true;
    }

    public Map(){
        terrTrans = new TerrainTranslator();
        terrTrans.countTerrainData();
        double[][] terrainHeight =  terrTrans.terrainData;
        Terrain = new PointOfTerrain[terrTrans.imgWidth][terrTrans.imgHeight];
        for(int i=0;i<terrTrans.imgHeight;i++)
        {
            for(int j=0;j<terrTrans.imgWidth;j++)
            {
                Terrain[i][j] = new PointOfTerrain(terrainHeight[i][j], KindOfTerrain.NORMAL);
            }
        }
    }
}

