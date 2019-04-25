
/** Client.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * @author Krish Ghiya
 * @since 20.04.2019
 */

package edu.sjsu.cs.cs151.connectfour.network;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.View.Button;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourGameWindow;
import edu.sjsu.cs.cs151.connectfour.View.ConnectFourMainWindow;

public class Client extends Network {
  
  private String serverIP;
  
  //constructor
  public Client(String host, ConnectFourMainWindow parent){
    super(parent, "Player 2");
    serverIP = host;
  }
  
  //connect to server
  public void startRunning(){
    try {
      connectToServer();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    super.startRunning();
  }
  
  //connect to server
  private void connectToServer() throws IOException{
    connection = new Socket(InetAddress.getByName(serverIP), 6789);
    System.out.println("Now connected to " + connection.getInetAddress().getHostName());
  } 
}
