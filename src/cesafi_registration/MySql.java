/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import static cesafi_registration.Table.dm;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rosalijos
 */
public class MySql implements RegistrantCommands{
    private java.sql.Connection conn;
    private java.sql.Statement stmt;

    public void initDB(){
       //System.out.println("Database connected\n");  
       try {
         // Connect to the local InterBase database
         conn = DriverManager.getConnection("jdbc:mysql://localhost/event","kaizer","12345678");         
         //Create a statement
         stmt = conn.createStatement();         
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
    
    public void addRegistrant(Registrant registrant){
        initDB();
        try{
           PreparedStatement ps = conn.prepareStatement("INSERT INTO participants (lastname,firstName, level, school, role, imgLink)\n" 
                   + "VALUES (?,?,?,?,?,?)");
           ps.setString(1, registrant.getLastName().toUpperCase().toString());
           ps.setString(2, registrant.getFirstName().toUpperCase().toString());
           ps.setString(3, registrant.getLevel().toUpperCase().toString());
           ps.setString(4, registrant.getSchool().toUpperCase().toString());
           ps.setString(5, registrant.getRole().toUpperCase().toString());
           ps.setString(6, registrant.getImgLink().toUpperCase().toString());
           ps.execute();

           conn.close();
           ps.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
      }

       public void updateRegistrant(Registrant registrantOld, Registrant registrantNew){
           initDB();   
           synchronized(this){
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
                    ps.setString(1, registrantNew.getLastName().toUpperCase());
                    ps.setString(2, registrantNew.getFirstName().toUpperCase());
                    ps.setString(3, registrantNew.getLevel().toUpperCase());
                    ps.setString(4, registrantNew.getSchool().toUpperCase());
                    ps.setString(5, registrantNew.getRole().toUpperCase());
                    ps.setString(6, registrantNew.getImgLink().toUpperCase());
                    ps.setString(7, registrantOld.getLastName().toUpperCase());
                    ps.setString(8, registrantOld.getFirstName().toUpperCase());
                    ps.setString(9, registrantOld.getLevel().toUpperCase());
                    ps.setString(10, registrantOld.getSchool().toUpperCase());
                    ps.setString(11, registrantOld.getRole().toUpperCase());
                    ps.setString(12, registrantOld.getImgLink().toUpperCase());
                    ps.executeUpdate();
                    conn.close();

           }catch(Exception ex){
              ex.printStackTrace();

           }
        }

    }
    
    public void deleteRegistrant(Registrant registrant){
       initDB();   
       try{
            PreparedStatement ps = conn.prepareStatement("DELETE \n" +
                                                        "FROM participants \n" +
                                                        "WHERE lastName = ? AND "
                                                        + "firstName = ? AND "
                                                        + "level =  ?  AND "
                                                        + "school = ?  AND "
                                                        + "role = ? AND "
                                                        + "imgLink= ?");
            ps.setString(1, registrant.getLastName().toUpperCase().toString());
            ps.setString(2, registrant.getFirstName().toUpperCase().toString());
            ps.setString(3, registrant.getLevel().toUpperCase().toString());
            ps.setString(4, registrant.getSchool().toUpperCase().toString());
            ps.setString(5, registrant.getRole().toUpperCase().toString());
            ps.setString(6, registrant.getImgLink().toUpperCase().toString());   
            ps.executeUpdate();

            conn.close();
            ps.close();
       }catch(Exception ex){
            ex.printStackTrace();
       }
    }

    public void updateTable(DefaultTableModel dm){
        clearRow(dm);
        initDB();
        try{
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink \n"
                    + "FROM participants\n" + "ORDER BY lastname");
            while(resultSet.next())
               dm.addRow(new Object[]{resultSet.getString(1), 
                   resultSet.getString(2),resultSet.getString(3),
                   resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)});
            stmt.close();
            conn.close();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }   
    }
    
    private void clearRow(DefaultTableModel dm){
       if(dm.getRowCount() > 0){
         for(int i = dm.getRowCount()-1;i>-1;i--)
            dm.removeRow(i);
      }       
    }
    
    public void updateTableDesc(DefaultTableModel dm){
        clearRow(dm);
        initDB();
        try{
            Statement stmt = conn.createStatement();            
            ResultSet resultSet = stmt.executeQuery("SELECT lastName, firstName, role, level,school, imgLink\n"
                    + "FROM participants\n" + "ORDER BY lastname DESC");  
            while(resultSet.next())
                dm.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), 
                    resultSet.getString(5), resultSet.getString(6)});
            
            stmt.close();
            conn.close();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }      
    }

    
}
