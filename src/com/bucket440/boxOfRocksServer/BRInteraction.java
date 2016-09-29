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
 * BRInteraction :			This class has one real job.  To handle all the
 * 							data switching from one rock to another while they
 * 							are speaking to each other.		
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

public class BRInteraction {
	
	private Random rand = new Random();
	
	public void conversation(BRAllRocks rockA, BRAllRocks rockB){
		
		//This portion updates the friendliness values accordingly:
		int diff = 0;
		diff = rockA.getFriendValue() - rockB.getFriendValue();
		diff = diff / 10;
				
		try{
			//System.out.println("Setting " + rockA.getRockName() + " value minus " + diff);
			rockA.setFriendValue(rockA.getFriendValue() - diff);
			rockB.setFriendValue(rockB.getFriendValue() + diff);
		}catch (Exception e){
			System.out.println(e);
		}
		
		//A gets info from B, and then B gets info from A... its a two-way
		//conversation.
		nutsAndBolts(rockA, rockB);
		nutsAndBolts(rockB, rockA);
	}
	
	private void nutsAndBolts(BRAllRocks rockA, BRAllRocks rockB){
		Map<String,String> notebookA = new HashMap<String,String>();
		Map<String,String> notebookB = new HashMap<String,String>();
		notebookA = rockA.getNotebook();
		notebookB = rockB.getNotebook();
		
		//Create the keys for both rocks outside of the notebook.  This way
		//I'm sure that these are the correct keys.
		String keyA = rockA.getRockName() + " - " + rockA.getOwnerName();
		String keyB = rockB.getRockName() + " - " + rockB.getOwnerName();
			
		boolean aKnowsB = false;
		
		//Has rock A already met rockB?
		if(notebookA.containsKey(keyB)){
			aKnowsB = true;
		}
		
		//If not, he/she knows him/her now!
		if(!aKnowsB){
			String rockBInfo = rockB.getID() + ";Q;" + rockB.getRockName() 
			+ ";Q;?;Q;?;Q;?;Q;?;Q;?";
			notebookA.put((keyB), rockBInfo);
		}
		
		if(aKnowsB){
			String[] breakDownB = new String[6];
			List<String> rockBNotesList = new ArrayList<String>();
			int r = 0;
			for(String rKey : notebookB.keySet() ){
				rockBNotesList.add(r, rKey);
				r++;
			}
			if(rockBNotesList.size() > 0){
				int i = rand.nextInt(rockBNotesList.size());
				String newKey = rockBNotesList.get(i);
				if(!(keyA.equals((newKey)))){
					if(!(notebookA.containsKey(newKey))){
					
						notebookA.put(newKey, "?;Q;?;Q;?;Q;?;Q;?;Q;?;Q;?" );
					}
					String rawB = notebookB.get(newKey);
				
					//This splits the long data string into usable parts by my custom
					//delimiter ";Q;".
					breakDownB = rawB.split(";Q;");
				
					notebookA = updateEntryData(notebookA, 0, newKey, breakDownB[0]);
					notebookA = updateEntryData(notebookA, 1, newKey, breakDownB[1]);
				
					//You get 2 chances for new information.
					for(int x = 0; x < 2; x++){
						i = rand.nextInt(4) + 2;
						notebookA = updateEntryData(notebookA, i, newKey, breakDownB[i]);
					}
				}
			}
		}
		
		rockA.saveNotebook(notebookA);
		
		//The following decides if you get more information based on friendliness
		//and rock type.
		int chances = 2;
		
		if(rockB.getFriendValue() > 90){
			chances = chances + 3;
		} else if(rockB.getFriendValue() > 70){
			chances = chances + 2;
		} else if(rockB.getFriendValue() > 50){
			chances = chances + 1;
		}else if(rockB.getFriendValue() < 20){
			chances = chances - 1;
		}
		
		if(rockB.getRockType() == 'I'){
			chances = chances - 1;
		} else if(rockB.getRockType() == 'S'){
			chances = chances + 1;
		}
		
		if(chances <= 0){
			chances = 1;
		}
		
		if(chances >= 6){
			chances = 5;
		}
		
		for(int i = 0; i < chances; i ++){
			int id = rand.nextInt(5) + 2;
			if(id == 2){
				notebookA = updateEntryData(notebookA, id, keyB, 
						rockB.getOwnerName());
			} else if(id == 3){
				notebookA = updateEntryData(notebookA, id, keyB, 
						rockB.getRockTypeStr());
			} else if(id == 4){
				notebookA = updateEntryData(notebookA, id, keyB, 
						String.valueOf(rockB.getFriendValue()));
			} else if(id == 5){
				notebookA = updateEntryData(notebookA, id, keyB, 
						rockB.getFavoriteSong());
			} else if(id == 6){
				notebookA = updateEntryData(notebookA, id, keyB, 
						rockB.getFavoriteColor());
			}
			rockA.saveNotebook(notebookA);
		
		}
	}
	
	//This just deals with the specifics of updating the notebook.
	private Map<String,String> updateEntryData(Map<String,String> notebook, int dataId, String key, String newData){
		String[] breakDown = new String[6];
		
		//This is the full data string.
		String raw = notebook.get(key);
		
		//Now its broken into an array.
		breakDown = raw.split(";Q;");
		
		//The new info gets swapped into the right spot.
		breakDown[dataId] = newData;
		
		//And the array is rebuilt.
		String result = "";
		for(int i = 0; i < 7; i ++){
			result = result + breakDown[i];
			if(i != 6){
				result = result + ";Q;";
			}
		}
		notebook.put(key, result);
		return notebook;
	}
}
