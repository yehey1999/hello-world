/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Remote;

import cesafi_registration.Registrant;
import java.rmi.Remote;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rosalijos
 */
public interface RMIRegistrant extends Remote{
    void initDB();
    void createRegistrant();
    void addRegistrant(Registrant registrant);
    void updateRegistrant(Registrant registrantOld, Registrant registrantNew);
    void deleteRegistrant(Registrant registrant);
    void updateTable(DefaultTableModel dm);
    void updateTableDesc(DefaultTableModel dm);    
}
