package com.bucket440.boxOfRocksClient;

/*******************************************************************************
 * Jason Schindler
 * CIS-287
 * Final Project
 * 05122007
 * 
 * boxOfRocksClient package : 	This is the client half of the new/improved
 * 								BoxOfRocks.  Neat huh?
 * 
 * BoxOfRocksClient class :		This is the only class in this package.  It
 * 								handles all of the GUI interaction, the writing 
 * 								to and pulling from disk, and the socket to 
 * 								the server. 		
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import com.bucket440.boxOfRocks.*;



public class BoxOfRocksClient {
	
	//Defining some things:
	private String serverIP;
	private int serverPort;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket sock;
	private BRAllRocks myRock;
	private File saveFile = new File("BOR.dat");
	private JFrame TheBox = new JFrame("Box Of Rocks");
	private JFrame TheBoxSubMenu = new JFrame("BOR: ");
	private static BoxOfRocksClient myBoxOfRocks = new BoxOfRocksClient();
	private String location = "Outside";
	
	public static void main(String[] args){
		//Main logic and execution
		
		try{
			myBoxOfRocks.openScreen();
			myBoxOfRocks.startBoxOfRocks();
			myBoxOfRocks.connectBORServer();
			myBoxOfRocks.mainMenu();
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}
	
	public void openScreen(){
		JOptionPane.showMessageDialog(null, "Welcome to Box Of Rocks!\n" +
				"Jason Schindler\n" +
				"CIS-287 Final Project");
	}
	
	private void startBoxOfRocks(){
		TheBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TheBox.setSize(220,350);
		TheBoxSubMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		TheBoxSubMenu.setSize(270,200);
		loadData();
	}
	
	//Pulls saved rock data from the disk.
	private void loadData(){
		if(saveFile.exists()){
			try{
				FileInputStream inStream = new FileInputStream(saveFile);
				ObjectInputStream fromFile = new ObjectInputStream(inStream);
				myRock = (BRAllRocks)fromFile.readObject();
				fromFile.close();
				inStream.close();
			}catch(Exception e){
				System.out.println(e);
			}
		} else {
			createNewRock();
		}
	}
	
	//Saves rock to disk.
	private void saveData(){
		try{
			System.out.print("Saving...");
			saveFile.createNewFile();
			FileOutputStream outStream = new FileOutputStream(saveFile);
			ObjectOutputStream toFile = new ObjectOutputStream(outStream);
			toFile.writeObject(myRock);
			toFile.close();
			System.out.println("Done");
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	//You'll notice a lot of random "out.blah" in the rest of this code.  The out
	//object is how we are talking to the server.
	
	private void updateMyRock(){
		try{
			//So what is happening here, is the server knows after it receives 
			//"UpdateMyRock" that it needs to send a BRAllRocks type object next.
			//That should explain the casting below.
			
			out.writeObject("UpdateMyRock");
			out.flush(); //Don't forget to wash your hands.
		
			myRock = (BRAllRocks) in.readObject();
		}catch(Exception e){
			System.out.println("Error updating rock from server" + e);
		}
	}
	
	//Here is my part of my required GUI for project credit.
	private void mainMenu(){
		JPanel main = new JPanel();
		main.setBorder(BorderFactory.createTitledBorder("Main Menu"));
		TheBox.add(main);
		main.setLayout(new GridLayout(5, 1));
		JButton button1 = new JButton("Enter Garden");
			button1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					locationMenu();
				}
			});
		main.add(button1);
		
		JButton button2 = new JButton("View Your Rock");
			button2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					updateMyRock();
					viewMyRockLocal();
				}
			});
		main.add(button2);
		
		JButton button3 = new JButton("View Your Notebook");
			button3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					updateMyRock();
					viewNotebook();
				}
			});
		main.add(button3);
	
		JButton button4 = new JButton("Quit With Save");
			button4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					updateMyRock();
					saveData();
					quit();	
				}
			});
		main.add(button4);

		JButton button5 = new JButton("Quit Without Save");
			button5.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					quit();
				}
			});
		main.add(button5);
	
		//This centers the menu on the screen.
		TheBox.setLocationRelativeTo(null);
		TheBox.setVisible(true);

		
	}
	
//	"Where do you want to go today?"
	private void locationMenu(){
		try{
			
			out.writeObject("ChangeLocation");
			out.flush();
		
			String[] locationNames = (String[]) in.readObject();
				
			try{
				String c = (String)JOptionPane.showInputDialog(
						null, 
						"Where would you like to go?", 
						"Go Where?",
						JOptionPane.PLAIN_MESSAGE,
						null, 
						locationNames,
						null);
		
				if(c == null){
					//OK, so the server expects a response...even if the client doesn't have
					//one.  So this is the string I picked.
					c = "[[BR]NEVERMIND]";
				}
				location = c;
				out.writeObject(c);
				out.flush();
				
				if(!c.equals("[[BR]NEVERMIND]")){
					eventsMenu();
				}
			
			}catch(Exception e){
				System.out.println(e);
			}
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}
	
	private void eventsMenu(){
		if(!location.equals("Outside")){
			TheBoxSubMenu.setTitle("BOR: " + location);
			JPanel events = new JPanel();
			events.setBorder(BorderFactory.createTitledBorder("In The Garden"));
			TheBoxSubMenu.add(events);
			events.setLayout(new GridLayout(3, 1));
			JButton button1 = new JButton("Interact With Someone");
				button1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							out.writeObject("WhoIsHere");
							out.flush();
							
							String[] otherRocksStr = (String[]) in.readObject();						
							
							try{
								String c = (String)JOptionPane.showInputDialog(
									null, 
									"Who would you like to speak with?", 
									"Rocks in " + location,
									JOptionPane.PLAIN_MESSAGE,
									null, 
									otherRocksStr,
									null
									);
								
								if(c == null){
									c = "[[BR]NEVERMIND]";
									out.writeObject(c);
									out.flush();
								} else if(c.equals(myRock.getRockName() + " - " + myRock.getOwnerName())){
									c = "[[BR]NEVERMIND]";
									out.writeObject(c);
									out.flush();
									JOptionPane.showMessageDialog(null, "Don't talk to yourself... its creepy.");
								} else {
									out.writeObject(c);
									out.flush();
									JOptionPane.showMessageDialog(null, "Speaking with: " + c);
								}
								
							}catch(Exception x){
								System.out.println(x);
							}
						}catch(Exception f){
							System.out.println(f);
						}
					}
						
					});
			events.add(button1);
		
			JButton button2 = new JButton("View Your Notebook");
				button2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMyRock();
						viewNotebook();
					}
				});
			events.add(button2);
		
			JButton button3 = new JButton("Return To Main Menu");
				button3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//This just closes the sub-menu.
						TheBoxSubMenu.dispose();
					}
				});
			events.add(button3);
	
			TheBoxSubMenu.setLocationRelativeTo(null);
			TheBoxSubMenu.setVisible(true);
		}
	
	}
	
	//	If we can't find a saved rock, then we need to make a new one.
	private void createNewRock(){
		boolean finished = false;
		JOptionPane.showMessageDialog(null, "I can't find a previously saved rock.  Lets make one!");
		do{
			try{
				myRock = new BRAllRocks();
			}catch(Exception e){
				System.out.println(e);
			}
			String strResponse;
			strResponse = JOptionPane.showInputDialog("Please give your rock a name:");
			try{
				if(strResponse == null){
					myRock.setRockName("None");
				} else if(strResponse.length() < 1){
					myRock.setRockName("None");
				} else {
					myRock.setRockName(strResponse);
				}
			}catch(Exception e){
				System.out.println(e);
			}
			strResponse = JOptionPane.showInputDialog("What is your name?");
			try{
				if(strResponse == null){
					myRock.setOwnerName("None");
				}else if(strResponse.length() < 1){
					myRock.setOwnerName("None");
				} else {
					myRock.setOwnerName(strResponse);
				}
			}catch(Exception e){
				System.out.println(e);
			}
			
			char rType;
			try{
				String[] rockTypesA = {"Igneous", "Sedimentary","Metamorphic"};
				String c = (String)JOptionPane.showInputDialog(
						null, 
						"Which type of rock would you like?", 
						"Type Of Rock?",
						JOptionPane.PLAIN_MESSAGE,
						null, 
						rockTypesA,
						null);
				if(c.equals("Igneous")){
					rType = 'I';
				} else if(c.equals("Sedimentary")){
					rType = 'S';
				} else if(c.equals("Metamorphic")){
					rType = 'M';
				} else {
					rType = 'I';
				}
			}catch(Exception e){
				rType = 'I';
			}
			
			myRock.setRockType(rType);
			boolean tempValid = false;
			do{
				
				//This is my best example of a try...catch block gracefully
				//catching an error and keeping things running.
				try{
					String friendlyStr = 
						JOptionPane.showInputDialog("Please set a FriendlienssValue between" +
								" 0 - 100\n(This will dictate how easily you share and receive" +
								" information");
					int friendlyInt = Integer.parseInt(friendlyStr);
					myRock.setFriendValue(friendlyInt);
					tempValid = true;
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Please enter a value between 0 and 100.", "Alert!", JOptionPane.ERROR_MESSAGE);
					tempValid = false;
				}
			}while(!tempValid);
			strResponse = JOptionPane.showInputDialog("What is " + myRock.getRockName() + "'s favorite song?");
			try{
				if(strResponse == null){
					myRock.setFavoriteSong("None");
				}else if(strResponse.length() < 1){
					myRock.setFavoriteSong("None");
				} else {
					myRock.setFavoriteSong(strResponse);
				}
			}catch(Exception e){
				System.out.println(e);
			}
			strResponse = JOptionPane.showInputDialog("What is " + myRock.getRockName() + "'s favorite color?");
			try{
				if(strResponse == null){
					myRock.setFavoriteColor("None");
				}
				if(strResponse.length() < 1){
					myRock.setFavoriteColor("None");
				} else {
					myRock.setFavoriteColor(strResponse);
				}
			}catch(Exception e){
				System.out.println(e);
			}
			finished = true;
			saveData();
			viewMyRockLocal();
					
		}while(!finished);
	}
	
	private void viewMyRockLocal(){
		JOptionPane.showMessageDialog(null, 
				"Here is your rock:\n\n" +
				"Name: " + myRock.getRockName() + "\n" +
				"Owner: " + myRock.getOwnerName() + "\n" +
				"Type: " + myRock.getRockTypeStr() + "\n" +
				"Friendliness: " + String.valueOf(myRock.getFriendValue()) + "\n" +
				"Favorite Song: " + myRock.getFavoriteSong() + "\n" +
				"Favorite Color: " + myRock.getFavoriteColor());
	}
	
	private void connectBORServer(){
		
		
		try{
			//The IP prompt will default to localhost.
			serverIP = "127.0.0.1";
			serverPort = 3459;
			
			serverIP = JOptionPane.showInputDialog("What is your Box Of Rocks Server IP :");
			try{
				if(serverIP.length() < 1){
					serverIP = "127.0.0.1";
				}
			}catch(Exception e){
				serverIP = "127.0.0.1";
			}
			
			System.out.println("Connecting to server at: " + serverIP);
			sock = new Socket(serverIP,serverPort);
			
			//A handshake of sorts.
			System.out.println("Waiting for welcome text...");
			
			in = new ObjectInputStream(sock.getInputStream());
			
			System.out.println( ((String) in.readObject()) + " :Got it!");
			
			System.out.println("Sending rock: " + myRock.getRockName());
			
			out = new ObjectOutputStream(sock.getOutputStream());
			
			out.writeObject(myRock);
			out.flush();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error connecting to: " + serverIP + "\n" + e, "Alert!", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	private void viewNotebook(){
		Map<String,String> notebook = myRock.getNotebook();
		if(!(notebook.isEmpty())){
			String[] notebookStr = new String[notebook.size()];
			int place = 0;
			for(String rName : notebook.keySet() ){
					notebookStr[place] = rName;
					place++;
			}
		
			try{
				String c = (String)JOptionPane.showInputDialog(
					null, 
					"Please pick an entry to read", 
					myRock.getRockName() + "'s Notebook",
					JOptionPane.PLAIN_MESSAGE,
					null, 
					notebookStr,
					null
					);
		
				String raw = notebook.get(c);
				String[] breakDown = new String[6];
				breakDown = raw.split(";Q;");
				JOptionPane.showMessageDialog(null, 
					"Here is what we know so far:\n\n" +
					"Name: " + breakDown[1] + "\n" +
					"Owner: " + breakDown[2] + "\n" +
					"Type: " + breakDown[3] + "\n" +
					"Friendliness: " + breakDown[4] + "\n" +
					"Favorite Song: " + breakDown[5] + "\n" +
					"Favorite Color: " + breakDown[6]);
								
			}catch(Exception e){
				//Quietly ignore this exception.
			}
		} else {
			JOptionPane.showMessageDialog(null, "Your notebook is empty!");
		}
	}
	
	private void quit(){
		System.out.println("Disconnecting from server...");
		try{
			//Close it all down!
			out.reset();
			out.writeObject("Disconnect");
			out.close();
			in.close();
			sock.close();
		}catch(Exception e){
			System.out.println("Error closing connection " + e);
		}
		System.out.println("Goodbye!");
		System.exit(0);
	}
}
