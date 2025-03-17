package controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {
    public static String getProfileImagePath(User user) {
        if (user != null && user.getPhoto() != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getPhoto());
        } else {
            return "res/avatar.png";
        }
    }

    public static String getMessageImagePath(Message message) {
        if (message != null && message.getImage() != null) {
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(message.getImage());
        } else {
            return ""; // Pas d'image, retourne une chaîne vide
        }
    }
}
