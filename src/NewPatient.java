import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPatient {
    CreateFrame frame = new CreateFrame(600,640 , "Add Patient");
    JTextField[] recordedData = new JTextField[5];

    String DOB;
    private Records records;

    NewPatient(Records records){
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.records = records;
    }
    public void FillData(){
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.VERTICAL;

        DisplayText(frame,gbc);
        DisplayFields(frame,gbc);
    }

    void DisplayText(JFrame frame , GridBagConstraints gbc){
        gbc.gridx=0;
        gbc.gridy=0;
        frame.add(new JLabel("id") , gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        frame.add(new JLabel("Name") , gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        frame.add(new JLabel("DOB") , gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        frame.add(new JLabel("Diagnosis") , gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        frame.add(new JLabel("Medication") , gbc);

        gbc.gridx=0;
        gbc.gridy=5;
        frame.add(new JLabel("Record Date") , gbc);
    }

    void DisplayFields(JFrame frame , GridBagConstraints gbc){
        JComboBox<String> dayBox = new JComboBox<>(GenerateDOBOptions(3));
        JComboBox<String> monthBox = new JComboBox<>(GenerateDOBOptions(2));
        JComboBox<String> yearBox = new JComboBox<>(GenerateDOBOptions(1));
        JComboBox<String> recordDateComboBox = new JComboBox<>(GenerateDateOptions());

        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dobPanel.add(yearBox);
        dobPanel.add(monthBox);
        dobPanel.add(dayBox);

        for(int i = 0 ; i<6 ;i++){
            int index = i;
            gbc.gridx=1;
            gbc.gridy=i;

            if(i==2){
                gbc.gridwidth = 2;
                frame.add(dobPanel, gbc);
                gbc.gridwidth = 1;
                DOB = yearBox.getSelectedItem().toString() + "-" + monthBox.getSelectedItem().toString() + "-" + dayBox.getSelectedItem().toString();
                continue;
            }
            if(i==5){
                frame.add(recordDateComboBox , gbc);
                continue;
            }
            JTextField tf = new JTextField(20);
            if(index > 2 ){
                index--;
            }
            recordedData[index] = tf;
            frame.add(tf , gbc);
        }
        gbc.gridx=0;
        gbc.gridy=7;
        gbc.gridwidth = 2;
        JButton submit = new JButton("Submit");
        frame.add(submit , gbc);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                try {
                    id = Integer.parseInt(recordedData[0].getText().trim());
                    System.out.println("Converted value: " + id);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid number format: " + recordedData[0].getText());
                }
                InsertPatient(id, recordedData[1].getText() , DOB , recordedData[2].getText(),recordedData[3].getText(),recordDateComboBox.getSelectedItem().toString());
                records.RefreshPatientTable();
            }
        });
    }

    void InsertPatient(int id , String name , String DOB , String diag , String medication , String recorddate){
        String url = "jdbc:postgresql://localhost:5432/Patient_Records";
        String user = "postgres";
        String password = "suyog123";

        String sql = "Insert into patient_details (Id , Name , DOB , Diagnosis , Medication , RecordDate) Values (?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement prepStmt = conn.prepareStatement(sql)
        ){
            prepStmt.setInt(1,id);
            prepStmt.setString(2,name);
            prepStmt.setString(3,DOB);
            prepStmt.setString(4,diag);
            prepStmt.setString(5,medication);
            prepStmt.setString(6,recorddate);


            int rows = prepStmt.executeUpdate();
            if(rows > 0 ){
                JOptionPane.showMessageDialog(null, "✅ patient entered succesfully");
                frame.dispose();
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    String[] GenerateDateOptions(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR , -7);

        String[] dateOptions = new String[8];

        for(int i=0 ; i<dateOptions.length ; i++){
            dateOptions[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR , 1);
        }
        return dateOptions;
    }

    String[] GenerateDOBOptions(int index){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        String[] dateOptions;
        switch (index){
            case 1 :
                dateFormat = new SimpleDateFormat("yyyy");
                calendar.add(index, -50);
                dateOptions = new String[51];
                for(int j=0 ; j<dateOptions.length ; j++){
                    dateOptions[j] = dateFormat.format(calendar.getTime());
                    calendar.add(Calendar.YEAR , 1);
                }
                return dateOptions;

            case 2 :
                String[] months = new String[12];
                for (int i = 1; i <= 12; i++) {
                    months[i - 1] = String.format("%02d", i); // 01 to 12
                }
                return months;

            case 3:
                String[] days = new String[31];
                for (int i = 1; i <= 31; i++) {
                    days[i - 1] = String.format("%02d", i); // 01 to 31
                }
                return days;

            default:
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                calendar.add(index, -50);
                dateOptions = new String[51];
                for(int j=0 ; j<dateOptions.length ; j++){
                    dateOptions[j] = dateFormat.format(calendar.getTime());
                    calendar.add(Calendar.DAY_OF_YEAR , 1);
                }
                return dateOptions;
        }
    }
}


