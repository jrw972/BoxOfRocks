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
 * BRReloadThread class :		This class does nothing more than hold the
 * 								thread that moves the bots around and makes
 * 								them be social. 		
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


public class BRReloadThread implements Runnable{

	private BRTheGarden theGarden;
	
	BRReloadThread(BRTheGarden inputGarden){
		theGarden = inputGarden;
	}

	public void run(){
		while(true){
			try{
				//Every 30 seconds:
				Thread.sleep(30000);
				System.out.println("Moving Bots...");
				theGarden.moveEnvironment();
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
}
