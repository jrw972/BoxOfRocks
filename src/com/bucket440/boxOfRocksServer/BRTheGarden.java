package com.bucket440.boxOfRocksServer;
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
 * BRTheGarden class :		For right now, this class controls the environment.
 * 							I suspect that eventually, this code will become
 * 							the server portion of BoxOfRocks.		
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
import com.bucket440.boxOfRocks.*;

public class BRTheGarden {
	
	//Various ways to keep track of the what/when/wheres:
	private BRInteraction interact = new BRInteraction();
	private Map<String,BRLocation> LocationMap = new HashMap<String,BRLocation>();
	private List<BRLocation> AllLocations = new ArrayList<BRLocation>();
	private List<BRAllRocks> TheRocks = new ArrayList<BRAllRocks>();
	private Map<BRAllRocks,BRLocation> locationOfRock = new HashMap<BRAllRocks,BRLocation>();
	private Random rand = new Random();
	private Map<String,BRAllRocks> rocksInGarden = new HashMap<String,BRAllRocks>();
		
	public void startEnvironment(){
		
		//First we need some locations.  A new location is as easy as adding
		//another line of code.
		addLocation("Outside"); //This location is required... do not remove
		addLocation("Soda Shop");
		addLocation("Bowling Alley");
		addLocation("Museum");
		addLocation("Pub");
		addLocation("Mall");
		//addLocation("The Park");
		
		
		//Because you shouldn't play with yourself... 
		//Here are 10 "bot" rocks controlled by the program.
		try{
			TheRocks.add(
				new BRAllRocks("12", "Tony", "BoxOfRocks", 'I', 30, 
						"Like A Rock","Brick Red", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("34", "Jerry", "BoxOfRocks", 'M', 90, 
						"I Will Survive","Orange", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("32", "Stewart", "BoxOfRocks", 'S', 78, 
						"The Wind Cries Mary","Tapioka", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("10", "Big Ed", "BoxOfRocks", 'S', 12, 
						"King Of The Road","Moss Green", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("30", "Lars", "BoxOfRocks", 'M', 1, 
						"Whiplash","Black", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("7", "Sarah", "BoxOfRocks", 'I', 65, 
						"Its' Raining Men","Purple", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("8", "Brad", "BoxOfRocks", 'S', 45, 
						"Summer of '69","Blue", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("9", "Justin", "BoxOfRocks", 'M', 21, 
						"I Love This Bar","Brown", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("11", "Carrie", "BoxOfRocks", 'I', 87, 
						"Something About Love","Red", new HashMap<String,String>()));
			TheRocks.add(
				new BRAllRocks("13", "Stan", "BoxOfRocks", 'I', 50, 
						"Tribute","Plaid", new HashMap<String,String>()));
		}catch(Exception e){
			System.out.println(e);
		}
		
		//And of course we need to put the rocks someplace.
		addRockToLocation(LocationMap.get("Soda Shop"),TheRocks.get(0));
		addRockToLocation(LocationMap.get("Museum"),TheRocks.get(1));
		addRockToLocation(LocationMap.get("Museum"),TheRocks.get(2));
		addRockToLocation(LocationMap.get("Mall"),TheRocks.get(3));
		addRockToLocation(LocationMap.get("Bowling Alley"),TheRocks.get(4));
		addRockToLocation(LocationMap.get("Bowling Alley"),TheRocks.get(5));
		addRockToLocation(LocationMap.get("Museum"),TheRocks.get(6));
		addRockToLocation(LocationMap.get("Pub"),TheRocks.get(7));
		addRockToLocation(LocationMap.get("Pub"),TheRocks.get(8));
		addRockToLocation(LocationMap.get("Soda Shop"),TheRocks.get(9));
		
		Iterator<BRAllRocks> i = TheRocks.iterator();
		while(i.hasNext()){
			BRAllRocks r = i.next();
			addRockToGarden(r);
		}
	}
	
	//This function gives the "bot rocks" an opportunity to change locations,
	//communicate with each other, and even start a conversation with you! 
	public void moveEnvironment(){
		Iterator<BRAllRocks> I = TheRocks.iterator();
		
		//Every rock has a 50% chance of changing locations.
		//Lesson learned the hard way: You cannot update an object while you
		//are iterating over it.
		while(I.hasNext()){
			BRAllRocks r = I.next();
			int i = rand.nextInt(100);
			if(i < 50){
				locationOfRock.get(r).delFromLocation(r);
				i = rand.nextInt(AllLocations.size());
				addRockToLocation(AllLocations.get(i),r);
			}
			 
		}
		
		//Up to 3 locations will have a conversation with any two rocks
		//located at that location.
		for(int x = 0; x < 3; x++){
			List<BRAllRocks> rocksHere = new ArrayList<BRAllRocks>();
			int i = rand.nextInt(AllLocations.size());
			BRLocation L = AllLocations.get(i);
			rocksHere = L.whoIsHereList();
			if(rocksHere.size() > 0){
				i = rand.nextInt(rocksHere.size());
				int j = rand.nextInt(rocksHere.size());
				if(i != j){
					interact.conversation(rocksHere.get(i), rocksHere.get(j));
				}
			}
		}
	}
	
	//Makes new location:
	private void addLocation(String LocationName){
		
		BRLocation L = new BRLocation(LocationName);
		LocationMap.put(LocationName, L);
		if(!LocationName.equals("Outside")){
			AllLocations.add(L);
		}
	}
	
	//Get all locations in list form:
	public List<BRLocation> getAllLocations(){
		return AllLocations;
	}
	
	//.. .. in map form:
	public Map<String,BRLocation> getLocationMap(){
		return LocationMap;
	}
	
	public void addRockToGarden(BRAllRocks thisRock){
		rocksInGarden.put(
				thisRock.getRockName() + " - " + thisRock.getOwnerName(), thisRock);
	}
	
	public void removeRockFromGarden(BRAllRocks thisRock){
		rocksInGarden.remove(thisRock.getRockName() + " - " + thisRock.getOwnerName());
	}
	
	public Map<String,BRAllRocks> getAllRocksMap(){
		return rocksInGarden;
	}
	
	//Self explanatory:
	public void addRockToLocation(BRLocation Location, BRAllRocks TheRock){
		locationOfRock.put(TheRock, Location);
		Location.addToLocation(TheRock);
	}
}
