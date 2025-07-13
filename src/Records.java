import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Records {
    CreateFrame RecordFrame = new CreateFrame(1080,720 , "Records");

    String[] patientcolumns = {"Id" , "Name" , "DOB" , "Details" , "Diagnosis" , "Medication" , "RecordDate"};
    String[] recordscolumns ={"Diagnosis" , "Medication" , "RecordDate"};

    DefaultTableModel patientmodel = new DefaultTableModel();

    String url = "jdbc:postgresql://localhost:5432/Patient_Records";
    String user = "postgres";
    String password = "suyog123";
    public void ShowRecordWindow(){
        ShowPatientData();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        AddPatient(panel);
        ClearPatient(panel);
    }
    public void ShowPatientData(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(720 , 720));
        panel.setLayout(new BorderLayout(10,10));
        panel.setBackground(Color.pink);

        patientmodel.setColumnIdentifiers(patientcolumns);
        JTable patientTable = new JTable(patientmodel);

        for(int i=0 ; i< 3 ; i++){
            patientTable.removeColumn(patientTable.getColumnModel().getColumn(4));
        }

        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement()
        )
        {
            String query = "SELECT * from Patient_Details";

            try(ResultSet rs = stmt.executeQuery(query)) {
                while(rs.next()){
                    int id = rs.getInt(patientcolumns[0]);
                    String name = rs.getString(patientcolumns[1]);
                    String dob = rs.getString(patientcolumns[2]);
                    String diag = rs.getString(recordscolumns[0]);
                    String medcation = rs.getString(recordscolumns[1]);
                    String rcdDate = rs.getString(recordscolumns[2]);
                    patientmodel.addRow(new Object[]{id,name,dob,"Details",diag,medcation,rcdDate});
                }
            }

            patientTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
            patientTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),"Details" , patientmodel));

            JScrollPane scrollPane = new JScrollPane(patientTable);
            panel.add(scrollPane,BorderLayout.CENTER);
            RecordFrame.add(panel);  // You can also use BorderLayout.CENTER if needed
            RecordFrame.setVisible(true);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void RefreshPatientTable(){
        patientmodel.setRowCount(0);

        try(Connection conn = DriverManager.getConnection(url , user , password);
            Statement stmnt = conn.createStatement()
        ){
            String query = "Select * from patient_details";
            try (ResultSet rs = stmnt.executeQuery(query)){
                while(rs.next()){
                    int id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    String DOB = rs.getString("DOB");
                    String diag = rs.getString(recordscolumns[0]);
                    String medcation = rs.getString(recordscolumns[1]);
                    String rcdDate = rs.getString(recordscolumns[2]);
                    patientmodel.addRow(new Object[]{id,name,DOB,"Details",diag,medcation,rcdDate});
                }
            }
        }
        catch (SQLException eq){
            eq.printStackTrace();
        }
    }

    public void AddPatient(JPanel panel){
        JButton button = new JButton("Add Patient");
        button.setPreferredSize(new Dimension(100,40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPatient patient = new NewPatient(Records.this);
                patient.FillData();
            }
        });

        panel.add(button,BorderLayout.EAST);
        RecordFrame.add(panel,BorderLayout.SOUTH);
    }

    public void ClearPatient(JPanel panel){
        JButton button = new JButton("Clear Patient");
        button.setPreferredSize(new Dimension(100,40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try(Connection conn = DriverManager.getConnection(url,user,password);
                    Statement stmt = conn.createStatement()
                ){
                    String query = "Delete From patient_details";
                    stmt.executeUpdate(query);
                    RefreshPatientTable();
                }
                catch (SQLException eq){
                    eq.printStackTrace();
                }
            }
        });
        panel.add(button,BorderLayout.CENTER);
    }

}
