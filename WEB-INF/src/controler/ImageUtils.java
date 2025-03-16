package controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] imageToBytes(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
}
