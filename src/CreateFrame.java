import javax.swing.*;

public class CreateFrame extends JFrame {

    CreateFrame(int width , int height , String title){
        this.setVisible(true);
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle(title);
    }
}
