import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    private JLabel nameLabel;
    private JButton logoutBtn;
    private JLabel scoreLabel;
    int width = 150;
    int height = 25;

    public UserPanel(String name,JLabel scoreLabel, JFrame loginGUI, JFrame clientGUI) {
        nameLabel = new JLabel("Hi! " + name, SwingConstants.CENTER);
        nameLabel.setSize(75,height);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(75,0, 75, height);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginGUI();
                clientGUI.setVisible(false);
            }
        });
        scoreLabel = new JLabel("Hi! " + name, SwingConstants.CENTER);
        scoreLabel.setSize(75,height);

        setSize(width,height);
        setBounds(530,15,width,height);
        setFocusable(true);
        this.setBackground(new Color(179,226,131));
        this.add(nameLabel);
        this.add(logoutBtn);
        this.add(scoreLabel);
        setLayout(null);
        setVisible(true);
    }
}
