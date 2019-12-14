import com.kruglikov.pointilism.index.Indexer;
import com.kruglikov.pointilism.index.LuminanceIndexer;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class runLuminance {
    public static void main(String[] argv) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("/Users/aaronkruglikov/Desktop/dog.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Indexer indexer = new LuminanceIndexer();
        indexer.Index(img);
    }
}