
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class ChatClient extends JFrame implements Runnable{
    
    Socket socket;
    JTextArea ta;
    JButton send,logout;
    JTextField tf;
    
    
    Thread thread;
    
    DataInputStream din;
    DataOutputStream dout;
    
    String LoginName;

    public ChatClient(String login) throws UnknownHostException,IOException {
        super(login);
        LoginName = login;
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                try {
                    dout.writeUTF(LoginName + " " + "LOGOUT");
                    System.exit(1);
                } catch (IOException ex) {
                    //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });
        
        ta = new JTextArea(18,50);
        tf = new JTextField(50);
        
        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        if(tf.getText().length()>0)    
                            dout.writeUTF(LoginName + " " + "DATA " + tf.getText().toString());
                        tf.setText("");
                    } catch (IOException ex) {
                        //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        send = new JButton("Send");
        logout = new JButton("Logout");
        
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if(tf.getText().length()>0)
                        dout.writeUTF(LoginName + " " + "DATA " + tf.getText().toString());
                    tf.setText("");
                } catch (IOException ex) {
                    //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });
        
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    dout.writeUTF(LoginName + " " + "LOGOUT" );
                    System.exit(1);
                } catch (IOException ex) {
                    //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });
        
        socket = new Socket("localhost",5217);
        
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
        
        dout.writeUTF(LoginName);
        dout.writeUTF(LoginName + " " + "LOGIN");
        
        thread = new Thread(this);
        thread.start();
        setup();
    }
    
    private void setup() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        setSize(600,400);
        
        JPanel panel = new JPanel();
        
        panel.add(new JScrollPane(ta));
        panel.add(tf);
        panel.add(send);
        panel.add(logout);
        
        add(panel);
        
        setVisible(true);
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        while(true){            
            try {
                ta.append("\n" + din.readUTF());
            } catch (IOException ex) {
                //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }
    /*public static void main(String[] args) throws UnknownHostException,IOException {
        ChatClient client = new ChatClient("User1");
    }*/
    
}
