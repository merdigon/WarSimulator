package com.company.Enviroment;

import com.company.Simulation.Agents.Soldiers.Soldier;

/**
 * Created by Szymon on 2015-10-31.
 */
public class PointOfTerrain {
    double height;
    KindOfTerrain kindOfTerrain;
    Soldier soldierOnThisPlace;
    int isArrow = 0;

    public PointOfTerrain(double height, KindOfTerrain kOfTerr) {
        this.height = height;
        kindOfTerrain = kOfTerr;
    }

    public void setSoldier(Soldier sold) {
        soldierOnThisPlace = sold;
    }

    public Soldier getSoldier() {
        return soldierOnThisPlace;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTerrainKind(KindOfTerrain kndOfTerr) {
        this.kindOfTerrain = kndOfTerr;
    }

    public double getHeight() {
        return this.height;
    }

    public KindOfTerrain getTerrainKind() {
        return this.kindOfTerrain;
    }

    public void setArrowHit() { isArrow=10; }

    public boolean ifArrowHit() {
        if(isArrow>0)
        {
            isArrow--;
            return true;
        }
        return false;
    }
}