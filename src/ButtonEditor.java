import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    JButton button;
    String label;

    DefaultTableModel model;
    int row;
    ButtonEditor(JCheckBox checkBox , String labelType , DefaultTableModel model){
        super(checkBox);
        this.model = model;
        button = new JButton();
        button.setOpaque(true);
        this.label = labelType;

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int id = (int) model.getValueAt(row,0);
                if(label.equals("Details")){
                    CreateFrame recordsFrame = new CreateFrame(400,400 , "Patient Detail");
                    recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    recordsFrame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    // Common layout tweaks
                    gbc.insets = new Insets(5, 10, 10, 10); // margin around components
                    gbc.anchor = GridBagConstraints.NORTHWEST; // align to top-left
                    gbc.weightx = 1.0;
                    gbc.weighty = 0; // don't expand vertically
                    gbc.fill = GridBagConstraints.NONE;


                    String name = (String) model.getValueAt(row,1);
                    String dob = (String) model.getValueAt(row,2);
                    String dg = (String) model. getValueAt(row,4);
                    String med = (String) model.getValueAt(row,5);
                    String rcd = (String) model.getValueAt(row,6);


                    gbc.gridx=0;
                    gbc.gridy=0;
                    gbc.gridwidth=1;
                    JLabel nameLabel = new JLabel(name);
                    nameLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
                    recordsFrame.add(nameLabel,gbc);

                    gbc.gridx=0;
                    gbc.gridy=1;
                    gbc.gridwidth = 1;
                    recordsFrame.add(new JLabel("Id : "),gbc);

                    gbc.gridx=1;
                    gbc.gridy=1;
                   // gbc.gridwidth=3;
                    JLabel idLabel = new JLabel(String.valueOf(id));
                    recordsFrame.add(idLabel,gbc);

                    gbc.gridx=0;
                    gbc.gridy=2;
                    recordsFrame.add(new JLabel("DOB : "),gbc);

                    gbc.gridx=1;
                    gbc.gridy=2;
                   // gbc.gridwidth=3;
                    JLabel dobLabel = new JLabel(dob);
                    recordsFrame.add(dobLabel,gbc);

                    gbc.gridx=0;
                    gbc.gridy=3;
                    recordsFrame.add(new JLabel("Diagnosis : "),gbc);

                    gbc.gridx=1;
                    gbc.gridy=3;
                   // gbc.gridwidth=3;
                    JLabel diagLabel = new JLabel(dg);
                    recordsFrame.add(diagLabel,gbc);

                    gbc.gridx=0;
                    gbc.gridy=4;
                    recordsFrame.add(new JLabel("Medication : "),gbc);

                    gbc.gridx=1;
                    gbc.gridy=4;
                   // gbc.gridwidth=3;
                    JLabel medcLabel = new JLabel(med);
                    recordsFrame.add(medcLabel,gbc);

                    gbc.gridx=0;
                    gbc.gridy=5;
                    recordsFrame.add(new JLabel("Record Date : "),gbc);

                    gbc.gridx=1;
                    gbc.gridy=5;
                    //gbc.gridwidth=3;
                    JLabel recdLabel = new JLabel(rcd);
                    recordsFrame.add(recdLabel,gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 6;
                    gbc.weighty = 1.0; // this row takes remaining vertical space
                    gbc.fill = GridBagConstraints.VERTICAL;
                    recordsFrame.add(Box.createVerticalGlue(), gbc);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label; // return the button label, not a boolean
    }
}
