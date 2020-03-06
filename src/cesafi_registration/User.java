    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import com.github.sarxos.webcam.WebcamPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.activation.MimetypesFileTypeMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Rosalijos
 */
public class User extends javax.swing.JPanel {

    /**
     * Creates new form User
     */
    private int[][] colors_Elementary = {{0, 84, 20}, {0, 117, 32}, {200, 225, 157}};
    private int[][] colors_JuniorHS = {{0, 80, 115}, {0, 112, 158}, {174, 211, 231}};
    private int[][] colors_SeniorHS = {{112, 119, 0}, {148, 145, 16}, {217, 215, 156}};
    private int[][] colors_College = {{156, 65, 15}, {168, 83, 35}, {250, 205, 138}};
    
    private String[] strings_Elementary = {"ELEMENTARY", "elementary.png", "home_elementary.png"};
    private String[] strings_JuniorHS = {"JUNIOR HS", "junior_hs.png", "home_juniorhs.png"};
    private String[] strings_SeniorHS = {"SENIOR HS", "senior_hs.png", "home_seniorhs.png"};
    private String[] strings_College = {"COLLEGE", "college.png", "home_college.png"};
    
    private String[] strings_ElementaryLevel = {"Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6"};
    private String[] strings_JuniorHSLevel = {"Grade 7", "Grade 8", "Grade 9", "Grade 10"};
    private String[] strings_SeniorHSLevel = {"Grade 11", "Grade 12"};
    private String[] strings_CollegeLevel = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
    
    private int colors[][] = null;
    private String strings[] = {"", "", ""};
    private String levels[];
    private int dept;
    
    private JFrame frame;
    private JPanel cards;
    private CameraHolder cameraHolder;
    
    private String lastName;
    private String firstName;
    private String school;
    private String level;
    private String role;
    
    private ImageIcon photo;
    private User user;
    private Registrant registrant;
    
    private final int USER_NUMBER = 0;
    
    private boolean hasPhoto = false;
    private Table table;
    private java.sql.Connection conn;
    private java.sql.Statement stmt;
    private int i = 0, j = 0, k = 0, l = 0;
    
    public User(JFrame frame, JPanel cards, CameraHolder cameraHolder) {
        initComponents();
        this.cards = cards;
        this.frame = frame;
        this.cameraHolder = cameraHolder;
        user = this;
        
        lbl_UserPhoto.setHorizontalAlignment(JLabel.CENTER);
       
        System.out.println("hello");
        
        lbl_CesafiLogo.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("cesafi logo.jpg")), 200,90));
        lbl_CameraIcon.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("camera_white.png")), 80,80));
        lbl_FolderIcon.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource("folder_white.png")), 40,40));
        
        pnl_CameraPhoto.addComponentListener(new PanelListener());
        
        initializeListeners();

        btn_Add.setEnabled(false);
        photo = new ImageIcon(this.getClass().getResource("elementary.png"));   
    }
    
    public void initializeColors(int dept){
        this.dept = dept;
        switch(dept){
            case 1:
                colors = colors_Elementary;
                strings = strings_Elementary;
                levels = strings_ElementaryLevel;
                break;
            case 2:
                colors = colors_JuniorHS;
                strings = strings_JuniorHS;
                levels = strings_JuniorHSLevel;
                break;
            case 3:
                colors = colors_SeniorHS;
                strings = strings_SeniorHS;
                levels = strings_SeniorHSLevel;
                break;
            case 4:
                colors = colors_College;
                strings = strings_College;
                levels = strings_CollegeLevel;
                break;
        }
        
        setLowerBackground(colors[2][0], colors[2][1], colors[2][2]);

        
        lbl_Title.setBackground(new Color(colors[2][0], colors[2][1], colors[2][2]));
                
        pnl_CameraButtons.setBackground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        pnl_Camera.setBorder(BorderFactory.createLineBorder(new Color(colors[0][0], colors[0][1], colors[0][2]), 10));
        btn_Add.setBackground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        btn_Clear.setBackground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        lbl_Title.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        
        pnl_Form.setBackground(new Color(colors[1][0], colors[1][1], colors[1][2]));
        
        tf_LastName.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        tf_FirstName.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        tf_School.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        cb_Level.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        cb_Role.setForeground(new Color(colors[0][0], colors[0][1], colors[0][2]));
        
        lbl_Title.setText(strings[0]);
        
        lbl_Home.setIcon(Images.changeSize(new ImageIcon(this.getClass().getResource(strings[2])),  80, 80));
        
        photo = new ImageIcon(this.getClass().getResource(strings[1]));
        lbl_UserPhoto.setIcon(Images.resizedImageBasedOnParent(photo, (Component)pnl_Camera));
        
        DefaultComboBoxModel model = (DefaultComboBoxModel) cb_Level.getModel();
        model.removeAllElements();
        
        for(String item: levels)
            model.addElement(item);
        
        cb_Level.setModel(model);
        

       
    }
    
    public void setTable(Table table){
        this.table = table;
    }
    
    public void setRegistrant(Registrant registrant){
        this.registrant = registrant;
    }
    
    public Registrant getRegistrant(){
        return registrant;
    }
    
    public void setLowerBackground(int red, int green, int blue){
        pnl_1.setBackground(new Color(red, green, blue));
        pnl_2.setBackground(new Color(red, green, blue));
        pnl_3.setBackground(new Color(red, green, blue));
        pnl_4.setBackground(new Color(red, green, blue));
        pnl_5.setBackground(new Color(red, green, blue));
        pnl_6.setBackground(new Color(red, green, blue));
        pnl_7.setBackground(new Color(red, green, blue));
        pnl_8.setBackground(new Color(red, green, blue));
        pnl_9.setBackground(new Color(red, green, blue));
        pnl_10.setBackground(new Color(red, green, blue));       
    }
     public void initDB(){
       //System.out.println("Database connected\n");  
       try {
         // Connect to the local InterBase database
         conn = DriverManager.getConnection("jdbc:mysql://localhost/event","kaizer","12345678");
         
         //Create a statement
         stmt = conn.createStatement();
         
         //System.out.println("Database connected successfully...\n");
       }
       catch (Exception ex) {
           System.out.println(ex);
       }
    }
    public void initializeListeners(){
        
        //document listeners
        tf_LastName.getDocument().addDocumentListener(new TextHandler());
        tf_FirstName.getDocument().addDocumentListener(new TextHandler());
        tf_School.getDocument().addDocumentListener(new TextHandler());    
        
        //lbl_UserPhoto.addPropertyChangeListener(new PropertyHandler());
        
        //component listeners
        pnl_CameraPhoto.addComponentListener(new PanelListener());
        
        //mouse listeners
        lbl_Home.addMouseListener(new LabelListener());
        lbl_CameraIcon.addMouseListener(new LabelListener());
        lbl_FolderIcon.addMouseListener(new LabelListener());
        
        //action listeners
        btn_Add.addActionListener(new ButtonListener());
        btn_Clear.addActionListener(new ButtonListener());
    }
    
    public void setIcons(){
        
    }
    
    public void setDepartment(int dept){
        this.dept = dept;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setCameraPhoto(ImageIcon photo){
        this.photo = photo;
        lbl_UserPhoto.setIcon(Images.resizedImageBasedOnParent(photo, pnl_CameraPhoto));
        hasPhoto = true;
        
        i = 0;
        j = 0;
        l = 0;
        k = 0;
        
        if(!tf_School.getText().equals(""))
            i = 1;
        if(!tf_LastName.getText().equals(""))
            j = 1;
        if(!tf_FirstName.getText().equals(""))
            k = 1;  

        if(hasPhoto == true)
            l = 1;

        if(i + j + k + l == 4){
           btn_Add.setEnabled(true);
           
        }
        else    
           btn_Add.setEnabled(false);
    }
    
    
    public void setAddButtonEnable(boolean bool){
        btn_Add.setEnabled(bool);
    }
    
    public String setSchool(){
        return school;
    }
    
    public int getDepartment(){
        return dept;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getFirstName(){
        return firstName;
    }
        
    public String getSchool(){
        return school;
    }
    
    public String getUserImagePath(){
        return "Photos\\" + tf_LastName.getText() + "_" + tf_FirstName.getText() + ".jpg";
    }
    
    public int[][] getColors(){
        return colors;
    }
    
    public void setDefault(){
        tf_LastName.setText("");
        tf_FirstName.setText("");
        tf_School.setText("");
        cb_Level.setSelectedIndex(0);
        cb_Role.setSelectedIndex(0);
        
        lbl_UserPhoto.setIcon(Images.resizedImageBasedOnParent(new ImageIcon(this.getClass().getResource(strings[1])), pnl_CameraPhoto));
    }
    
    public void setInputEnable(boolean bool){
        tf_LastName.setEnabled(bool);
        tf_FirstName.setEnabled(bool);
        tf_School.setEnabled(bool);
        cb_Role.setEnabled(bool);
        cb_Level.setEnabled(bool);
    }
    
    public void getInputData(){
        firstName = tf_FirstName.getText();
        lastName = tf_LastName.getText();
        school = tf_School.getText();
        level = cb_Level.getSelectedItem().toString();
        role = cb_Role.getSelectedItem().toString();
    }
    
    class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() == btn_Clear){
                setDefault();
                setInputEnable(true);
                hasPhoto = false;
            }
            else if(ae.getSource() == btn_Add){
                setInputEnable(false);
                hasPhoto = false;
                
                registrant.setLastName(tf_LastName.getText());
                registrant.setFirstName(tf_FirstName.getText());
                registrant.setSchool(tf_School.getText());
                registrant.setLevel(cb_Level.getSelectedItem().toString());
                registrant.setRole(cb_Role.getSelectedItem().toString());

                try{
                    RegistrantSockets socket = new RegistrantSockets(10000);
                    socket.sendSocketAdd(registrant);
                }catch(Exception e){
                    System.out.println("Hello");
                    e.printStackTrace();
                }
                table.updateTable();
                
                btn_Add.setEnabled(false);
                System.out.println("end");
            }
            
        }
    }
    
    class PropertyHandler implements PropertyChangeListener{
        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            i = 0;
            j = 0;
            l = 0;
            k = 0;
        
            if(!tf_School.getText().equals(""))
                i++;
            if(!tf_LastName.getText().equals(""))
                j++;
            if(!tf_FirstName.getText().equals(""))
                k++;  
            
            if(hasPhoto == true)
                l++;
            
            if(i + j + k + l == 4){
               btn_Add.setEnabled(true);
            }
            else    
               btn_Add.setEnabled(false);         
        }
    }
    
    class PanelListener implements ComponentListener{
        @Override
        public void componentResized(ComponentEvent ce) {
            lbl_UserPhoto.setIcon(Images.resizedImageBasedOnParent(photo, (Component)ce.getSource()));
        }

        @Override
        public void componentMoved(ComponentEvent ce) {}

        @Override
        public void componentShown(ComponentEvent ce) {}

        @Override
        public void componentHidden(ComponentEvent ce) {}
        
    }
    
    class LabelListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent me) {
            CardLayout c = (CardLayout)cards.getLayout();
            if(me.getSource() == lbl_Home)
                c.show(cards, "home");
            else if(me.getSource() == lbl_CameraIcon){
                getInputData();
                 if(!tf_LastName.getText().equals("") && !tf_FirstName.getText().equals("")){
                registrant.setLastName(tf_LastName.getText());
                registrant.setFirstName(tf_FirstName.getText());
                
                cameraHolder.setRegistrant(registrant);
                cameraHolder.setUser(user);
                cameraHolder.setCaller(USER_NUMBER);
                cameraHolder.startDisplay();
                cameraHolder.showCountdown();
                
                CardLayout cm = (CardLayout)cameraHolder.getLayout();
                cm.show(cameraHolder, "cameraHolder");
                c.show(cards, "camera");}
            }
            else if(me.getSource() == lbl_FolderIcon){
                try {
                    JFileChooser fileChooser = new JFileChooser("C:\\Users\\Rosalijos\\Documents\\Cesafi_Registration\\Photos\\");
                    int result = fileChooser.showOpenDialog(user);
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
                                lbl_UserPhoto.setIcon(Images.changeSize(new ImageIcon(path), d.width , d.height));
                                registrant.setImgLink(path);
                                
                                i = 0;
                                j = 0;
                                l = 0;
                                k = 0;
                                
                                hasPhoto = true;
                                if(!tf_School.getText().equals(""))
                                    i++;
                                if(!tf_LastName.getText().equals(""))
                                    j++;
                                if(!tf_FirstName.getText().equals(""))
                                    k++;  

                                if(hasPhoto == true)
                                    l++;

                                if(i + j + k + l == 4){
                                   btn_Add.setEnabled(true);
                                }
                                else    
                                   btn_Add.setEnabled(false);
                                
                            }
                            else{ 
                                JOptionPane.showMessageDialog(user, "The chosen file type is not an image.", "Warning", JOptionPane.WARNING_MESSAGE);
                                System.out.println("It's NOT an image");
                            }

                        } 
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
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
    
    class TextHandler implements DocumentListener{
        public void changedUpdate(DocumentEvent e) {
           warn();
         }
         public void removeUpdate(DocumentEvent e) {
           warn();
         }
         public void insertUpdate(DocumentEvent e) {
           warn();
         }
         public void warn() {
            i = 0;
            j = 0;
            l = 0;
            k = 0;
            
            if(!tf_School.getText().equals(""))
                i = 1;
            if(!tf_LastName.getText().equals(""))
                j = 1;
            if(!tf_FirstName.getText().equals(""))
                k = 1;  
            
            if(hasPhoto == true)
                l = 1;
            
            if(i + j + k + l == 4){
               btn_Add.setEnabled(true);
            }
            else    
               btn_Add.setEnabled(false);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lbl_CesafiLogo = new javax.swing.JLabel();
        lbl_Home = new javax.swing.JLabel();
        pnl_4 = new javax.swing.JPanel();
        pnl_5 = new javax.swing.JPanel();
        pnl_1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        pnl_2 = new javax.swing.JPanel();
        pnl_8 = new javax.swing.JPanel();
        btn_Add = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pnl_10 = new javax.swing.JPanel();
        pnl_9 = new javax.swing.JPanel();
        btn_Clear = new javax.swing.JButton();
        pnl_6 = new javax.swing.JPanel();
        pnl_7 = new javax.swing.JPanel();
        lbl_Title = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jpanel = new javax.swing.JPanel();
        pnl_3 = new javax.swing.JPanel();
        pnl_Camera = new javax.swing.JPanel();
        pnl_CameraButtons = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbl_CameraIcon = new javax.swing.JLabel();
        lbl_FolderIcon = new javax.swing.JLabel();
        pnl_CameraPhoto = new javax.swing.JPanel();
        lbl_UserPhoto = new javax.swing.JLabel();
        pnl_Form = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_School = new javax.swing.JTextField();
        tf_LastName = new javax.swing.JTextField();
        tf_FirstName = new javax.swing.JTextField();
        cb_Level = new javax.swing.JComboBox<>();
        cb_Role = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1200, 720));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1524, 150));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(220, 150));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel11, java.awt.BorderLayout.LINE_END);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(220, 150));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12, java.awt.BorderLayout.LINE_START);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.GridLayout(1, 2));
        jPanel13.add(lbl_CesafiLogo);

        lbl_Home.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_HomeMouseClicked(evt);
            }
        });
        jPanel13.add(lbl_Home);

        jPanel1.add(jPanel13, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnl_4.setBackground(new java.awt.Color(200, 225, 157));
        pnl_4.setPreferredSize(new java.awt.Dimension(220, 620));

        javax.swing.GroupLayout pnl_4Layout = new javax.swing.GroupLayout(pnl_4);
        pnl_4.setLayout(pnl_4Layout);
        pnl_4Layout.setHorizontalGroup(
            pnl_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        pnl_4Layout.setVerticalGroup(
            pnl_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        add(pnl_4, java.awt.BorderLayout.LINE_START);

        pnl_5.setBackground(new java.awt.Color(200, 225, 157));
        pnl_5.setPreferredSize(new java.awt.Dimension(220, 620));

        javax.swing.GroupLayout pnl_5Layout = new javax.swing.GroupLayout(pnl_5);
        pnl_5.setLayout(pnl_5Layout);
        pnl_5Layout.setHorizontalGroup(
            pnl_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        pnl_5Layout.setVerticalGroup(
            pnl_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        add(pnl_5, java.awt.BorderLayout.LINE_END);

        pnl_1.setBackground(new java.awt.Color(200, 225, 157));

        javax.swing.GroupLayout pnl_1Layout = new javax.swing.GroupLayout(pnl_1);
        pnl_1.setLayout(pnl_1Layout);
        pnl_1Layout.setHorizontalGroup(
            pnl_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1524, Short.MAX_VALUE)
        );
        pnl_1Layout.setVerticalGroup(
            pnl_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        add(pnl_1, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(240, 240, 40));
        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.rowHeights = new int[] {0};
        jPanel5Layout.columnWeights = new double[] {1.0};
        jPanel5Layout.rowWeights = new double[] {1.0};
        jPanel5.setLayout(jPanel5Layout);

        jPanel8.setLayout(new java.awt.BorderLayout());

        pnl_2.setBackground(new java.awt.Color(200, 225, 157));
        pnl_2.setPreferredSize(new java.awt.Dimension(722, 130));
        pnl_2.setLayout(new java.awt.BorderLayout());

        pnl_8.setBackground(new java.awt.Color(200, 225, 157));
        pnl_8.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 1, 1, 1));
        pnl_8.setPreferredSize(new java.awt.Dimension(430, 90));
        pnl_8.setRequestFocusEnabled(false);
        pnl_8.setLayout(new java.awt.BorderLayout());

        btn_Add.setBackground(new java.awt.Color(0, 84, 20));
        btn_Add.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        btn_Add.setForeground(new java.awt.Color(255, 255, 255));
        btn_Add.setText("ADD ENTRY");
        btn_Add.setMaximumSize(new java.awt.Dimension(79, 15));
        btn_Add.setMinimumSize(new java.awt.Dimension(79, 15));
        btn_Add.setPreferredSize(new java.awt.Dimension(79, 55));
        btn_Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AddMouseClicked(evt);
            }
        });
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        pnl_8.add(btn_Add, java.awt.BorderLayout.PAGE_START);

        pnl_2.add(pnl_8, java.awt.BorderLayout.LINE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        pnl_10.setBackground(new java.awt.Color(200, 225, 157));
        pnl_10.setPreferredSize(new java.awt.Dimension(20, 130));

        javax.swing.GroupLayout pnl_10Layout = new javax.swing.GroupLayout(pnl_10);
        pnl_10.setLayout(pnl_10Layout);
        pnl_10Layout.setHorizontalGroup(
            pnl_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        pnl_10Layout.setVerticalGroup(
            pnl_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        jPanel2.add(pnl_10, java.awt.BorderLayout.LINE_END);

        pnl_9.setBackground(new java.awt.Color(200, 225, 157));
        pnl_9.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 1, 59, 1));
        pnl_9.setLayout(new java.awt.BorderLayout());

        btn_Clear.setBackground(new java.awt.Color(0, 84, 20));
        btn_Clear.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        btn_Clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_Clear.setText("CLEAR");
        btn_Clear.setPreferredSize(new java.awt.Dimension(150, 65));
        btn_Clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ClearMouseClicked(evt);
            }
        });
        pnl_9.add(btn_Clear, java.awt.BorderLayout.LINE_END);

        jPanel2.add(pnl_9, java.awt.BorderLayout.CENTER);

        pnl_2.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel8.add(pnl_2, java.awt.BorderLayout.PAGE_END);

        pnl_6.setBackground(new java.awt.Color(200, 225, 157));
        pnl_6.setLayout(new java.awt.BorderLayout());

        pnl_7.setBackground(new java.awt.Color(200, 225, 157));
        pnl_7.setPreferredSize(new java.awt.Dimension(430, 126));
        pnl_7.setLayout(new java.awt.BorderLayout());

        lbl_Title.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(0, 84, 20));
        lbl_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Title.setText("ELEMENTARY");
        lbl_Title.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnl_7.add(lbl_Title, java.awt.BorderLayout.CENTER);

        pnl_6.add(pnl_7, java.awt.BorderLayout.LINE_END);

        jPanel8.add(pnl_6, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBackground(new java.awt.Color(240, 240, 40));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jpanel.setInheritsPopupMenu(true);
        jpanel.setPreferredSize(new java.awt.Dimension(450, 0));
        jpanel.setLayout(new java.awt.BorderLayout());

        pnl_3.setBackground(new java.awt.Color(200, 225, 157));
        pnl_3.setPreferredSize(new java.awt.Dimension(20, 314));

        javax.swing.GroupLayout pnl_3Layout = new javax.swing.GroupLayout(pnl_3);
        pnl_3.setLayout(pnl_3Layout);
        pnl_3Layout.setHorizontalGroup(
            pnl_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        pnl_3Layout.setVerticalGroup(
            pnl_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        jpanel.add(pnl_3, java.awt.BorderLayout.LINE_START);

        pnl_Camera.setBackground(new java.awt.Color(20, 240, 240));
        pnl_Camera.setLayout(new java.awt.BorderLayout());

        pnl_CameraButtons.setBackground(new java.awt.Color(0, 84, 20));
        pnl_CameraButtons.setPreferredSize(new java.awt.Dimension(430, 90));
        pnl_CameraButtons.setLayout(new java.awt.GridLayout(1, 3));
        pnl_CameraButtons.add(jLabel6);

        lbl_CameraIcon.setBackground(new java.awt.Color(200, 225, 157));
        lbl_CameraIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnl_CameraButtons.add(lbl_CameraIcon);

        lbl_FolderIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pnl_CameraButtons.add(lbl_FolderIcon);

        pnl_Camera.add(pnl_CameraButtons, java.awt.BorderLayout.PAGE_END);

        pnl_CameraPhoto.setLayout(new java.awt.BorderLayout());
        pnl_CameraPhoto.add(lbl_UserPhoto, java.awt.BorderLayout.CENTER);

        pnl_Camera.add(pnl_CameraPhoto, java.awt.BorderLayout.CENTER);

        jpanel.add(pnl_Camera, java.awt.BorderLayout.CENTER);

        jPanel14.add(jpanel, java.awt.BorderLayout.LINE_END);

        pnl_Form.setBackground(new java.awt.Color(0, 117, 32));
        pnl_Form.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 25, 50, 45));
        pnl_Form.setForeground(new java.awt.Color(255, 255, 255));
        pnl_Form.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SCHOOL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LAST NAME");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FIRST NAME");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("LEVEL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ROLE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(jLabel5, gridBagConstraints);

        tf_School.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        tf_School.setForeground(new java.awt.Color(0, 84, 20));
        tf_School.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_SchoolKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.9;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(tf_School, gridBagConstraints);

        tf_LastName.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        tf_LastName.setForeground(new java.awt.Color(0, 84, 20));
        tf_LastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_LastNameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.9;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(tf_LastName, gridBagConstraints);

        tf_FirstName.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        tf_FirstName.setForeground(new java.awt.Color(0, 84, 20));
        tf_FirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_FirstNameActionPerformed(evt);
            }
        });
        tf_FirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_FirstNameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.9;
        gridBagConstraints.weighty = 1.0;
        pnl_Form.add(tf_FirstName, gridBagConstraints);

        cb_Level.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        cb_Level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_Level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_LevelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnl_Form.add(cb_Level, gridBagConstraints);

        cb_Role.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        cb_Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Participant", "Coach" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnl_Form.add(cb_Role, gridBagConstraints);

        jPanel14.add(pnl_Form, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel14, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel5.add(jPanel8, gridBagConstraints);

        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
 
    private void tf_FirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_FirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_FirstNameActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
      //  registrant = new Registrant();
        
        
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_AddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AddMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_AddMouseClicked

    private void tf_SchoolKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_SchoolKeyReleased
        // TODO add your handling code here:
         tf_School.setText( tf_School.getText().toUpperCase());
               Set<String> s=new TreeSet<String>();     
        initDB(); 
   try{
   ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT school FROM participants ORDER BY school");


   while(resultSet.next())
   s.add(resultSet.getString(1));
   stmt.close();
   }

   catch(Exception ex){
      ex.printStackTrace();
   }
         if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE||evt.getKeyCode()==KeyEvent.VK_DELETE)
         {
          
         }
          else
        {   
            String to_check=tf_School.getText();
            int to_check_len=to_check.length();
            for(String data:s)
            {
                String check_from_data="";
                for(int i=0;i<to_check_len;i++)
                {
                    if(to_check_len<=data.length())
                    {
                        check_from_data = check_from_data+data.charAt(i);
                    }
                }
                //System.out.print(check_from_data);
                if(check_from_data.equals(to_check))
                {
                    //System.out.print("Found");
                   tf_School.setText(data);
                    tf_School.setSelectionStart(to_check_len);
                   tf_School.setSelectionEnd(data.length());
                    break;
                }
            }
        }
    }//GEN-LAST:event_tf_SchoolKeyReleased

    private void tf_LastNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_LastNameKeyReleased
        // TODO add your handling code here:
          tf_LastName.setText( tf_LastName.getText().toUpperCase());
    }//GEN-LAST:event_tf_LastNameKeyReleased

    private void tf_FirstNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_FirstNameKeyReleased
        // TODO add your handling code here:
          tf_FirstName.setText( tf_FirstName.getText().toUpperCase());
    }//GEN-LAST:event_tf_FirstNameKeyReleased

    private void lbl_HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_HomeMouseClicked
        // TODO add your handling code here:
         tf_LastName.setText("");
         tf_FirstName.setText("");
         tf_School.setText("");
         cb_Level.setSelectedIndex(0);
         cb_Role.setSelectedIndex(0);
    }//GEN-LAST:event_lbl_HomeMouseClicked

    private void btn_ClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClearMouseClicked
        // TODO add your handling code here:
         tf_LastName.setText("");
         tf_FirstName.setText("");
         tf_School.setText("");
         cb_Level.setSelectedIndex(0);
         cb_Role.setSelectedIndex(0);
    }//GEN-LAST:event_btn_ClearMouseClicked

    private void cb_LevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_LevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_LevelActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Clear;
    private javax.swing.JComboBox<String> cb_Level;
    private javax.swing.JComboBox<String> cb_Role;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jpanel;
    private javax.swing.JLabel lbl_CameraIcon;
    private javax.swing.JLabel lbl_CesafiLogo;
    private javax.swing.JLabel lbl_FolderIcon;
    private javax.swing.JLabel lbl_Home;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JLabel lbl_UserPhoto;
    private javax.swing.JPanel pnl_1;
    private javax.swing.JPanel pnl_10;
    private javax.swing.JPanel pnl_2;
    private javax.swing.JPanel pnl_3;
    private javax.swing.JPanel pnl_4;
    private javax.swing.JPanel pnl_5;
    private javax.swing.JPanel pnl_6;
    private javax.swing.JPanel pnl_7;
    private javax.swing.JPanel pnl_8;
    private javax.swing.JPanel pnl_9;
    private javax.swing.JPanel pnl_Camera;
    private javax.swing.JPanel pnl_CameraButtons;
    private javax.swing.JPanel pnl_CameraPhoto;
    private javax.swing.JPanel pnl_Form;
    private javax.swing.JTextField tf_FirstName;
    private javax.swing.JTextField tf_LastName;
    private javax.swing.JTextField tf_School;
    // End of variables declaration//GEN-END:variables
}
