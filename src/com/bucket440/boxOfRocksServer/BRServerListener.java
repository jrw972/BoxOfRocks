package com.bucket440.boxOfRocksServer;

/*******************************************************************************
 * Jason Schindler
 * CIS-287
 * Final Project
 * 05122007
 * 
 * boxOfRocksServer package : 	This is the server half of the new/improved
 * 								BoxOfRocks.  Neat huh?
 * 
 * BRReloadThread class :		This is the looping process that listens for
 * 								new exciting connections. 		
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

import java.net.*;


public class BRServerListener{

	
	public static void main(String[] args) throws Exception {
		
		BRTheGarden theGarden = new BRTheGarden();
		Socket client = null;
		ServerSocket BORServer = null;
		System.out.println("Starting Box Of Rocks Server...");
		System.out.print("Starting Environment...");
		theGarden.startEnvironment();
		System.out.println("Done");
		System.out.println("Starting Bots-Reload Thread...");
		Thread reloadGarden = new Thread(new BRReloadThread(theGarden));
		reloadGarden.start();
		BORServer = new ServerSocket(3459);
		System.out.println("\n\n-----------{[><<<<<<<<<<<<<<<Box Of Rocks>>>>>>>>>>>>>>><]}\n\n");
		
		while(true){
			client = null;
			System.out.println("Ready for connection...");
			client = BORServer.accept();
			System.out.println("Client has connected: " + client.getInetAddress());
			System.out.println("Sending to server...");
			
			Thread newClient = new Thread(new BoxOfRocksServer(client, theGarden));
			newClient.start();
		}
	}
}
