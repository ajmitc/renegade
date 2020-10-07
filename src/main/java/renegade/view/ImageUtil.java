package renegade.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ImageUtil {
    private static final Logger logger = Logger.getLogger(ImageUtil.class.getName());

    private static Map<String, Image> cache = new HashMap<>();

    public static Image get(String filename){
        if (cache.containsKey(filename))
            return cache.get(filename);

        try{
            Image image = ImageIO.read(new File("image/" + filename));
            cache.put(filename, image);
            return image;
        }
        catch (Exception e){
            logger.severe("Unable to load image: " + e);
        }
        return null;
    }

    private ImageUtil() {

    }
}
