/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
// * @author Rosalijos
 */
public class CameraPhoto extends javax.swing.JPanel{

    /**
     * Creates new form CameraPhoto
     */

    private Thread t;
    private SarxosCamera cam;
    private JPanel card;
    private ImageIcon photo;
    private JPanel mainCards;
    private User user;
    private int[][] colors;
    
    public String[] repeatIcons = {"Images//repeat elementary.png", "Images//repeat junior.png", "Images//repeat seniorHS.png", "Images//repeat college.png"};
    public String[] nextIcons = {"Images//next elementary.png", "Images//next junior.png", "Images//next seniorHS.png", "Images//next college.png"};

    private Table table;
    private int current = 0;
    
    public CameraPhoto(){
    }
    
    public CameraPhoto(JPanel card, SarxosCamera cam, JPanel mainCards) {
        initComponents();
        this.t = t;
        this.cam = cam;
        this.card = card;
        this.mainCards = mainCards;
        this.user = user;
        
        pnl_CameraPhoto.addComponentListener(new PanelListener());
        
        lbl_repeatIcon.setIcon(Images.changeSize(new ImageIcon("Images//repeat.png"), 80, 80));
        lbl_nextIcon.setIcon(Images.changeSize(new ImageIcon("Images//next.png"), 80, 80));
        
        lbl_repeatIcon.addMouseListener(new MouseHandler());
        lbl_nextIcon.addMouseListener(new MouseHandler());
        
        //photo = new ImageIcon("Images\\college.png");
        
        setIcons();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        pnl_Inner2 = new javax.swing.JPanel();
        pnl_Outer2 = new javax.swing.JPanel();
        pnl_Center2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        pnl_Inner1 = new javax.swing.JPanel();
        pnl_Outer1 = new javax.swing.JPanel();
        pnl_Center1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        pnl_Holder = new javax.swing.JPanel();
        pnl_Bottom = new javax.swing.JPanel();
        lbl_repeatIcon = new javax.swing.JLabel();
        lbl_nextIcon = new javax.swing.JLabel();
        pnl_CameraPhoto = new javax.swing.JPanel();
        lbl_CameraPhoto = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(120, 773));
        jPanel6.setLayout(new java.awt.BorderLayout());

        pnl_Inner2.setBackground(new java.awt.Color(0, 112, 158));
        pnl_Inner2.setPreferredSize(new java.awt.Dimension(30, 773));

        javax.swing.GroupLayout pnl_Inner2Layout = new javax.swing.GroupLayout(pnl_Inner2);
        pnl_Inner2.setLayout(pnl_Inner2Layout);
        pnl_Inner2Layout.setHorizontalGroup(
            pnl_Inner2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        pnl_Inner2Layout.setVerticalGroup(
            pnl_Inner2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        jPanel6.add(pnl_Inner2, java.awt.BorderLayout.LINE_START);

        pnl_Outer2.setBackground(new java.awt.Color(0, 80, 115));
        pnl_Outer2.setLayout(new java.awt.BorderLayout());

        pnl_Center2.setBackground(new java.awt.Color(174, 211, 231));
        pnl_Center2.setPreferredSize(new java.awt.Dimension(15, 773));

        javax.swing.GroupLayout pnl_Center2Layout = new javax.swing.GroupLayout(pnl_Center2);
        pnl_Center2.setLayout(pnl_Center2Layout);
        pnl_Center2Layout.setHorizontalGroup(
            pnl_Center2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        pnl_Center2Layout.setVerticalGroup(
            pnl_Center2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        pnl_Outer2.add(pnl_Center2, java.awt.BorderLayout.LINE_START);

        jPanel6.add(pnl_Outer2, java.awt.BorderLayout.CENTER);

        add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(120, 773));
        jPanel7.setLayout(new java.awt.BorderLayout());

        pnl_Inner1.setBackground(new java.awt.Color(0, 112, 158));
        pnl_Inner1.setPreferredSize(new java.awt.Dimension(30, 773));

        javax.swing.GroupLayout pnl_Inner1Layout = new javax.swing.GroupLayout(pnl_Inner1);
        pnl_Inner1.setLayout(pnl_Inner1Layout);
        pnl_Inner1Layout.setHorizontalGroup(
            pnl_Inner1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        pnl_Inner1Layout.setVerticalGroup(
            pnl_Inner1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        jPanel7.add(pnl_Inner1, java.awt.BorderLayout.LINE_END);

        pnl_Outer1.setBackground(new java.awt.Color(0, 80, 115));
        pnl_Outer1.setLayout(new java.awt.BorderLayout());

        pnl_Center1.setBackground(new java.awt.Color(174, 211, 231));
        pnl_Center1.setPreferredSize(new java.awt.Dimension(15, 773));

        javax.swing.GroupLayout pnl_Center1Layout = new javax.swing.GroupLayout(pnl_Center1);
        pnl_Center1.setLayout(pnl_Center1Layout);
        pnl_Center1Layout.setHorizontalGroup(
            pnl_Center1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        pnl_Center1Layout.setVerticalGroup(
            pnl_Center1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        pnl_Outer1.add(pnl_Center1, java.awt.BorderLayout.LINE_END);

        jPanel7.add(pnl_Outer1, java.awt.BorderLayout.CENTER);

        add(jPanel7, java.awt.BorderLayout.LINE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(1515, 50));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1515, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(1500, 40));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1515, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel9.setLayout(new java.awt.BorderLayout());

        pnl_Holder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 80, 115), 10));
        pnl_Holder.setLayout(new java.awt.BorderLayout());

        pnl_Bottom.setBackground(new java.awt.Color(174, 211, 231));
        pnl_Bottom.setPreferredSize(new java.awt.Dimension(1315, 90));
        pnl_Bottom.add(lbl_repeatIcon);

        lbl_nextIcon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pnl_Bottom.add(lbl_nextIcon);

        pnl_Holder.add(pnl_Bottom, java.awt.BorderLayout.PAGE_END);

        pnl_CameraPhoto.setLayout(new java.awt.BorderLayout());
        pnl_CameraPhoto.add(lbl_CameraPhoto, java.awt.BorderLayout.CENTER);

        pnl_Holder.add(pnl_CameraPhoto, java.awt.BorderLayout.CENTER);

        jPanel9.add(pnl_Holder, java.awt.BorderLayout.CENTER);

        add(jPanel9, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_CameraPhoto;
    private javax.swing.JLabel lbl_nextIcon;
    private javax.swing.JLabel lbl_repeatIcon;
    private javax.swing.JPanel pnl_Bottom;
    private javax.swing.JPanel pnl_CameraPhoto;
    private javax.swing.JPanel pnl_Center1;
    private javax.swing.JPanel pnl_Center2;
    private javax.swing.JPanel pnl_Holder;
    private javax.swing.JPanel pnl_Inner1;
    private javax.swing.JPanel pnl_Inner2;
    private javax.swing.JPanel pnl_Outer1;
    private javax.swing.JPanel pnl_Outer2;
    // End of variables declaration//GEN-END:variables


    class MouseHandler implements MouseListener{
        public void mouseClicked(MouseEvent me) {
            if(me.getSource() == lbl_repeatIcon){
                CardLayout c = (CardLayout)(card.getLayout());
                ((CameraHolder)card).showCountdown();
                c.show(card, "cameraHolder");
            }
            else if(me.getSource() == lbl_nextIcon){
                cam.close();
                CardLayout c = (CardLayout)(mainCards.getLayout());
                
                if(current == 0)
                    c.show(mainCards, "user");
                if(current == 1)
                    c.show(mainCards, "admin");
            }
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
    
    public void setTable(Table table){
        current = 1;
        this.table = table;
    }

    public void setUser(User user){
        current = 0;
        this.user = user;
    }
    
    public void setPhoto(ImageIcon photo, String path){
        this.photo = photo;
        Dimension size = pnl_CameraPhoto.getSize();
        lbl_CameraPhoto.setIcon(Images.changeSize(photo, size.width, size.height));
        System.out.println(path);
        //lbl_CameraPhoto.setIcon(Images.changeSize(this.photo, 720, 100));
        //lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon(path), 720, 100));
    }
    
    public void setColors(int[][] colors){
        this.colors = colors;
    }
    class PanelListener implements ComponentListener{
        public void componentResized(ComponentEvent e) {
            Dimension size = pnl_CameraPhoto.getSize();
            if(photo != null)
            lbl_CameraPhoto.setIcon(Images.changeSize(photo, size.width, size.height));
        }

        @Override
        public void componentMoved(ComponentEvent ce) {}

        @Override
        public void componentShown(ComponentEvent ce) {}

        @Override
        public void componentHidden(ComponentEvent ce) {}
    }
    
    public void setPanelsColor(int[][] colors, int department){
        pnl_Inner1.setBackground(new Color(colors[1][0], colors[1][1], colors[1][2]));
        pnl_Inner2.setBackground(new Color(colors[1][0], colors[1][1], colors[1][2]));
        
        pnl_Center1.setBackground(new Color(colors[2][0], colors[2][1], colors[2][2]));
        pnl_Center2.setBackground(new Color(colors[2][0], colors[2][1], colors[2][2]));

        pnl_Outer1.setBackground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        pnl_Outer2.setBackground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        
        pnl_Bottom.setBackground(new Color(colors[2][0], colors[2][1], colors[2][2]));
        
        pnl_Holder.setBorder(BorderFactory.createLineBorder(new Color(colors[0][0], colors[0][1], colors[0][2]), 10));
        
        ImageIcon repeatIcon = null;
        ImageIcon nextIcon = null;
        
        switch(department){
            case 1:
                repeatIcon = new ImageIcon(repeatIcons[0]);
                nextIcon = new ImageIcon(nextIcons[0]);
                break;
            case 2:
                repeatIcon = new ImageIcon(repeatIcons[1]);
                nextIcon = new ImageIcon(nextIcons[1]);
                break;
            case 3:
                repeatIcon = new ImageIcon(repeatIcons[2]);
                nextIcon = new ImageIcon(nextIcons[2]);
                break;
            case 4:
                repeatIcon = new ImageIcon(repeatIcons[3]);
                nextIcon = new ImageIcon(nextIcons[3]);
                break;
        }
        
        lbl_repeatIcon.setIcon(Images.changeSize(repeatIcon, 80, 80));
        lbl_nextIcon.setIcon(Images.changeSize(nextIcon, 80, 80));
    }
    
    public void setIcons(){
        lbl_repeatIcon.setIcon(Images.changeSize(new ImageIcon(repeatIcons[2]), 80, 80));
        lbl_nextIcon.setIcon(Images.changeSize(new ImageIcon(nextIcons[2]), 80, 80));       
    }

}
