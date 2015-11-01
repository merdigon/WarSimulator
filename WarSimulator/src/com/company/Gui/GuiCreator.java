package com.company.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.company.Enviroment.Map;
import com.company.Enviroment.PointOfTerrain;
import com.company.Simulation.Teams;
import com.company.Simulation.Agents.Soldiers.Soldier;

public class GuiCreator extends JPanel {

	public static final Color RED_ARMY = new Color(255, 0, 0);
	public static final Color BLUE_ARMY = new Color(0, 0, 255);

	public static final Color[] ARMIES = { RED_ARMY, BLUE_ARMY, };

	public static final int NUM_ROWS = 40;
	public static final int NUM_COLS = 50;

	private final Color[][] armyGrid;

	public GuiCreator() {
		this.armyGrid = new Color[NUM_ROWS][NUM_COLS];
		JFrame frame = new JFrame("WarSimulator");

		JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// eastPanel.add(b1);
		// eastPanel.add(b2);

		frame.add(this);
		frame.add(new ButtonPanel(this), BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
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
			}
		}

		int i = 0;
		for (PointOfTerrain[] tp : m.Terrain) {
			int j = 0;
			for (PointOfTerrain p : tp) {
				Soldier s = p.getSoldier();
				if(s != null){
					if(s.getSquad().team == Teams.RED)
						this.armyGrid[i][j] = ARMIES[0];
					else if(s.getSquad().team == Teams.BLUE)
						this.armyGrid[i][j] = ARMIES[1];
				}
				j++;
			}
			i++;
		}
		updateUI();
	}

	@Override
	public void paintComponent(Graphics g) {


		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File(
							"C:\\Users\\Szymon\\Documents\\GitHub\\WarSimulator\\WarSimulator\\out\\production\\WarSimulator\\com\\company\\terrain.png")); //TODO: insert your own abs path
		} catch (IOException ex) {
			System.out.print("Lipka");
		}
		Image scaledImage = image.getScaledInstance(this.getWidth(),
				this.getHeight(), Image.SCALE_SMOOTH);

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
				Color terrainColor = armyGrid[i][j];
				// if there is no color
				if (terrainColor == null)
					continue;
				g.setColor(terrainColor);
				Rectangle2D rect = new Rectangle2D.Double(x, y, rectWidth,
						rectHeight);
				g2.setPaint(terrainColor);
				g2.fill(rect);
			}
		}
	}
/*
	public static void main(String[] args) {
		// TO TEST GUI
		 GuiCreator gui = new GuiCreator();
	}*/

}
