package com.company.Enviroment;

import com.company.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Szymon on 2015-10-15.
 */
public class TerrainTranslator extends Component {

    BufferedImage terrain;
    public double[][] terrainData;
    int mistakeMargin = 20;
    public int imgWidth;
    public int imgHeight;
    BufferedImage finalImg;

    public TerrainTranslator() {
        try {
            ClassLoader loader = Main.class.getClassLoader();
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File subDir_1 = new File(s, "WarSimulator");
            File subDir = new File(subDir_1, "out");
            File subDir2 = new File(subDir, "production");
            File file = new File(subDir2, "terrain.png");
            System.out.println(s);
            terrain = ImageIO.read(file);
        } catch (IOException ex) {
            System.out.print("Nie wczytało w GuiCreator");
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File subDir3 = new File(s, "out");
            File subDir4 = new File(subDir3, "production");
            File file2 = new File(subDir4, "terrain.png");
            try {
                terrain = ImageIO.read(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgWidth = terrain.getWidth();
        imgHeight = terrain.getHeight();
        terrainData = new double[imgWidth][imgHeight];
        for (int i = 0; i < terrain.getHeight(); i++)
            for (int j = 0; j < terrain.getWidth(); j++)
                terrainData[j][i] = -1;

    }

    public int[] readPixelRGB(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);
        return new int[]{
                (rgb >> 16) & 0xff, //red
                (rgb >> 8) & 0xff, //green
                (rgb) & 0xff  //blue
        };
    }

    public void countTerrainData() {
        int width = terrain.getWidth();
        int height = terrain.getHeight();
        for (int i = 0; i < terrain.getHeight(); i++) {
            for (int j = 0; j < terrain.getWidth(); j++) {
                int[] rgb = readPixelRGB(terrain, j, i);
                float[] hsv = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);
                if (hsv[0] <= 0.75)
                    terrainData[j][i] = (double) Math.round(10000 * hsv[0]) / 100;
            }
        }
    }

    public void printTerrain() {
        for (int i = 0; i < terrain.getHeight(); i++) {
            for (int j = 0; j < terrain.getWidth(); j++) {
                System.out.print(terrainData[j][i] + ", ");
            }
            System.out.println();
        }
    }

    public void saveTerrainData(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8")) {
            for (int i = 0; i < terrain.getHeight(); i++) {
                for (int j = 0; j < terrain.getWidth(); j++) {
                    writer.print(terrainData[j][i] + ",");
                }
                writer.println();
            }
        } catch (IOException ex) {
            System.out.println("Blad zapisu do pliku!");
        }
    }

    public void saveImage() {
        try {
            finalImg = new BufferedImage(terrain.getWidth(), terrain.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < terrain.getHeight(); i++) {
                for (int j = 0; j < terrain.getWidth(); j++) {
                    double terr = terrainData[j][i];
                    float h = (float) (terr / 100.0);
                    int rgb = Color.HSBtoRGB(h, 1, 1);
                    finalImg.setRGB(j, i, rgb);
                }
            }

            File f = new File("final.png");
            ImageIO.write(finalImg, "PNG", f);
        } catch (IOException ex) {
            System.out.println("Sie wywali�o");
        }
    }
}