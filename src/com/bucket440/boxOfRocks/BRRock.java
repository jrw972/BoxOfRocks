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
 * BRRock class :			This abstract class holds generic info for all 
 * 							rocks.		
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

public abstract class BRRock {
	
	private String Name;
	private String OwnName;
	private char RType;
	private int FriendV;
	private String FavSong;
	private String FavColor;
	
	//Constructors:
	public BRRock() throws Exception{
		setRockName("?");
		setOwnerName("?");
		setRockType('U');
		setFriendValue(0);
		setFavoriteSong("?");
		setFavoriteColor("?");
	}
	
	public BRRock(String NName, String NOwnName, char NRType, 
			int NFriendV, String NFavSong, String NFavColor) throws Exception{
		setRockName(NName);
		setOwnerName(NOwnName);
		setRockType(NRType);
		setFriendValue(NFriendV);
		setFavoriteSong(NFavSong);
		setFavoriteColor(NFavColor);
	}
	
	//Setters:
	public void setRockName(String NName){
		Name = NName;
	}
	
	public void setOwnerName(String NOwnName){
		OwnName = NOwnName;
	}
	
	public void setRockType(char NRType){
		RType = NRType;
	}
	
	
	//Here is my method throwing my custom exception:
	public void setFriendValue(int NFriendV) throws fvOutOfRange{
		if((NFriendV < 0) || NFriendV > 100){
			throw new fvOutOfRange("Friendliness Value Out Of Range");
		} else {
			FriendV = NFriendV;
		}
	}
	
	public void setFavoriteSong(String NFavSong){
		FavSong = NFavSong;
	}
	
	public void setFavoriteColor(String NFavColor){
		FavColor = NFavColor;
	}
	
	//Getters:
	public String getRockName(){
		return Name;
	}
	
	public String getOwnerName(){
		return OwnName;
	}
	
	public char getRockType(){
		return RType;
	}
	
	//Returns a String version of the rock type:
	public String getRockTypeStr(){
		if (RType == 'I'){
			return "Igneous";
		} else if (RType == 'S'){
			return "Sedimentary";
		} else if (RType == 'M'){
			return "Metamorphic";
		} else if (RType == 'U'){
			return "?";
		} else {
			return "There seems to be a serious problem.--BRRock-getRockTypeStr";
		}
	}
	
	public int getFriendValue(){
		return FriendV;
	}
	
	public String getFavoriteSong(){
		return FavSong;
	}
	
	public String getFavoriteColor(){
		return FavColor;
	}
}
