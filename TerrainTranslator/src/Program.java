import java.util.Scanner;


public class Program {

	    public static void main(String[] args) {
	        TerrainTranslator terrTrans = new TerrainTranslator();
	        terrTrans.countTerrainData();
	        terrTrans.printTerrain();
	        terrTrans.saveImage();
	        System.out.println("Done");
	        (new Scanner(System.in)).nextLine();
	    }
}
