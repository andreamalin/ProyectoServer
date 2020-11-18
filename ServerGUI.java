import javax.swing.*;

public class ServerGUI extends JFrame {

    JLabel title, connected;
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
        title = new JLabel("MAIN SERVER");
        this.add(title);
        console = new JTextArea(1, 1);
        scrollConsole = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        console.setBounds(10, 40, 465,270);


        add(console);
        add(scrollConsole);


        setVisible(true);
    }

}
