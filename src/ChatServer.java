
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatServer {
    static  Vector ClientSockets;
    static  Vector LoginNames;

    public ChatServer() throws IOException {
        ServerSocket server = new ServerSocket(5217);
        ClientSockets = new Vector();
        LoginNames = new Vector();
        
        while (true) {            
            Socket client = server.accept();
            AcceptClient acceptClient = new AcceptClient(client);
        }
    }
    
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
    }
    
    class AcceptClient extends Thread{
        Socket ClientSocket;
        DataInputStream din;
        DataOutputStream dout;

        public AcceptClient(Socket client) throws IOException {
            ClientSocket =client;
            din = new DataInputStream(ClientSocket.getInputStream());
            dout = new DataOutputStream(ClientSocket.getOutputStream());
            
            String LoginName = din.readUTF();
            LoginNames.add(LoginName);
            ClientSockets.add(ClientSocket);
            
            start();
        }
        @Override
        public void run()
        {
            while (true) {                
                try {
                    String msgFromClient = din.readUTF();
                    StringTokenizer st = new StringTokenizer(msgFromClient);
                    String LoginName = st.nextToken();
                    String MsgType = st.nextToken();
                    int lo=-1;
                    String msg="";
                    
                    while (st.hasMoreTokens()) {
                        msg=msg+" "+st.nextToken();
                    }
                    
                    if(MsgType.equals("LOGIN")){
                        for(int i=0;i<LoginNames.size();i++)
                        {
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + " has logged in.");
                        }
                    }
                    else if(MsgType.equals("LOGOUT")){
                        for(int i=0;i<LoginNames.size();i++)
                        {
                            if(LoginName.equals( LoginNames.elementAt(i)))
                                lo=i;
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + " has logged out.");
                        }
                        if(lo>=0)
                        {
                            LoginNames.removeElementAt(lo);
                            ClientSockets.removeElementAt(lo);
                        }
                    }
                    else{
                        for(int i=0;i<LoginNames.size();i++)
                        {
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + ": "+msg);
                        }
                    }
                    if(MsgType.equals("LOGOUT")){
                        break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
