/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import static cesafi_registration.Table.dm;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rosalijos
 */


public class Registrant /*extends JFrame*/ implements Serializable{
    private static final long SerialVersionUID = 100L;
    
    private String lastName = "0000";
    private String firstName = "0000";
    private String level;
    private String school;
    private String role;
    private String imgLink;
//    private java.sql.Connection conn;
//    private java.sql.Statement stmt;
    
    public Registrant(){
        //initDB();
        //createRegistrant();
    }
    
    public Registrant(String lastName, String firstName, String level, String school, String role, String imgLink){   
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
        this.school = school;
        this.role = role;
        this.imgLink = imgLink;
    }
    
    public boolean isAllHaveValues(){
        boolean hasValue = true;       
        if(lastName.equals("") && lastName.equals(firstName) && lastName.equals(level) && lastName.equals(school) && lastName.equals(role))
            hasValue = false;
        return hasValue;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLevel(String level){
        this.level = level;
    }
    public void setSchool(String school){
        this.school = school;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setImgLink(String imgLink){
        this.imgLink=imgLink;
    }
     public String getLastName(){
        return lastName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    public String getLevel(){
        return level;
    }
    public String getSchool(){
        return school;
    }  
    public String getRole(){
        return role;
    }
    public String getImgLink(){
        return imgLink;
    }
    
    /*
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
    
    public void createRegistrant(){
       String ddl = "CREATE TABLE IF NOT EXISTS participants(\n" +
                       "   lastName varchar(30),\n" +
                       "   firstName varchar(30),\n" +
                       "   level varchar(20),\n" +
                       "  school varchar(50),\n" +
                       "   role varchar(20),\n" +
                        "  imgLink	varchar(1024));";
         try{
             
         if(stmt != null)
            stmt.executeUpdate(ddl);

      }catch(SQLException ex){
         ex.printStackTrace();
      }
       catch(Exception ex){
          ex.printStackTrace();
       }
    }
    */
    
    /*
    public void addRegistrant(){
     initDB();
        try{
         //conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/event","root","");

            PreparedStatement ps = conn.prepareStatement("INSERT INTO participants (lastname,firstName, level, school, role, imgLink)\n" + "VALUES (?,?,?,?,?,?)");
            ps.setString(1, lastName.toUpperCase().toString());
            ps.setString(2, firstName.toUpperCase().toString());
            ps.setString(3, level.toUpperCase().toString());
            ps.setString(4, school.toUpperCase().toString());
            ps.setString(5, role.toUpperCase().toString());
            ps.setString(6, imgLink.toUpperCase().toString());
            ps.execute();

            conn.close();
            ps.close();

            JOptionPane.showMessageDialog(this,"Successfully Registered", "Status",JOptionPane.INFORMATION_MESSAGE);  

       }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex,"Error Message",JOptionPane.ERROR_MESSAGE);
       }
   }
    */
    
    /*
public void updateRegistrant(String lastNames, String firstNames, String levels, String schools, String roles, String imgLinks){
   initDB();   
   try{
      PreparedStatement ps = conn.prepareStatement("UPDATE participants\n" +
                                                "SET lastName = ?,\n" +
                                                "    firstName = ?,\n" +
                                                "    level =  ?,\n" +
                                                "    school = ?,\n" +
                                                "    role = ?,\n" +
                                                "    imgLink= ? \n" +
                                                "WHERE lastName = ? AND "
                                                + "firstName = ? AND "
                                                + "level =  ?  AND "
                                                + "school = ?  AND "
                                                + "role = ? AND "
                                                + "imgLink= ?");
    ps.setString(1, lastNames.toUpperCase().toString());
    ps.setString(2, firstNames.toUpperCase().toString());
    ps.setString(3, levels.toUpperCase().toString());
    ps.setString(4, schools.toUpperCase().toString());
    ps.setString(5, roles.toUpperCase().toString());
    ps.setString(6, imgLinks.toUpperCase().toString());
     ps.setString(7, lastName.toUpperCase().toString());
    ps.setString(8, firstName.toUpperCase().toString());
    ps.setString(9, level.toUpperCase().toString());
    ps.setString(10, school.toUpperCase().toString());
    ps.setString(11, role.toUpperCase().toString());
    ps.setString(12, imgLink.toUpperCase().toString());
    ps.executeUpdate();
   conn.close();
   JOptionPane.showMessageDialog(this,"Information Updated!", "Status",JOptionPane.INFORMATION_MESSAGE);  
 
   }catch(Exception ex){
      JOptionPane.showMessageDialog(this,ex,"Error Message",JOptionPane.ERROR_MESSAGE);
     
   }
    
   }
    public void deleteRegistrant(String lastNames, String firstNames, String levels, String schools, String roles, String imgLinks){
        initDB();   
       try{
      //   conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/event","root","");
          PreparedStatement ps = conn.prepareStatement("DELETE \n" +
                                                    "FROM participants \n" +
                                                    "WHERE lastName = ? AND "
                                                    + "firstName = ? AND "
                                                    + "level =  ?  AND "
                                                    + "school = ?  AND "
                                                    + "role = ? AND "
                                                    + "imgLink= ?");
        ps.setString(1, lastNames.toUpperCase().toString());
        ps.setString(2, firstNames.toUpperCase().toString());
        ps.setString(3, levels.toUpperCase().toString());
        ps.setString(4, schools.toUpperCase().toString());
        ps.setString(5, roles.toUpperCase().toString());
        ps.setString(6, imgLinks.toUpperCase().toString());   
        ps.executeUpdate();

        conn.close();
        ps.close();

       JOptionPane.showMessageDialog(this,"Information Deleted!", "Status",JOptionPane.INFORMATION_MESSAGE);  

       }catch(Exception ex){
          JOptionPane.showMessageDialog(this,ex,"Error Message",JOptionPane.ERROR_MESSAGE);

       }
    }

    public void updateTable(DefaultTableModel dm){
        //clearRow();
        initDB();
        try{
            //ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school \n" + "FROM participants\n" + "ORDER BY lastname");  
      //  conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/event","root","");
            Statement stmt = conn.createStatement();
           
            ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink \n" + "FROM participants\n" + "ORDER BY lastname");
            while(resultSet.next())
               dm.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});
            stmt.close();
            conn.close();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }   
   }
    
    public void updateTableDesc(DefaultTableModel dm){
        //clearRow();
        initDB();
        try{
       //  conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/event","root","");
            Statement stmt = conn.createStatement();
            
            ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink\n" + "FROM participants\n" + "ORDER BY lastname DESC");  
            while(resultSet.next())
                dm.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});
            
            stmt.close();
            conn.close();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
      
    }

*/
    
}

