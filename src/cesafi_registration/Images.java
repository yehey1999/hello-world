/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

/**
 *
 * @author Rosalijos
 */
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class Images{
    //changeSize returns a resized ImageIcon
    public static ImageIcon changeSize(ImageIcon img, int width, int height){
        return new ImageIcon(scaleImage(img.getImage(), width, height), img.getDescription());
    }
    
    //scaleImage is a submethod in resizing ImageIcon
    private static Image scaleImage(Image image, int w, int h) {
         return image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }
    
    public static ImageIcon resizedImageBasedOnParent(ImageIcon icon, Component parent){
        Dimension size = parent.getSize();
        return Images.changeSize(icon, size.width, size.height);
    }
}