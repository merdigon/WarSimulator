package com.company.Enviroment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Szymon on 2015-10-15.
 */
public class TerrainTranslator extends Component{

    BufferedImage terrain;
    public double[][] terrainData;
    int mistakeMargin = 20;
    public int imgWidth;
    public int imgHeight;
    BufferedImage finalImg;

    public TerrainTranslator(){
        try {
            terrain = ImageIO.read(TerrainTranslator.class.getClassLoader().getResourceAsStream("terrain.png"));
            imgWidth = terrain.getWidth();
            imgHeight = terrain.getHeight();
            terrainData = new double[imgWidth][imgHeight];
            for(int i=0; i<terrain.getHeight(); i++)
        		for(int j=0;j<terrain.getWidth(); j++)
        			terrainData[j][i]=-1;
        }
        catch(IOException ex){
            System.out.println("Problem z otwarciem!");
        }
    }

    public int[] readPixelRGB(BufferedImage image, int x, int y)
    {    	
        int rgb = image.getRGB(x, y);
    	return new int[] {
    		    (rgb >> 16) & 0xff, //red
    		    (rgb >>  8) & 0xff, //green
    		    (rgb      ) & 0xff  //blue
    		    };
    }
    
    public void countTerrainData()
    {
    	int width =terrain.getWidth();
    	int height = terrain.getHeight();
    	for(int i=0; i<terrain.getHeight(); i++)
    	{
    		for(int j=0;j<terrain.getWidth(); j++)
    		{    			
    	    	int[] rgb = readPixelRGB(terrain, j, i);    	    	
    	    	float[] hsv = new float[3];
    	    	Color.RGBtoHSB(rgb[0],rgb[1],rgb[2],hsv);
    	    	if(hsv[0]<=0.75)
    	    		terrainData[j][i] = (double)Math.round(10000 * hsv[0]) / 100;
    		}
    	}
    }
    
    public void printTerrain()
    {
    	for(int i=0; i<terrain.getHeight(); i++)
    	{
    		for(int j=0;j<terrain.getWidth(); j++)
    		{
    			System.out.print(terrainData[j][i]+", ");
    		}
    		System.out.println();
    	}
    }

	public void saveTerrainData(String fileName)
	{
		try(PrintWriter writer = new PrintWriter(fileName+".txt", "UTF-8")) {
			for (int i = 0; i < terrain.getHeight(); i++) {
				for (int j = 0; j < terrain.getWidth(); j++) {
					writer.print(terrainData[j][i]+ ",");
				}
				writer.println();
			}
		}
		catch(IOException ex)
		{
			System.out.println("Blad zapisu do pliku!");
		}
	}
    
    public void saveImage()
    {
    	try{
	    	finalImg = new BufferedImage(terrain.getWidth(), terrain.getHeight(),
	    		    BufferedImage.TYPE_INT_RGB);
	    	for(int i=0; i<terrain.getHeight(); i++)
	    	{
	    		for(int j=0;j<terrain.getWidth(); j++)    		
	    		{
	    			double terr = terrainData[j][i];
	    			float h = (float)(terr/100.0);
	    			int rgb = Color.HSBtoRGB(h, 1, 1);
	    			finalImg.setRGB(j, i, rgb);
	    		}
	    	}
	
	    	File f = new File("final.png");
	    	ImageIO.write(finalImg, "PNG", f);
    	}
    	catch(IOException ex)
    	{
    		System.out.println("Sie wywali³o");
    	}    	
    }
}