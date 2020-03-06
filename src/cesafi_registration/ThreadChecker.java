/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Rosalijos
 */

public class ThreadChecker implements Runnable {
    
    private Thread t;
    private SarxosCamera cam;
    private CameraPhoto cameraPhoto;
    private JPanel card;
    private User user;
    private Table table;
    private int threadChosen = 0;
    private Registrant registrant;
    
    public ThreadChecker(Thread t, SarxosCamera cam, JPanel card, CameraPhoto cameraPhoto, Table table, Registrant registrant){
        this.t = t;
        this.cam = cam;
        this.cameraPhoto = cameraPhoto;
        this.card = card;
        this.table = table;   
        threadChosen = 1;
        this.registrant = registrant;
    }
    
    public ThreadChecker(Thread t, SarxosCamera cam, JPanel card, CameraPhoto cameraPhoto, User user, Registrant registrant){
        this.t = t;
        this.cam = cam;
        this.cameraPhoto = cameraPhoto;
        this.card = card;
        this.user = user;
        threadChosen = 2;
        this.registrant = registrant;
    }
    
    @Override
    public void run() {
        try{
            while(t.isAlive()){};
        }catch(Exception e){}
       
       String path = "Photos\\" + registrant.getLastName() + "_" + registrant.getFirstName() + ".jpg";
       
       cam.takeImage(path);
       registrant.setImgLink(path);
       
       if(threadChosen == 2){           
            cameraPhoto.setPanelsColor(user.getColors(), user.getDepartment());
            user.setCameraPhoto(new ImageIcon(path));
       }
       else if(threadChosen == 1){
            cameraPhoto.setIcons();
            table.setCameraPhoto(new ImageIcon(path));           
       }
       
       cameraPhoto.setPhoto(new ImageIcon(path), path);
       
       CardLayout c = (CardLayout)(card.getLayout());
       c.show(card, "cameraPhoto");     
    }
 
    
}
