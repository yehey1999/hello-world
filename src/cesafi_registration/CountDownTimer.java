/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Rosalijos
 */
public class CountDownTimer implements Runnable{
    private ArrayList<ImageIcon> images;
    private JLabel imgContainer;
    private int count;
    
    public CountDownTimer(ArrayList<ImageIcon> images){
        this.images = images;
        this.count = images.size();
    }
    
    public void setContainer(JLabel imgContainer){
        this.imgContainer = imgContainer;
    }
    
    @Override
    public void run() {
       try{
           for(count -= 1; count >= 0; count--){
               imgContainer.setIcon(images.get(count));
               Thread.sleep(1000);
           }
       }catch(Exception e){
           e.printStackTrace();
       }
    }

}
