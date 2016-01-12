package com.company.Gui;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.company.Battle;
import com.company.Enviroment.Map;
import com.company.Enviroment.PointOfTerrain;
import com.company.Main;
import com.company.Simulation.Agents.Squads.SquadType;
import com.company.Simulation.Teams;
import com.company.Simulation.Agents.Soldiers.Soldier;

public class GuiCreator extends JPanel {

    public static final Color RED_ARMY_WARRIOR = new Color(255, 1, 0);
    public static final Color RED_ARMY_ARCHER = new Color(255, 2, 0);
    public static final Color RED_ARMY_CAVALRY = new Color(255, 3, 0);
    public static final Color BLUE_ARMY_WARRIOR = new Color(0, 1, 255);
    public static final Color BLUE_ARMY_ARCHER = new Color(0, 2, 255);
    public static final Color BLUE_ARMY_CAVALRY = new Color(0, 3, 255);

    public static final Color[] ARMIES = {RED_ARMY_WARRIOR, RED_ARMY_ARCHER, RED_ARMY_CAVALRY,
            BLUE_ARMY_WARRIOR, BLUE_ARMY_ARCHER, BLUE_ARMY_CAVALRY};

    public static final int NUM_ROWS = 80;
    public static final int NUM_COLS = 100;

    private final Color[][] armyGrid;
    private final boolean[][] arrowGrid;

    private ButtonPanel buttonPanel;

    private Battle battle;

    BufferedImage image;

    public GuiCreator(Battle battle) {
        this.battle = battle;
        this.armyGrid = new Color[NUM_ROWS][NUM_COLS];
        this.arrowGrid = new boolean[NUM_ROWS][NUM_COLS];
        JFrame frame = new JFrame("WarSimulator");

        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // eastPanel.add(b1);
        // eastPanel.add(b2);

        frame.add(this);
        buttonPanel = new ButtonPanel(this, battle);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);

        image = null;
        try {
            ClassLoader loader = Main.class.getClassLoader();
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File subDir_1 = new File(s, "WarSimulator");
            File subDir = new File(subDir_1, "out");
            File subDir2 = new File(subDir, "production");
            File file = new File(subDir2, "terrain.png");
            System.out.println(s);
            image = ImageIO.read(file);
        } catch (IOException ex) {
            System.out.print("Nie wczytało w GuiCreator");
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File subDir3 = new File(s, "out");
            File subDir4 = new File(subDir3, "production");
            File file2 = new File(subDir4, "terrain.png");
            try {
                image = ImageIO.read(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        createMap();

    }

    private void createMap() {

//		Random r = new Random();
//		for (int i = 0; i < NUM_ROWS; i++) {
//			for (int j = 0; j < NUM_COLS; j++) {
//				Color randomColor = ARMIES[r.nextInt(2)];
//				this.armyGrid[i][j] = randomColor;
//			}
//		}
        setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    }

    public void changeGrid(Map m) {
        // Clear grid
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                this.armyGrid[i][j] = null;
                this.arrowGrid[i][j] = false;
            }
        }

        //Update labels
        buttonPanel.setRedArchersLabel(battle.getNumberOfAlive(Teams.RED, SquadType.Archer));
        buttonPanel.setRedWarriorsLabel(battle.getNumberOfAlive(Teams.RED, SquadType.Warrior));
        buttonPanel.setRedCavalryLabel(battle.getNumberOfAlive(Teams.RED, SquadType.Cavalry));
        buttonPanel.setBlueArchersLabel(battle.getNumberOfAlive(Teams.BLUE, SquadType.Archer));
        buttonPanel.setBlueWarriorsLabel(battle.getNumberOfAlive(Teams.BLUE, SquadType.Warrior));
        buttonPanel.setBlueCavalryLabel(battle.getNumberOfAlive(Teams.BLUE, SquadType.Cavalry));

        int i = 0;
        for (PointOfTerrain[] tp : m.Terrain) {
            int j = 0;
            for (PointOfTerrain p : tp) {
                Soldier s = p.getSoldier();
                if (p.ifArrowHit()) {
                    this.arrowGrid[i][j] = true;
                }
                if (s != null) {
                    if (s.getSquad().team == Teams.RED && s.getSquad().squadType == SquadType.Warrior)
                        this.armyGrid[i][j] = ARMIES[0];
                    else if (s.getSquad().team == Teams.RED && s.getSquad().squadType == SquadType.Archer)
                        this.armyGrid[i][j] = ARMIES[1];
                    else if (s.getSquad().team == Teams.RED && s.getSquad().squadType == SquadType.Cavalry)
                        this.armyGrid[i][j] = ARMIES[2];
                    else if (s.getSquad().team == Teams.BLUE && s.getSquad().squadType == SquadType.Warrior)
                        this.armyGrid[i][j] = ARMIES[3];
                    else if (s.getSquad().team == Teams.BLUE && s.getSquad().squadType == SquadType.Archer)
                        this.armyGrid[i][j] = ARMIES[4];
                    else if (s.getSquad().team == Teams.BLUE && s.getSquad().squadType == SquadType.Cavalry)
                        this.armyGrid[i][j] = ARMIES[5];
                }
                j++;
            }
            i++;
        }
        updateUI();
    }

    @Override
    public void paintComponent(Graphics g) {
        Image scaledImage = image.getScaledInstance(getWidth(),
                getHeight(), Image.SCALE_SMOOTH);//TODO: przenieść poza painta, ale jak się tak robi to getHeight() daje 0

        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(scaledImage, 0, 0, null);
        // Draw the grid
        double rectWidth = (double) (this.getWidth()) / NUM_COLS;
        double rectHeight = (double) (getHeight()) / NUM_ROWS;

        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                double x = i * rectWidth;
                double y = j * rectHeight;
                boolean isArrow = arrowGrid[i][j];

                Color terrainColor = armyGrid[i][j];
                // if there is no color
                if (terrainColor != null) {
                    g.setColor(terrainColor);
                    Rectangle2D rect = new Rectangle2D.Double(x, y, rectWidth,
                            rectHeight);
                    g2.setPaint(terrainColor);
                    g2.fill(rect);
                    if (terrainColor == RED_ARMY_WARRIOR || terrainColor == BLUE_ARMY_WARRIOR) {
                        Rectangle2D rect2 = new Rectangle2D.Double(x + rectWidth * 0.25, y + rectHeight * 0.25, rectWidth * 0.5,
                                rectHeight * 0.5);
                        g2.setPaint(Color.BLACK);
                        g2.fill(rect2);
                    } else if (terrainColor == RED_ARMY_ARCHER || terrainColor == BLUE_ARMY_ARCHER) {
                        Ellipse2D circle = new Ellipse2D.Double(x + rectWidth * 0.2, y + rectHeight * 0.2, rectWidth * 0.4,
                                rectHeight * 0.4);
                        g2.setPaint(Color.BLACK);
                        g2.fill(circle);
                    } else if (terrainColor == RED_ARMY_CAVALRY || terrainColor == BLUE_ARMY_CAVALRY) {
                        Arc2D arc = new Arc2D.Double(x + rectWidth * 0.2, y + rectHeight * 0.2, rectWidth * 0.4,
                                rectHeight * 0.4, 30, 120, Arc2D.PIE);
                        g2.setPaint(Color.BLACK);
                        g2.fill(arc);
                    }
                }
                if (isArrow) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                    g2.drawString("x", Double.valueOf(x).intValue(), Double.valueOf(y).intValue());
                }

            }
        }
    }
/*
    public static void main(String[] args) {
		// TO TEST GUI
		 GuiCreator gui = new GuiCreator();
	}*/

}
