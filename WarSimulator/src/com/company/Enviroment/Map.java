package com.company.Enviroment;


/**
 * Created by Szymon on 2015-10-16.
 */
public class Map {
    //public static int WidthX = 200;
    //public static int HeightY = 200;
    PointOfTerrain[][] Terrain;
    TerrainTranslator terrTrans;

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

class PointOfTerrain{
    double height;
    KindOfTerrain kindOfTerrain;
    public PointOfTerrain(double height, KindOfTerrain kOfTerr)
    {
        this.height = height;
        kindOfTerrain = kOfTerr;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setTerrainKind(KindOfTerrain kndOfTerr){
        this.kindOfTerrain = kndOfTerr;
    }

    public double getHeight(){
        return this.height;
    }

    public KindOfTerrain getTerrainKind(){
        return this.kindOfTerrain;
    }
}

