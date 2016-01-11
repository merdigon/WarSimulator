package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.ArrowHitBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.WakerBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowPhysic extends Agent {

    //squad �uczniczy albo sami �ucznicy wywo�uj� setNextArrowAttack (wystrzelenie strza�y w powietrze)
    private static int arrowSpeed = 25; //ilosc milisekund na 1 kratke
    public ArrowPhysic(Map map){
        simMap = map;
    }

    int arrowFrequency=0;

    Map simMap;
    public void setNewArrowAttack(int sx, int sy, final int x, final int y){

        //doda� obliczanie czasu lotu strza�y

        int arrowTimeout = (int)(Math.sqrt(Math.pow(x - sx, 2) + Math.pow(y - sy, 2)) * arrowSpeed);

        if(arrowFrequency == 10) {
            if (x < simMap.X && x >= 0 && y < simMap.Y && y >= 0) {
                simMap.Terrain[x][y].setArrowHit();
                arrowFrequency = 0;
            }
        }
        else
            arrowFrequency++;

        addBehaviour(
                new WakerBehaviour(arrowTimeout) {
                    public void action() {
                        Soldier sld;
                        if (x < simMap.X && x >= 0 && y < simMap.Y && y >= 0) {
                            if ((sld = simMap.Terrain[x][y].getSoldier()) != null) {
                                sld.addBehaviour(new ArrowHitBehaviour(sld));
                            }
                        }
                        //else
                          //  System.out.println("Poszło");

                    }
                }
        );
    }

    //doda� metody obliczaj�ce lot strza�
}
