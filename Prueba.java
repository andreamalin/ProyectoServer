import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Prueba{

    static final int clientPort = 1400;
    static final int serversPort = 1500;
    static final int dNSPort = 1200;

    public static void main(String[] args){

        JLabel messages;
        JFrame success = new JFrame("Exito");

        success.setLayout(null);
        success.setResizable(false);
        success.setLocationRelativeTo(null);
        success.setBounds(10, 10, 240, 100);
        success.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        messages = new JLabel("Se completo con exito");
        messages.setBounds(50,  10, 200, 40);

        success.add(messages);
        success.setVisible(true);

    }
}
