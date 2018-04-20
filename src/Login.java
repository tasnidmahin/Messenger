
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.*;


public class Login {
    public static void main(String[] args) {
        final JFrame login = new JFrame("Login");
        JPanel panel = new JPanel();
        final JTextField loginName = new JTextField(20);
        JButton enter = new JButton("Login");
        
        panel.add(loginName);
        panel.add(enter);
        login.setSize(300,100);
        login.add(panel);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    ChatClient client = new ChatClient(loginName.getText());
                    login.setVisible(false);
                    login.dispose();
                } catch(UnknownHostException ex)
                {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });
        
        loginName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getKeyCode() == KeyEvent.VK_ENTER){    
                    try {
                        ChatClient client = new ChatClient(loginName.getText());
                        login.setVisible(false);
                        login.dispose();
                    }catch(UnknownHostException ex){
                        ex.printStackTrace();
                    }
                    catch (IOException ex) {
                        //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
