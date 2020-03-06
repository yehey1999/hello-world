/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rosalijos
 */
public class SarxosCamera {
    
    private Dimension ds;
    private Dimension cs;
    private Webcam wCam;
    private WebcamPanel wCamPanel;    
    private JLabel countdown = new JLabel();
    private JFrame frame;
    private JPanel pnl_CameraHolder;    
    private int index = 0;
    
    public SarxosCamera(){ }
    
    //start the thread for the video
    public boolean startVideo(JPanel pnl_CameraHolder){
        boolean isSuccessful = true;
        try{
            Thread t = new Thread(){
                @Override
                public void run(){
                    wCamPanel.start();
                }
            };
            t.setDaemon(true);
            t.start();  
        }catch(Exception e){
            e.printStackTrace();
            isSuccessful = false;
        }
        
        return isSuccessful;
    }
    
    public boolean open(){
        boolean isSuccessful = true;
        try{
            open(false);
        }catch(Exception e){
            e.printStackTrace();
            isSuccessful = false;
        }
        return isSuccessful;
    }
    
    public boolean open(boolean async){
        boolean isSuccessful = false;
        if(wCam != null){
            wCam.open(async);
            isSuccessful = true;
        }
        
        return isSuccessful;
    }
    
    public boolean close(){
        boolean isSuccessful = false;
        if(wCam != null){
            wCam.close();
            isSuccessful = true;
        }
        
        return isSuccessful;
    }
    
    public void setCameraIndex(int index){
        this.index = index;
    }
    
    public int getCameraIndex(){
        return index;
    }
    
    public WebcamPanel initializeVideo(){
        return initializeVideo(getCameraIndex());
    }
    
    public WebcamPanel initializeVideo(int index){     
        ds = new Dimension(554, 384);
        cs = WebcamResolution.VGA.getSize();
        wCam = Webcam.getWebcams().get(index);
        wCamPanel = new WebcamPanel(wCam, ds, false);
        wCamPanel.setLayout(new BorderLayout());
        wCam.setViewSize(cs);
               
        return wCamPanel;
    }
    
    public ImageIcon takeImage(String filePath){    
        ImageIcon img = null;
        try{
            ImageIO.write(wCam.getImage(), "JPG", new File(filePath));
            img = new ImageIcon(filePath);
        }catch(Exception e){
        }
        return img;
    }
    
    public ImageIcon takeImage(String filePath, int width, int height){    
        ImageIcon img = null;
        try{
            ImageIO.write(wCam.getImage(), "JPG", new File(filePath));
            img = Images.changeSize(new ImageIcon(filePath), width, height);
        }catch(Exception e){
        }
        return img;
    }
    
    public List<Webcam> getCamera(){
        return Webcam.getWebcams();
    }

    
}
