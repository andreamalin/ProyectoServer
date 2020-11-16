import javax.swing.*;

public class ServerGUI extends JFrame {

    JTextArea console;
    JScrollPane scrollConsole;

    public  ServerGUI(){

        // Del frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setLayout(null);
        setBounds(0,0,500,400);
        setResizable(false);
        setLocationRelativeTo(null);

        // Componentes
        console = new JTextArea(25, 25);
        console.setBounds(10, 10, 450,350);



        scrollConsole = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollConsole);
        add(console);
        setVisible(true);
    }

}
