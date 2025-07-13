import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class LoginField {
    CreateFrame loginFrame = new CreateFrame(540,250 , "Login");
    String enteredUser;
    String enteredPass;
    public void ShowLoginFrame(){
        loginFrame.setLayout(new GridBagLayout());

        DisplayInputFields();

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true); // Makes the frame visible
    }

    public void DisplayInputFields(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,120));
        panel.setBackground(Color.gray);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel user = new JLabel("username :");
        panel.add(user , gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel pass = new JLabel("password :");
        panel.add(pass , gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(150,15));
        panel.add(userField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(150,15));
        panel.add(passField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(40,20));
        panel.add(loginButton,gbc);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enteredUser = userField.getText();
                enteredPass = new String(passField.getPassword());
                if(IsValidUser(enteredUser,enteredPass)){
                    loginFrame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid Credentials");
                }
            }
        });

        loginFrame.add(panel);
    }

    boolean IsValidUser(String user , String pass){
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Patient_Records","postgres" , "suyog123");
            Statement statement = connection.createStatement()
        ){
            String query = "Select * from " + "Login_Info" + " where username = '" + user + "' and pass = '" + pass + "'";
            try (ResultSet rs = statement.executeQuery(query)){
                if(rs.next()){
                    Records records = new Records();
                    records.ShowRecordWindow();
                    rs.close();
                    statement.close();
                    connection.close();
                    return true;
                }
                else {
                    rs.close();
                    statement.close();
                    connection.close();
                    return false;
                }
            }


        }
        catch (SQLException sq){
            sq.printStackTrace();
            return false;
        }
    }
}
