/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import UDP.UDPServer;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Rosalijos
 */
public class Table extends javax.swing.JPanel {

    /**
     * Creates new form Table
     */
    
    private JFrame frame;
    private JPanel cards;
    private CameraHolder cameraHolder;
    private Table table;
    private CameraPhoto cameraPhoto;
    private Registrant registrant;
    private String oldLink;
    private CountDownTimer countdown;
    public static DefaultTableModel dm;
    private Connection conn;
    private Statement stmt;
    private int cameraIndex;
    
    private final int TABLE_NUMBER = 1;
    private String imgPathNetwork;
    

      

    public Table(JFrame frame, JPanel cards, CameraHolder cameraHolder) {
        initComponents();
        this.frame = frame;
        this.cards = cards;
        this.cameraHolder = cameraHolder;
        this.table = this;
        
        
        lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_blue.png", "camera_initial"), 60,60));
        lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon("Images\\folder_blue.png", "camera_initial"), 40,40));
        lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon("Images\\college.png"), 260, 190));
        
        lbl_UpdateIcon.setIcon(Images.changeSize(new ImageIcon("Images\\edit_icon_blue.png", "edit_initial"), 60, 60));
        lbl_DeleteIcon.setIcon(Images.changeSize(new ImageIcon("Images\\delete_icon_blue.png", "delete_initial"), 60, 60));
        lbl_AddIcon.setIcon(Images.changeSize(new ImageIcon("Images\\add_icon_blue.png", "add_initial"), 60, 60));
        
        lbl_PrintIcon.setIcon(Images.changeSize(new ImageIcon("Images\\print_icon.png"), 60, 60));
        lbl_Asc.setIcon(Images.changeSize(new ImageIcon("Images\\ascending.png"), 30, 30));
        lbl_Desc.setIcon(Images.changeSize(new ImageIcon("Images\\descending.png"), 30, 30));
        lbl_RefreshTable.setIcon(Images.changeSize(new ImageIcon("Images\\reload white.png"), 30, 30));
        
        lbl_Home.setIcon(Images.changeSize(new ImageIcon("Images\\home_admin.png"),  80, 80));
        lbl_CesafiLogo.setIcon(Images.changeSize(new ImageIcon("Images\\cesafi logo.jpg"), 200,90));       
        lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon("Images\\college.png"), 260, 190));
        
        lbl_Home.addMouseListener(new LabelListener());
        lbl_CameraIcon.addMouseListener(new LabelListener());
        lbl_FolderIcon.addMouseListener(new LabelListener());
        lbl_UpdateIcon.addMouseListener(new LabelListener());
        lbl_DeleteIcon.addMouseListener(new LabelListener());
        lbl_AddIcon.addMouseListener(new LabelListener());
        
        
        lbl_PrintIcon.addMouseListener(new LabelListener());
        lbl_Search.addMouseListener(new LabelListener());
        lbl_Asc.addMouseListener(new LabelListener());
        lbl_Desc.addMouseListener(new LabelListener());
        lbl_RefreshIcon.addMouseListener(new LabelListener());
        lbl_ViewCameraIcon.addMouseListener(new LabelListener());
        lbl_RefreshTable.addMouseListener(new LabelListener());
        
        lbl_RefreshIcon.setIcon(Images.changeSize(new ImageIcon("Images\\reload white.png"), 30, 30));
        lbl_CameraTypeIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_white.png"), 60, 60));
        lbl_ViewCameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\eye_white.png"), 35, 40));
        
        
        setCameras();

        dm = (DefaultTableModel) jTable1.getModel();
        dm.addColumn("LastName");
        dm.addColumn("FirstName");
        dm.addColumn("Role");
        dm.addColumn("Year Level");
        dm.addColumn("School");
        dm.addColumn("Img Link");
        
        cb_Cameras.addItemListener(new ComboboxHandler());
        MySql database = new MySql();
        database.updateTable(dm);
    }
    
    public void initDB(){       
       try {
         // Connect to the local InterBase database
         conn = DriverManager.getConnection("jdbc:mysql://localhost/event","kaizer","12345678");
        // System.out.println("Database connected\n");
         // Create a statement
         stmt = conn.createStatement();
       }
       catch (Exception ex) {
           
       }
    }
    
    private void filter(String query){
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        jTable1.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    public void clearRow(){
      if(dm.getRowCount() > 0){
         for(int i = dm.getRowCount()-1;i>-1;i--)
            dm.removeRow(i);
      }
    } 
    
    public void updateTable(){
        clearRow();  
        MySql database = new MySql();
        database.updateTable(dm);
        
        //database.updateTable(dm);
        
        /*
       try{
           //ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school \n" + "FROM participants\n" + "ORDER BY lastname");
           ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink \n" + "FROM participants\n" + "ORDER BY lastname");
           while(resultSet.next())
              dm.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});
              stmt.close();
          }
       catch(Exception ex){
          ex.printStackTrace();
       } 
        clearRow();
        //registrant.updateTable(dm);
        */
   }
    
  public void updateTableDesc(){
     MySql database = new MySql();
     database.updateTableDesc(dm);
     
     //database.updateTableDesc(dm);
   /*
   clearRow();
   initDB();
   try{
        ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink\n" +
                                                "FROM participants\n" +
                                               "ORDER BY lastname DESC");  


        while(resultSet.next())
             dm.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});
         stmt.close();
   }
   catch(Exception ex){
      ex.printStackTrace();
      
   }  
      */
   }
  
   public String getImgPathNetwork(){
       return imgPathNetwork;
   }
  
    public void setCameraPhoto(ImageIcon icon){
        Dimension size = pnl_CameraPhoto.getSize();
        lbl_CameraPhoto.setIcon(Images.changeSize(icon, size.width, size.height));
    }

    public void setCameras(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cb_Cameras.getModel();
        model.removeAllElements();
        
        for(Webcam item: cameraHolder.getCameras().getCamera())
            model.addElement(item.toString());
        
        cb_Cameras.setModel(model);       
    }
    
    public void setRegistrant(Registrant registrant){
        this.registrant = registrant;
    }
    
    public Registrant getRegistrant(){
        return registrant;
    }
    
    class ComboboxHandler implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent ie) {      
            if(ie.getSource() == cb_Cameras){
                cameraIndex = cb_Cameras.getSelectedIndex();
                cameraHolder.getCamera().setCameraIndex(cameraIndex);
                System.out.println("combobox");
                JOptionPane.showMessageDialog(frame, cb_Cameras.getSelectedItem() + " is now your default camera.");
            }
        }
        
    }
    
    class LabelListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            CardLayout c = (CardLayout)cards.getLayout();
            if(me.getSource() == lbl_Home){
                c.show(cards, "home");
            }
            else if(me.getSource() == lbl_CameraIcon){ 
                if(!tf_lname.getText().equals("") && !tf_fname.getText().equals("")){
                if(((ImageIcon)lbl_CameraIcon.getIcon()).getDescription().equals("camera_final")){
                    
                    String lname = tf_lname.getText();
                    String fname = tf_fname.getText();
                    
                    if(lname.equals(""))
                        lname = "0000";
                    if(fname.equals(""))
                        fname = "0000";
                    
                    registrant.setLastName(tf_lname.getText());
                    registrant.setFirstName(tf_fname.getText());
                    
                    cameraHolder.setRegistrant(registrant);
                    cameraHolder.setTable(table);
                    cameraHolder.setCaller(TABLE_NUMBER);
                    cameraHolder.showCountdown();
                    cameraHolder.startDisplay();
                    cameraHolder.getCamera().open();
                    
                    System.out.println("Table camera is clicked");
                    CardLayout cm = (CardLayout)cameraHolder.getLayout();
                    c.show(cards, "camera");
                    cm.show(cameraHolder, "cameraHolder");}
                }
            }
            else if(me.getSource() == lbl_FolderIcon){
                try {
                    if(((ImageIcon)lbl_FolderIcon.getIcon()).getDescription().equals("folder_final")){
                        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Rosalijos\\Documents\\Cesafi_Registration\\Photos\\");
                        int result = fileChooser.showOpenDialog(table);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                            if(result == JFileChooser.APPROVE_OPTION){
                                String mimetype= new MimetypesFileTypeMap().getContentType(selectedFile);
                                String type = mimetype.split("/")[0];
                                if(type.equals("image")){
                                    System.out.println("It's an image");
                                    String path = selectedFile.getAbsolutePath();
                                    Dimension d = pnl_CameraPhoto.getSize();
                                    lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon(path), d.width , d.height));
                                }
                                else{ 
                                    System.out.println("It's NOT an image");
                                }

                            } 
                        }
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }             
            }
            else if(me.getSource() == lbl_RefreshIcon){
                setCameras();
            }
            else if(me.getSource()==lbl_Desc){
                clear();
                updateTableDesc();
            }
            else if(me.getSource()==lbl_Asc){
                clearRow();
                updateTable();
            }
            else if(me.getSource() == lbl_ViewCameraIcon){
                SarxosCamera camera = new SarxosCamera();
                JFrame viewCameraFrame = new JFrame();
                viewCameraFrame.setSize(500,400);
                viewCameraFrame.setResizable(false);
                viewCameraFrame.setVisible(true);
                
                WebcamPanel pnl_Webcam = camera.initializeVideo(cameraIndex);
                
                viewCameraFrame.add(pnl_Webcam, BorderLayout.CENTER);
                camera.startVideo(pnl_Webcam);
                
                viewCameraFrame.addWindowListener(new WindowAdapter(){
                   public void windowClosing(WindowEvent e){
                       camera.close();
                       viewCameraFrame.setVisible(false);
                       viewCameraFrame.dispose();
                   } 
                });   
            }
            else if(me.getSource() == lbl_RefreshTable){
                MySql database = new MySql();
                database.updateTable(dm);
            }
            

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
    
    public int findSelectedLevel(String name){   
        int selected = 0;
        if(name.equalsIgnoreCase("grade 1"))
           selected = 0;
        if(name.equalsIgnoreCase("grade 2"))
           selected = 1;
        if(name.equalsIgnoreCase("grade 3"))
           selected = 2;
        if(name.equalsIgnoreCase("grade 4"))
           selected = 3;
        if(name.equalsIgnoreCase("grade 5"))
           selected = 4;
        if(name.equalsIgnoreCase("grade 6"))
           selected = 5;
        if(name.equalsIgnoreCase("grade 7"))
           selected = 6;
        if(name.equalsIgnoreCase("grade 8"))
           selected = 7;
        if(name.equalsIgnoreCase("grade 9"))
           selected = 8;
        if(name.equalsIgnoreCase("grade 10"))
           selected = 9;
        if(name.equalsIgnoreCase("grade 11"))
           selected = 10;
        if(name.equalsIgnoreCase("grade 12"))
           selected = 11;
        if(name.equalsIgnoreCase("1st year"))
           selected = 12;
        if(name.equalsIgnoreCase("2nd year"))
           selected = 13;
        if(name.equalsIgnoreCase("3rd year"))
           selected = 14;
        if(name.equalsIgnoreCase("4th year"))
           selected = 15;
        return selected;
    }
    
    public int findSelectedRole(String role){
       int index = 0;
       if(role.equalsIgnoreCase("COACH"))
         index = 1;

       return index;
    }   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        lbl_CesafiLogo = new javax.swing.JLabel();
        Jlabel = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lbl_Settings = new javax.swing.JLabel();
        lbl_Home = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        pnl_CameraButtons = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_CameraIcon = new javax.swing.JLabel();
        lbl_FolderIcon = new javax.swing.JLabel();
        pnl_CameraPhoto = new javax.swing.JPanel();
        lbl_CameraPhoto = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        lbl_UpdateIcon = new javax.swing.JLabel();
        lbl_AddIcon = new javax.swing.JLabel();
        lbl_DeleteIcon = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tf_lname = new javax.swing.JTextField();
        tf_fname = new javax.swing.JTextField();
        tf_school = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmb_level = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmb_role = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        lbl_SearchIcon = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        lbl_Search = new javax.swing.JLabel();
        tf_search = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        lbl_RefreshTable = new javax.swing.JLabel();
        lbl_Asc = new javax.swing.JLabel();
        lbl_Desc = new javax.swing.JLabel();
        lbl_PrintIcon = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        btnStartServer = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        lbl_CameraTypeIcon = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        lbl_RefreshIcon = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        pnl_CameraView = new javax.swing.JPanel();
        lbl_ViewCameraIcon = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        btnSetImgPath = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfSetImgPath = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        cb_Cameras = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1524, 1020));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1524, 150));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(200, 150));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel20, java.awt.BorderLayout.LINE_START);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(200, 150));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel21, java.awt.BorderLayout.LINE_END);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.GridLayout(1, 3));

        lbl_CesafiLogo.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.add(lbl_CesafiLogo);

        Jlabel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.add(Jlabel);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new java.awt.GridLayout(1, 3));
        jPanel23.add(jLabel11);

        lbl_Settings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel23.add(lbl_Settings);

        lbl_Home.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel23.add(lbl_Home);

        jPanel22.add(jPanel23);

        jPanel1.add(jPanel22, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(140, 204, 202));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 1020));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1049, Short.MAX_VALUE)
        );

        add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(140, 204, 202));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1049, Short.MAX_VALUE)
        );

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel4.setBackground(new java.awt.Color(140, 204, 202));
        jPanel4.setPreferredSize(new java.awt.Dimension(1524, 50));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1524, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(24, 240, 240));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(40, 240, 40));
        jPanel6.setPreferredSize(new java.awt.Dimension(420, 570));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(240, 240, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(420, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(140, 204, 202));
        jPanel9.setPreferredSize(new java.awt.Dimension(80, 250));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel9, java.awt.BorderLayout.LINE_START);

        jPanel10.setBackground(new java.awt.Color(140, 204, 202));
        jPanel10.setPreferredSize(new java.awt.Dimension(80, 250));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel10, java.awt.BorderLayout.LINE_END);

        jPanel11.setPreferredSize(new java.awt.Dimension(420, 60));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(140, 204, 202));
        jPanel12.setPreferredSize(new java.awt.Dimension(80, 60));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel12, java.awt.BorderLayout.LINE_START);

        jPanel13.setBackground(new java.awt.Color(140, 204, 202));
        jPanel13.setPreferredSize(new java.awt.Dimension(80, 60));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel13, java.awt.BorderLayout.LINE_END);

        pnl_CameraButtons.setBackground(new java.awt.Color(0, 109, 104));
        pnl_CameraButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        pnl_CameraButtons.setLayout(new java.awt.GridLayout(1, 3));
        pnl_CameraButtons.add(jLabel1);

        lbl_CameraIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_CameraButtons.add(lbl_CameraIcon);

        lbl_FolderIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pnl_CameraButtons.add(lbl_FolderIcon);

        jPanel11.add(pnl_CameraButtons, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        pnl_CameraPhoto.setBackground(new java.awt.Color(255, 255, 255));
        pnl_CameraPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 109, 104), 8));
        pnl_CameraPhoto.setLayout(new java.awt.BorderLayout());
        pnl_CameraPhoto.add(lbl_CameraPhoto, java.awt.BorderLayout.CENTER);

        jPanel7.add(pnl_CameraPhoto, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(240, 0, 240));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(0, 109, 104));
        jPanel14.setPreferredSize(new java.awt.Dimension(420, 120));
        jPanel14.setLayout(new java.awt.GridLayout(1, 0));

        lbl_UpdateIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_UpdateIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_UpdateIconMouseClicked(evt);
            }
        });
        jPanel14.add(lbl_UpdateIcon);

        lbl_AddIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_AddIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_AddIconMouseClicked(evt);
            }
        });
        jPanel14.add(lbl_AddIcon);

        lbl_DeleteIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_DeleteIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_DeleteIconMouseClicked(evt);
            }
        });
        jPanel14.add(lbl_DeleteIcon);

        jPanel8.add(jPanel14, java.awt.BorderLayout.PAGE_END);

        jPanel15.setBackground(new java.awt.Color(0, 164, 157));
        jPanel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FIRST NAME");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jLabel3, gridBagConstraints);

        tf_lname.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tf_lname.setForeground(new java.awt.Color(0, 109, 104));
        tf_lname.setEnabled(false);
        tf_lname.setMargin(new java.awt.Insets(5, 5, 5, 25));
        tf_lname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_lnameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(tf_lname, gridBagConstraints);

        tf_fname.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tf_fname.setForeground(new java.awt.Color(0, 109, 104));
        tf_fname.setEnabled(false);
        tf_fname.setMargin(new java.awt.Insets(5, 5, 5, 25));
        tf_fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_fnameActionPerformed(evt);
            }
        });
        tf_fname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_fnameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(tf_fname, gridBagConstraints);

        tf_school.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tf_school.setForeground(new java.awt.Color(0, 109, 104));
        tf_school.setEnabled(false);
        tf_school.setMargin(new java.awt.Insets(5, 5, 5, 25));
        tf_school.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_schoolKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(tf_school, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("SCHOOL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jLabel4, gridBagConstraints);

        cmb_level.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cmb_level.setForeground(new java.awt.Color(0, 109, 104));
        cmb_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6", "Grade 7", "Grade 8", "Grade 9", "Grade 10", "Grade 11", "Grade 12", "1st Year", "2nd Year", "3rd Year", "4th Year" }));
        cmb_level.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(cmb_level, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("LAST NAME");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("LEVEL");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jLabel6, gridBagConstraints);

        cmb_role.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cmb_role.setForeground(new java.awt.Color(0, 109, 104));
        cmb_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Participant", "Coach" }));
        cmb_role.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(cmb_role, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("ROLE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jLabel7, gridBagConstraints);

        jPanel8.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(140, 204, 202));
        jPanel17.setPreferredSize(new java.awt.Dimension(904, 150));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(0, 109, 104));
        jPanel19.setPreferredSize(new java.awt.Dimension(904, 60));
        jPanel19.setLayout(new java.awt.BorderLayout());

        lbl_SearchIcon.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel19.add(lbl_SearchIcon, java.awt.BorderLayout.CENTER);

        jPanel25.setPreferredSize(new java.awt.Dimension(904, 60));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jPanel26.setBackground(new java.awt.Color(140, 204, 202));
        jPanel26.setPreferredSize(new java.awt.Dimension(20, 60));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel25.add(jPanel26, java.awt.BorderLayout.LINE_END);

        jPanel27.setBackground(new java.awt.Color(0, 109, 104));
        jPanel27.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel28.setBackground(new java.awt.Color(0, 109, 104));
        jPanel28.setPreferredSize(new java.awt.Dimension(400, 60));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jPanel38.setBackground(new java.awt.Color(0, 109, 104));
        jPanel38.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 3, 5, 3));
        jPanel38.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel38.setLayout(new java.awt.BorderLayout());

        jComboBox4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(0, 109, 104));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Search by", "Last Name", "First Name", "Role", "Level", "School" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel38.add(jComboBox4, java.awt.BorderLayout.CENTER);

        jPanel28.add(jPanel38, java.awt.BorderLayout.WEST);
        jPanel28.add(lbl_Search, java.awt.BorderLayout.LINE_END);

        tf_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_search.setForeground(new java.awt.Color(0, 109, 104));
        tf_search.setMargin(new java.awt.Insets(5, 2, 5, 2));
        tf_search.setPreferredSize(new java.awt.Dimension(6, 59));
        tf_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_searchActionPerformed(evt);
            }
        });
        tf_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_searchKeyReleased(evt);
            }
        });
        jPanel28.add(tf_search, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28, java.awt.BorderLayout.LINE_START);

        jPanel29.setBackground(new java.awt.Color(0, 109, 104));
        jPanel29.setPreferredSize(new java.awt.Dimension(250, 60));
        jPanel29.setLayout(new java.awt.GridLayout(1, 4));
        jPanel29.add(lbl_RefreshTable);

        lbl_Asc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_AscMouseClicked(evt);
            }
        });
        jPanel29.add(lbl_Asc);

        lbl_Desc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_DescMouseClicked(evt);
            }
        });
        jPanel29.add(lbl_Desc);

        lbl_PrintIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_PrintIconMouseClicked(evt);
            }
        });
        jPanel29.add(lbl_PrintIcon);

        jPanel27.add(jPanel29, java.awt.BorderLayout.LINE_END);

        jPanel25.add(jPanel27, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel25, java.awt.BorderLayout.PAGE_END);

        jPanel17.add(jPanel19, java.awt.BorderLayout.PAGE_END);

        jLabel8.setToolTipText("");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 300));
        jPanel17.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel41.setBackground(new java.awt.Color(140, 204, 202));
        jPanel41.setPreferredSize(new java.awt.Dimension(904, 90));
        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel42.setPreferredSize(new java.awt.Dimension(200, 90));
        jPanel42.setLayout(new java.awt.BorderLayout());

        jPanel43.setBackground(new java.awt.Color(140, 204, 202));
        jPanel43.setPreferredSize(new java.awt.Dimension(200, 20));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel42.add(jPanel43, java.awt.BorderLayout.NORTH);

        jPanel45.setBackground(new java.awt.Color(140, 204, 202));
        jPanel45.setPreferredSize(new java.awt.Dimension(20, 10));
        jPanel45.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel42.add(jPanel45, java.awt.BorderLayout.EAST);

        jPanel44.setBackground(new java.awt.Color(140, 204, 202));
        jPanel44.setPreferredSize(new java.awt.Dimension(200, 20));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel42.add(jPanel44, java.awt.BorderLayout.SOUTH);

        btnStartServer.setBackground(new java.awt.Color(255, 255, 255));
        btnStartServer.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnStartServer.setForeground(new java.awt.Color(0, 109, 104));
        btnStartServer.setText("START SERVER");
        btnStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartServerActionPerformed(evt);
            }
        });
        jPanel42.add(btnStartServer, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel42, java.awt.BorderLayout.LINE_END);

        jPanel17.add(jPanel41, java.awt.BorderLayout.PAGE_START);

        jPanel16.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel18.setBackground(new java.awt.Color(140, 204, 202));
        jPanel18.setPreferredSize(new java.awt.Dimension(20, 870));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel18, java.awt.BorderLayout.LINE_END);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel16.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel30.setPreferredSize(new java.awt.Dimension(904, 80));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jPanel31.setBackground(new java.awt.Color(140, 204, 202));
        jPanel31.setPreferredSize(new java.awt.Dimension(20, 80));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel31, java.awt.BorderLayout.LINE_END);

        jPanel32.setLayout(new java.awt.BorderLayout());

        jPanel33.setBackground(new java.awt.Color(0, 109, 104));
        jPanel33.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel33.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel33.setLayout(new java.awt.BorderLayout());

        lbl_CameraTypeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel33.add(lbl_CameraTypeIcon, java.awt.BorderLayout.CENTER);

        jPanel32.add(jPanel33, java.awt.BorderLayout.LINE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jPanel35.setBackground(new java.awt.Color(0, 109, 104));
        jPanel35.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 20, 1));
        jPanel35.setPreferredSize(new java.awt.Dimension(540, 80));
        jPanel35.setRequestFocusEnabled(false);
        jPanel35.setLayout(new java.awt.BorderLayout());

        jPanel37.setBackground(new java.awt.Color(0, 109, 104));
        jPanel37.setPreferredSize(new java.awt.Dimension(50, 40));
        jPanel37.setRequestFocusEnabled(false);
        jPanel37.setLayout(new java.awt.BorderLayout());

        lbl_RefreshIcon.setBackground(new java.awt.Color(0, 109, 104));
        lbl_RefreshIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel37.add(lbl_RefreshIcon, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel37, java.awt.BorderLayout.WEST);

        jPanel39.setBackground(new java.awt.Color(0, 109, 104));
        jPanel39.setLayout(new java.awt.BorderLayout());

        jPanel40.setBackground(new java.awt.Color(0, 109, 104));
        jPanel40.setPreferredSize(new java.awt.Dimension(50, 40));
        jPanel40.setVerifyInputWhenFocusTarget(false);
        jPanel40.setLayout(new java.awt.BorderLayout());
        jPanel39.add(jPanel40, java.awt.BorderLayout.LINE_START);

        jPanel46.setLayout(new java.awt.BorderLayout());

        jPanel47.setBackground(new java.awt.Color(0, 109, 104));
        jPanel47.setPreferredSize(new java.awt.Dimension(140, 40));
        jPanel47.setLayout(new java.awt.BorderLayout());

        pnl_CameraView.setBackground(new java.awt.Color(0, 109, 104));
        pnl_CameraView.setPreferredSize(new java.awt.Dimension(50, 40));
        pnl_CameraView.setLayout(new java.awt.BorderLayout());

        lbl_ViewCameraIcon.setBackground(new java.awt.Color(0, 109, 104));
        lbl_ViewCameraIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_CameraView.add(lbl_ViewCameraIcon, java.awt.BorderLayout.CENTER);

        jPanel47.add(pnl_CameraView, java.awt.BorderLayout.WEST);

        jPanel51.setBackground(new java.awt.Color(0, 109, 104));
        jPanel51.setPreferredSize(new java.awt.Dimension(120, 40));

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel47.add(jPanel51, java.awt.BorderLayout.CENTER);

        jPanel46.add(jPanel47, java.awt.BorderLayout.LINE_START);

        jPanel48.setBackground(new java.awt.Color(0, 109, 104));
        jPanel48.setLayout(new java.awt.BorderLayout());

        jPanel49.setBackground(new java.awt.Color(0, 109, 104));
        jPanel49.setPreferredSize(new java.awt.Dimension(20, 40));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel48.add(jPanel49, java.awt.BorderLayout.LINE_END);

        jPanel50.setBackground(new java.awt.Color(0, 109, 104));
        jPanel50.setLayout(new java.awt.BorderLayout());

        btnSetImgPath.setBackground(new java.awt.Color(255, 255, 255));
        btnSetImgPath.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSetImgPath.setForeground(new java.awt.Color(0, 109, 104));
        btnSetImgPath.setText("SET");
        btnSetImgPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetImgPathActionPerformed(evt);
            }
        });
        jPanel50.add(btnSetImgPath, java.awt.BorderLayout.LINE_END);

        jLabel2.setBackground(new java.awt.Color(0, 109, 104));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("  Image Path  ");
        jPanel50.add(jLabel2, java.awt.BorderLayout.LINE_START);
        jPanel50.add(tfSetImgPath, java.awt.BorderLayout.CENTER);

        jPanel48.add(jPanel50, java.awt.BorderLayout.CENTER);

        jPanel46.add(jPanel48, java.awt.BorderLayout.CENTER);

        jPanel39.add(jPanel46, java.awt.BorderLayout.PAGE_END);

        jPanel35.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel36.setBackground(new java.awt.Color(0, 109, 104));
        jPanel36.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 20, 1));
        jPanel36.setPreferredSize(new java.awt.Dimension(240, 80));
        jPanel36.setLayout(new java.awt.BorderLayout());

        cb_Cameras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cb_Cameras.setForeground(new java.awt.Color(0, 109, 104));
        cb_Cameras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_Cameras.setActionCommand("");
        cb_Cameras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_CamerasActionPerformed(evt);
            }
        });
        jPanel36.add(cb_Cameras, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel36, java.awt.BorderLayout.LINE_START);

        jPanel32.add(jPanel34, java.awt.BorderLayout.CENTER);

        jPanel30.add(jPanel32, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel30, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel16, java.awt.BorderLayout.CENTER);

        add(jPanel5, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(jPanel24, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void tf_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_fnameActionPerformed

    private void tf_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_searchActionPerformed

    private void cb_CamerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_CamerasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_CamerasActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        clearRow();
        updateTable();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void tf_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_searchKeyReleased
        // TODO add your handling code here:
        //clearRow();
        String query = tf_search.getText().toUpperCase();
        filter(query);
    }//GEN-LAST:event_tf_searchKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
       int row = jTable1.rowAtPoint(evt.getPoint());
       int col = jTable1.columnAtPoint(evt.getPoint());
       
       int levelIndex = findSelectedLevel(jTable1.getValueAt(row,3).toString());
       int roleIndex = findSelectedRole(jTable1.getValueAt(row,2).toString());
       
       if(row >= 0 && col >= 0){
          tf_lname.setText(jTable1.getValueAt(row,0).toString());
          tf_fname.setText(jTable1.getValueAt(row,1).toString());
          tf_school.setText(jTable1.getValueAt(row,4).toString());
          cmb_level.setSelectedIndex(levelIndex);
          cmb_role.setSelectedIndex(roleIndex);
          
          //lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon("Photos\\" + jTable1.getValueAt(row,0).toString() + "_" + jTable1.getValueAt(row,1).toString() + ".jpg"), 260, 190));
          lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon(jTable1.getValueAt(row,5).toString()), 260, 190));
          lbl_DeleteIcon.setIcon(Images.changeSize(new ImageIcon("Images\\delete_icon_final.png", "delete_final"), 60, 60));
          
          String level = cmb_level.getSelectedItem().toString();
          String role = cmb_role.getSelectedItem().toString();
          registrant.setLastName(tf_lname.getText());
          registrant.setFirstName(tf_fname.getText());
          registrant.setLevel(level);
          registrant.setSchool(tf_school.getText());
          registrant.setRole(role);
          
//          String img ="Photos\\" + jTable1.getValueAt(row,0).toString() + "_" + jTable1.getValueAt(row,1).toString() + ".jpg";
//          String old = "Photos\\" + jTable1.getValueAt(row,0).toString() + "_" + jTable1.getValueAt(row,1).toString() + ".jpg";
          
          String img = jTable1.getValueAt(row, 5).toString();
          String old = jTable1.getValueAt(row, 5).toString();
          registrant.setImgLink(img);
          oldLink = old;
       
       }
      
    }//GEN-LAST:event_jTable1MouseClicked

    private void lbl_PrintIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_PrintIconMouseClicked
        // TODO add your handling code here:
       MessageFormat msg = new MessageFormat("OFFICIAL LIST OF PARTICIPANTS");
       try{
           boolean complete = jTable1.print(JTable.PrintMode.NORMAL, msg, null);
           if(complete)
              JOptionPane.showMessageDialog(this,"Done Printing", "Information",JOptionPane.INFORMATION_MESSAGE);
           else
              JOptionPane.showMessageDialog(this,"Printing!","Printer",JOptionPane.ERROR_MESSAGE);
      }
      catch(PrinterException e){
           JOptionPane.showMessageDialog(this,e,"Error Message",JOptionPane.ERROR_MESSAGE);
      }
    }//GEN-LAST:event_lbl_PrintIconMouseClicked
 
    public void clear(){
        tf_lname.setText("");
        tf_fname.setText("");
        tf_school.setText("");
        cmb_level.setSelectedIndex(0);
        cmb_role.setSelectedIndex(0);
    }

    public void setFieldsEnable(boolean enable){
        tf_lname.setEnabled(enable);
        tf_fname.setEnabled(enable);
        tf_school.setEnabled(enable);
        cmb_level.setEnabled(enable);
        cmb_role.setEnabled(enable);
    }    
    
    public void update(){
        String level = cmb_level.getSelectedItem().toString();
        String role = cmb_role.getSelectedItem().toString();
             
        String img = "Photos\\" + tf_lname.getText() + "_" + tf_fname.getText() + ".jpg";
        
        File old = new File(oldLink);
        File newLink = new File(img);
        boolean b = old.renameTo(newLink);
        
        System.out.println("");
        System.out.println("Old: " + old);
        System.out.println("New: " + newLink);
        System.out.println("Rename: " + b);
        System.out.println("");
        
        //registrant.updateRegistrant(tf_lname.getText() , tf_fname.getText() ,level ,tf_school.getText() ,role, img);
        try{
            RegistrantSockets socket = new RegistrantSockets(10000);
            socket.sendSocketUpdate(registrant, new Registrant(tf_lname.getText() , tf_fname.getText() ,level ,tf_school.getText() ,role, img));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        clear();
        updateTable();
    }
    
    public void delete(){
        String level = cmb_level.getSelectedItem().toString();
        String role = cmb_role.getSelectedItem().toString();
        
        File file = new File("Photos\\"+ tf_lname.getText() + "_" + tf_fname.getText() + ".jpg");
        file.delete();
        
        lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon("Images\\college.png"), 260, 190));
        String img = "Photos\\" + tf_lname.getText() + "_" + tf_fname.getText() + ".jpg";

        //registrant.deleteRegistrant(tf_lname.getText() , tf_fname.getText() ,level ,tf_school.getText() ,role,img);
        //clear();
        //updateTable();  
        
        try{
            RegistrantSockets socket = new RegistrantSockets(10000);
            socket.sendSocketDelete(registrant);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        MySql database = new MySql();
        database.updateTable(dm);
        clear();
        
        System.out.println("Deleted Successfully...");
    }
    
    public void add(){
        registrant.setLastName(tf_lname.getText());
        registrant.setFirstName(tf_fname.getText());
        registrant.setSchool(tf_school.getText());
        registrant.setImgLink("Photos\\" + tf_lname.getText() + "_" + tf_fname.getText() + ".jpg");
        String level = cmb_level.getSelectedItem().toString();
        registrant.setLevel(level);
        String role = cmb_role.getSelectedItem().toString();
        registrant.setRole(role);
        //registrant.addRegistrant();
        
        RegistrantSockets socket = null;
        try {
            socket = new RegistrantSockets(10000);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.sendSocketAdd(registrant);
        
        MySql database = new MySql();
        //database.addRegistrant(registrant);
        database.updateTable(dm);
        
        clear();
        /* 
        clearRow();
        updateTable();     
        */
    }
    
    private void lbl_UpdateIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_UpdateIconMouseClicked
        // TODO add your handling code here:     
        if(((ImageIcon)lbl_UpdateIcon.getIcon()).getDescription().equals("edit_initial")){
            //clear();
            lbl_UpdateIcon.setIcon(Images.changeSize(new ImageIcon("Images\\edit_icon_final.png", "edit_final"), 60, 60));
            lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_white.png", "camera_final"), 60, 60));
            lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon("Images\\folder_white.png", "camera_final"), 60, 60));
            lbl_DeleteIcon.setIcon(Images.changeSize(new ImageIcon("Images\\delete_icon.png", "delete_final"), 60, 60));
            
            setFieldsEnable(true);
        }
        else if(((ImageIcon)lbl_UpdateIcon.getIcon()).getDescription().equals("edit_final")){
            lbl_UpdateIcon.setIcon(Images.changeSize(new ImageIcon("Images\\edit_icon_blue.png", "edit_initial"), 60, 60));
            lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_blue.png", "camera_initial"), 60, 60));
            lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon("Images\\folder_blue.png", "folder_initial"), 60, 60));
            lbl_DeleteIcon.setIcon(Images.changeSize(new ImageIcon("Images\\delete_icon.png", "delete_initial"), 60, 60));
            
            update();
            clear();
            setFieldsEnable(false);
        }
    }//GEN-LAST:event_lbl_UpdateIconMouseClicked

    private void lbl_DeleteIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DeleteIconMouseClicked
        // TODO add your handling code here:
        
        if(((ImageIcon)lbl_DeleteIcon.getIcon()).getDescription().equals("delete_final")){
            delete();
            lbl_DeleteIcon.setIcon(Images.changeSize(new ImageIcon("Images\\delete_icon_blue.png", "delete_initial"), 60, 60));
        }
    }//GEN-LAST:event_lbl_DeleteIconMouseClicked
    
    private void tf_lnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_lnameKeyReleased
        // TODO add your handling code here:
         tf_lname.setText( tf_lname.getText().toUpperCase());
    }//GEN-LAST:event_tf_lnameKeyReleased

    private void tf_fnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_fnameKeyReleased
        // TODO add your handling code here:
         tf_fname.setText( tf_fname.getText().toUpperCase());
    }//GEN-LAST:event_tf_fnameKeyReleased

    private void tf_schoolKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_schoolKeyReleased
        // TODO add your handling code here:
         tf_school.setText( tf_school.getText().toUpperCase());
    }//GEN-LAST:event_tf_schoolKeyReleased

    private void lbl_AddIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_AddIconMouseClicked
        // TODO add your handling code here:
       //register = new Registrant();
        if(((ImageIcon)lbl_AddIcon.getIcon()).getDescription().equals("add_initial")){
            clear();
            lbl_CameraPhoto.setIcon(Images.changeSize(new ImageIcon("Images\\college.png"), 260, 190));
            lbl_AddIcon.setIcon(Images.changeSize(new ImageIcon("Images\\add_icon_final.png", "add_final"), 60, 60));
            lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_white.png", "camera_final"), 60, 60));
            lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon("Images\\folder_white.png", "folder_final"), 60, 60));
            
            setFieldsEnable(true);
        }
        else if(((ImageIcon)lbl_AddIcon.getIcon()).getDescription().equals("add_final")){
            lbl_AddIcon.setIcon(Images.changeSize(new ImageIcon("Images\\add_icon_blue.png", "add_initial"), 60, 60));
            lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon("Images\\camera_blue.png", "camera_initial"), 60, 60));
            lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon("Images\\folder_blue.png", "camera_initial"), 60, 60));
            
            add();
            clear();
            setFieldsEnable(false);
        }
    }//GEN-LAST:event_lbl_AddIconMouseClicked

    private void lbl_DescMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DescMouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_lbl_DescMouseClicked

    private void lbl_AscMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_AscMouseClicked
        // TODO add your handling code here:
     
    }//GEN-LAST:event_lbl_AscMouseClicked

    private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartServerActionPerformed
        ExecutorService pool = Executors.newFixedThreadPool(5);
        if(btnStartServer.getText().equals("START SERVER")){
            try {           
                MultithreadedSocketServer server = new MultithreadedSocketServer(pool);
                UDPServer udpServer = new UDPServer(tfSetImgPath.getText());
                btnStartServer.setText("STOP SERVER");
                pool.execute(udpServer);
                pool.execute(server);                    
            } catch (SocketException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        else if(btnStartServer.getText().equals("STOP SERVER")){
            pool.shutdown();
            btnStartServer.setText("START SERVER");
        }
        
    }//GEN-LAST:event_btnStartServerActionPerformed

    private void btnSetImgPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetImgPathActionPerformed
        // TODO add your handling code here:
        imgPathNetwork = tfSetImgPath.getText();
    }//GEN-LAST:event_btnSetImgPathActionPerformed

    
    public static void main(String args[]){
        JFrame f = new JFrame();
//        f.add(new Table(new JFrame(), new JPanel(), new CameraHolder(new JFrame(), new JPanel()));
        f.setSize(1200,850);
        f.setVisible(true);
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Jlabel;
    private javax.swing.JButton btnSetImgPath;
    private javax.swing.JButton btnStartServer;
    private javax.swing.JComboBox<String> cb_Cameras;
    private javax.swing.JComboBox<String> cmb_level;
    private javax.swing.JComboBox<String> cmb_role;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_AddIcon;
    private javax.swing.JLabel lbl_Asc;
    private javax.swing.JLabel lbl_CameraIcon;
    private javax.swing.JLabel lbl_CameraPhoto;
    private javax.swing.JLabel lbl_CameraTypeIcon;
    private javax.swing.JLabel lbl_CesafiLogo;
    private javax.swing.JLabel lbl_DeleteIcon;
    private javax.swing.JLabel lbl_Desc;
    private javax.swing.JLabel lbl_FolderIcon;
    private javax.swing.JLabel lbl_Home;
    private javax.swing.JLabel lbl_PrintIcon;
    private javax.swing.JLabel lbl_RefreshIcon;
    private javax.swing.JLabel lbl_RefreshTable;
    private javax.swing.JLabel lbl_Search;
    private javax.swing.JLabel lbl_SearchIcon;
    private javax.swing.JLabel lbl_Settings;
    private javax.swing.JLabel lbl_UpdateIcon;
    private javax.swing.JLabel lbl_ViewCameraIcon;
    private javax.swing.JPanel pnl_CameraButtons;
    private javax.swing.JPanel pnl_CameraPhoto;
    private javax.swing.JPanel pnl_CameraView;
    private javax.swing.JTextField tfSetImgPath;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_lname;
    private javax.swing.JTextField tf_school;
    private javax.swing.JTextField tf_search;
    // End of variables declaration//GEN-END:variables
}
