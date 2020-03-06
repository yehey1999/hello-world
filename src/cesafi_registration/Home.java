/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Rosalijos
 */
public class Home extends javax.swing.JPanel {

    /**
     * Creates new form Home
     */
    
    private BufferedImage img_Elem = null;
    private BufferedImage img_Junior = null;
    private BufferedImage img_Senior = null;
    private BufferedImage img_College = null;

    private JLabel lblElem = new JLabel();
    private JLabel lblJuniorHS = new JLabel();
    private JLabel lblSeniorHS = new JLabel();
    private JLabel lblCollege = new JLabel();
    private JFrame frame;
    private JPanel cards;
    private User user;
    
    public Home(){   
    }
        
    public Home(JFrame frame, User user, JPanel cards) {
        initComponents();
        
        this.frame = frame;
        this.user = user;
        this.cards = cards;
            
        initializeDesigns();
        initializeListeners();
    }
    
    public void setParentFrame(JFrame frame){
        this.frame = frame;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public void setCards(JPanel cards){
        this.cards = cards;
    }
    
    public void initializeDesigns(){
        setIcons();
        lblElem.setHorizontalAlignment(JLabel.CENTER);
        lblJuniorHS.setHorizontalAlignment(JLabel.CENTER);
        lblSeniorHS.setHorizontalAlignment(JLabel.CENTER);
        lblCollege.setHorizontalAlignment(JLabel.CENTER);
        
        setBorderColors();
        
        card_Elem.add(lblElem);
        card_JuniorHS.add(lblJuniorHS);
        card_SeniorHS.add(lblSeniorHS);
        card_College.add(lblCollege);       
    }
    
    public void setIcons(){
        JLabel lblLogo = new JLabel();
        lblLogo.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("quiz_ball_logo.png")),1000, 250));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        lblLogo.setBorder(new EmptyBorder(0,0,20,0));
        pnl_header.add(lblLogo, BorderLayout.CENTER);
        
        lbl_CesafiLogo.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("cesafi logo.jpg")), 220,90));
        lbl_AdminIcon.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("adminIcon.png")), 80,80));
        lbl_CesafiLogo.setBorder(new EmptyBorder(20,0,0,0));
        lbl_AdminIcon.setBorder(new EmptyBorder(20,0,0,0));     
        lblElem.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("elementary.png")), 250,200));
        lblJuniorHS.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("junior_hs.png")), 250,200));
        lblSeniorHS.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("senior_hs.png")), 250,200));
        lblCollege.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("college.png")), 250,200));
    }
    
    public void setBorderColors(){
        card_Elem.setBorder(BorderFactory.createLineBorder(new Color(0,84,20), 10));
        card_JuniorHS.setBorder(BorderFactory.createLineBorder(new Color(0,80,115), 10));
        card_SeniorHS.setBorder(BorderFactory.createLineBorder(new Color(112,119,0), 10));
        card_College.setBorder(BorderFactory.createLineBorder(new Color(156,65,15), 10));     
    }
    
    public void initializeListeners(){
        try{
            card_Elem.addComponentListener(new PanelListener());
            card_Elem.addMouseListener(new MouseActionListener());
            
            card_JuniorHS.addMouseListener(new MouseActionListener());
            card_SeniorHS.addMouseListener(new MouseActionListener());
            card_College.addMouseListener(new MouseActionListener());
            lbl_AdminIcon.addMouseListener(new MouseActionListener());
        }catch(Exception e){
            e.printStackTrace();
        }      
    }
    
    class PanelListener implements ComponentListener{
        public void componentResized(ComponentEvent e) {
                Dimension size = card_Elem.getSize(); 
                lblElem.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("elementary.png")), size.width, size.height));
                lblJuniorHS.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("junior_hs.png")), size.width, size.height));
                lblSeniorHS.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("senior_hs.png")), size.width, size.height));
                lblCollege.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("college.png")), size.width, size.height));
        }

        public void componentMoved(ComponentEvent e) {}

        public void componentShown(ComponentEvent e) {}

        public void componentHidden(ComponentEvent e) {}      

    }
    
    class MouseActionListener implements MouseListener{
       @Override
        public void mouseClicked(MouseEvent me) {
            
            CardLayout c1 = (CardLayout)cards.getLayout();
            
            if(me.getSource() == card_Elem)
                user.initializeColors(1);
            if(me.getSource() == card_JuniorHS)
                user.initializeColors(2);
            if(me.getSource() == card_SeniorHS)
                user.initializeColors(3);
            if(me.getSource() == card_College)
                user.initializeColors(4);
                       
            if(me.getSource() != lbl_AdminIcon)
                c1.show(cards,"user");           
            if(me.getSource() == lbl_AdminIcon)
                c1.show(cards,"admin");
        }

        @Override
        public void mousePressed(MouseEvent me) {}

        @Override
        public void mouseReleased(MouseEvent me) {}

        @Override
        public void mouseEntered(MouseEvent me) {
            frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent me) {
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        card = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        card_Elem = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        card_JuniorHS = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        card_SeniorHS = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        card_College = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lbl_CesafiLogo = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lbl_AdminIcon = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        pnl_LogIn = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(500, 300));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(100, 100, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(120, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(120, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.LINE_START);

        card.setBackground(new java.awt.Color(255, 255, 255));
        card.setLayout(new java.awt.GridLayout(1, 4, 10, 20));

        jPanel12.setBackground(new java.awt.Color(240, 240, 40));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.BorderLayout());
        jPanel12.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel14, java.awt.BorderLayout.PAGE_END);

        jPanel23.setBackground(new java.awt.Color(100, 100, 100));
        jPanel23.setLayout(new java.awt.BorderLayout());

        card_Elem.setBackground(new java.awt.Color(0, 84, 20));
        card_Elem.setLayout(new java.awt.BorderLayout());

        jButton4.setBackground(new java.awt.Color(0, 84, 20));
        jButton4.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("ELEMENTARY");
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setMargin(new java.awt.Insets(20, 14, 20, 14));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        card_Elem.add(jButton4, java.awt.BorderLayout.PAGE_END);

        jPanel23.add(card_Elem, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel23, java.awt.BorderLayout.CENTER);

        card.add(jPanel12);

        jPanel10.setBackground(new java.awt.Color(240, 24, 240));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel16, java.awt.BorderLayout.PAGE_END);

        card_JuniorHS.setBackground(new java.awt.Color(0, 80, 115));
        card_JuniorHS.setLayout(new java.awt.BorderLayout());

        jButton3.setBackground(new java.awt.Color(0, 80, 115));
        jButton3.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("JUNIOR HS");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setMargin(new java.awt.Insets(20, 14, 20, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        card_JuniorHS.add(jButton3, java.awt.BorderLayout.PAGE_END);

        jPanel10.add(card_JuniorHS, java.awt.BorderLayout.CENTER);

        card.add(jPanel10);

        jPanel9.setBackground(new java.awt.Color(240, 20, 40));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel18, java.awt.BorderLayout.PAGE_END);

        card_SeniorHS.setBackground(new java.awt.Color(112, 119, 0));
        card_SeniorHS.setLayout(new java.awt.BorderLayout());

        jButton2.setBackground(new java.awt.Color(112, 119, 0));
        jButton2.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SENIOR HS");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setMargin(new java.awt.Insets(20, 14, 20, 14));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        card_SeniorHS.add(jButton2, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(card_SeniorHS, java.awt.BorderLayout.CENTER);

        card.add(jPanel9);

        jPanel6.setBackground(new java.awt.Color(0, 240, 240));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        card_College.setBackground(new java.awt.Color(156, 65, 15));
        card_College.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(156, 65, 15));
        jButton1.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("COLLEGE");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setMargin(new java.awt.Insets(20, 14, 20, 14));
        card_College.add(jButton1, java.awt.BorderLayout.PAGE_END);

        jPanel6.add(card_College, java.awt.BorderLayout.CENTER);

        card.add(jPanel6);

        jPanel2.add(card, java.awt.BorderLayout.CENTER);

        jPanel11.setPreferredSize(new java.awt.Dimension(1634, 290));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(120, 220));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(120, 220));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel7, java.awt.BorderLayout.LINE_START);

        pnl_header.setBackground(new java.awt.Color(255, 255, 255));
        pnl_header.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(1394, 90));
        jPanel8.setLayout(new java.awt.GridLayout(1, 2));
        jPanel8.add(lbl_CesafiLogo);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));
        jPanel15.setLayout(new java.awt.BorderLayout());

        lbl_AdminIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_AdminIcon.setToolTipText("");
        lbl_AdminIcon.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel15.add(lbl_AdminIcon, java.awt.BorderLayout.EAST);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );

        jPanel15.add(jPanel17, java.awt.BorderLayout.LINE_START);

        pnl_LogIn.setBackground(new java.awt.Color(255, 255, 255));
        pnl_LogIn.setLayout(new java.awt.BorderLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );

        pnl_LogIn.add(jPanel21, java.awt.BorderLayout.LINE_END);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.GridLayout(2, 1, 5, 5));
        pnl_LogIn.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel15.add(pnl_LogIn, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel15);

        pnl_header.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel11.add(pnl_header, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel11, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card;
    private javax.swing.JPanel card_College;
    private javax.swing.JPanel card_Elem;
    private javax.swing.JPanel card_JuniorHS;
    private javax.swing.JPanel card_SeniorHS;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_AdminIcon;
    private javax.swing.JLabel lbl_CesafiLogo;
    private javax.swing.JPanel pnl_LogIn;
    private javax.swing.JPanel pnl_header;
    // End of variables declaration//GEN-END:variables
}
