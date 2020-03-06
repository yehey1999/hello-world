/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rosalijos
 */
public class CameraHolder extends javax.swing.JPanel {

    /**
     * Creates new form CameraHolder
     */
    
    private JFrame frame;
    private WebcamPanel pnl_Cam;
    private JLabel lbl_CameraIcon = new JLabel();
    private CountDownTimer countdown;
    private Thread t, u;
    private ImageIcon img;
    private SarxosCamera camera;
    private ThreadChecker check;
    private CameraPhoto cameraPhoto = new CameraPhoto();
    private JPanel card;
    private JPanel mainCards;
    private User user;
    private Registrant registrant;
    private Table table;
    private int currentCaller;
    
    public CameraHolder(JFrame frame, JPanel cards) {
        initComponents();
        this.frame = frame;
        card = this;
        this.mainCards = cards;
        
        setLayout(new CardLayout());
        
        camera = new SarxosCamera();
        pnl_Cam = camera.initializeVideo();
        
        //pnl_Cam = camera.initializeVideo(1);
        //camera.startVideo(pnl_Cam); 
       
        //pnl_CameraHolder.add(pnl_Cam, BorderLayout.CENTER);
        
        cameraPhoto = new CameraPhoto(this, camera, mainCards);
        //showCountdown();
        
        add(pnl_CameraHolder, "cameraHolder");
        add(cameraPhoto, "cameraPhoto");     
        
        //registrant = new Registrant();
    }
   
    
    public void startDisplay(){
        //camera = new SarxosCamera();
        pnl_Cam = camera.initializeVideo();
        pnl_Cam.setLayout(new BorderLayout());
        pnl_CameraHolder.add(pnl_Cam, BorderLayout.CENTER);
        
        camera.startVideo(pnl_Cam);       
        
        //cameraPhoto = new CameraPhoto(this, camera, mainCards);
        showCountdown();
    }
    
    public void setCameraIndex(int index){
        camera.setCameraIndex(index);
    }
    
    public WebcamPanel getCameraHolder(){
        return pnl_Cam;
    }
    
    public void setCameraHolder(WebcamPanel wCam){
        this.pnl_Cam = wCam;
    }
    
    public void setImgNetworkPath(String imgNetworkPath){
        
    }
    
    public SarxosCamera getCameras(){
        return camera;
    }
    
    public SarxosCamera getCamera(){
        return camera;
    }
    
    public void setRegistrant(Registrant registrant){
        this.registrant = registrant;
    }
        
    public void showCountdown(){
        ArrayList<ImageIcon> images = new ArrayList<>();
        images.add(new ImageIcon(this.getClass().getResource("number0.gif")));
        images.add(new ImageIcon(this.getClass().getResource("number1.gif")));
        images.add(new ImageIcon(this.getClass().getResource("number2.gif")));
        images.add(new ImageIcon(this.getClass().getResource("number3.gif")));   

        countdown = new CountDownTimer(images);
        countdown.setContainer(lbl_CameraIcon);
        
        lbl_CameraIcon.addMouseListener(new MouseHandler());
        lbl_CameraIcon.setIcon(new ImageIcon(this.getClass().getResource("cameraman.gif")));
        lbl_CameraIcon.setHorizontalAlignment(JLabel.CENTER);
        
        if(pnl_Cam != null)
            pnl_Cam.add(lbl_CameraIcon, BorderLayout.CENTER);
        
        t = new Thread(countdown);
        
        if(currentCaller == 0)
            u = new Thread(new ThreadChecker(t, camera, card, cameraPhoto, user, registrant));
        else if(currentCaller == 1)
            u = new Thread(new ThreadChecker(t, camera, card, cameraPhoto, table, registrant));        
        
        t.setPriority(10);
        u.setPriority(5);         
    }
    
    public void setUser(User user){
        cameraPhoto.setUser(user);
        this.user = user;
    }
    
    public void setCaller(int caller){
        this.currentCaller = caller;
    }
    
    public void setTable(Table table){
        cameraPhoto.setTable(table);
        this.table = table;
    }
    
    class MouseHandler implements MouseListener{

        @Override
        @SuppressWarnings("empty-statement")
        public void mouseClicked(MouseEvent me) {
            try{
                t.start();
                u.start();
            }catch(Exception e){}
        }

        @Override
        public void mousePressed(MouseEvent me) {}

        @Override
        public void mouseReleased(MouseEvent me) {}

        @Override
        public void mouseEntered(MouseEvent me) {}

        @Override
        public void mouseExited(MouseEvent me) {}
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_CameraHolder = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        pnl_CameraHolder.setLayout(new java.awt.BorderLayout());
        add(pnl_CameraHolder, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnl_CameraHolder;
    // End of variables declaration//GEN-END:variables
}
