package com.bucket440.boxOfRocks;
/*******************************************************************************
 * Jason Schindler
 * CIS-287
 * Midterm Project
 * 03052007
 * 
 * boxOfRocks package : 	This is a somewhat silly inpractical program
 * 							designed to help me explore the inner-workings
 * 							of object oriented programming.  For more info,
 * 							please see the README with this source.
 * 
 * BRAllRocks class :		As of now, this class holds the object for the only
 * 							kind of rock in BoxOfRocks.  It extends from BRock
 * 							so that when different rocks come along, they can
 * 							share a large number of the same attributes.		
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
import java.util.*;
import java.io.*;

//The "implements Externalizable" bit is a requrement of making this an
//externalized serialized object. 
public class BRAllRocks extends BRRock implements Externalizable{
	
	//This is required by serializable.  It basically lets us know if a saved
	//object was created with this exact version of this object.
	private static final long serialVersionUID = 1;
	//========================================================
	
	private String id;
	
	private Map<String, String> notebook;
	private Random rand = new Random();
	
	//Constructors:
	public BRAllRocks() throws Exception{
		super();
		setID(String.valueOf(rand.nextInt(1000)));
		saveNotebook(new HashMap<String,String>());
	}
	
	public BRAllRocks(String nID, String NName, String NOwnName, char NRType, 
			int NFriendV, String NFavSong, String NFavColor, 
			Map<String,String> NNotebook) throws Exception{
		setID(nID);
		setRockName(NName);
		setOwnerName(NOwnName);
		setRockType(NRType);
		setFriendValue(NFriendV);
		setFavoriteSong(NFavSong);
		setFavoriteColor(NFavColor);
		saveNotebook(NNotebook);
	}
	
	//This ID helps the system determine if two rocks with the same name / owner
	//etc are actually the *same* two rocks.  
	private void setID(String nId){
		id = nId;
	}
	
	public String getID(){
		return id;
	}
	
	public Map<String,String> getNotebook(){
		return notebook;
	}
	
	public void saveNotebook(Map<String,String> nNotebook){
		notebook = nNotebook;
	}
	
	//The writeExternal and readExternal below are requirements of the
	//"Externilization" interface.
	public void writeExternal(ObjectOutput out) throws IOException{
		out.writeObject(getID());
		out.writeObject(getRockName());
		out.writeObject(getOwnerName());
		out.writeChar(getRockType());
		out.write(getFriendValue());
		out.writeObject(getFavoriteSong());
		out.writeObject(getFavoriteColor());
		out.writeObject(getNotebook());
	}
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
		setID((String)in.readObject());
		setRockName((String)in.readObject());
		setOwnerName((String)in.readObject());
		setRockType(in.readChar());
		try{
			setFriendValue(in.read());
		}catch(Exception e){
			System.out.println(e);
		}
		setFavoriteSong((String)in.readObject());
		setFavoriteColor((String)in.readObject());
		saveNotebook((Map<String,String>)in.readObject());
	}	
}
