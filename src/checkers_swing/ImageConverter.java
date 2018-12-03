package checkers_swing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageConverter {
    public ImageConverter(Color color1, Color color2) {

        //Normal Pawns
    createOwnImage(color1,"/pictures/white_pawn.png","/pictures/white_pawn1.png");
    createOwnImage(color2,"/pictures/white_pawn.png","/pictures/black_pawn1.png");

        //King Pawns
    createOwnImage(color1, "/pictures/white_king.png","/pictures/white_king1.png");
    createOwnImage(color2,"/pictures/white_king.png","/pictures/black_king1.png");
    }

    public void createOwnImage(Color color, String sourcePath, String destinationPath){
        BufferedImage img = null;
        BufferedImage rgbImage;
        URL sourceUrl = getClass().getResource(sourcePath);
        URL destinationUrl = getClass().getResource(destinationPath);

        try {
            img = ImageIO.read(new File(sourceUrl.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rgbImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rgbImage.getGraphics().drawImage(img, 0, 0, null);

        Color whiteColor = new Color(255, 255, 255); // Color white
        Color newColor = color;

        int white = whiteColor.getRGB();
        int myNew = newColor.getRGB();
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                if (rgbImage.getRGB(i, j) == white)
                    rgbImage.setRGB(i, j, myNew);
            }
        }
        File outputfile = new File(destinationUrl.getPath());
        try {
            ImageIO.write(rgbImage, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
