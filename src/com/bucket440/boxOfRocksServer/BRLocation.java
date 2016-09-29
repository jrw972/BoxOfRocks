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
 * BRLocation class :		This holds the BRLocation object.		
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

public class BRLocation implements BRLocInterface {
	
	private String LocName;
	//An array list of all rocks at this location.
	private Map<String, BRAllRocks> GuestRocksAr = new HashMap<String,BRAllRocks>();
	private List<BRAllRocks> GuestRocksList = new ArrayList<BRAllRocks>();
	
	public BRLocation(String NLocName){
		setLocationName(NLocName);
	}
	
	private void setLocationName(String NLocName){
		LocName = NLocName;
	}
	
	public String getLocationName(){
		return LocName;
	}
	
	//Haul em in:
	public void addToLocation(BRAllRocks NewRock){
		GuestRocksList.add(NewRock);
		GuestRocksAr.put(NewRock.getRockName() + " - " + NewRock.getOwnerName(), NewRock);
	}
	
	//Ride em out:
	public void delFromLocation(BRAllRocks OldRock){
		GuestRocksList.remove(OldRock);
		GuestRocksAr.remove(OldRock.getRockName() + " - " + OldRock.getOwnerName());
	}
	
	//See who else is here:
	public Map<String,BRAllRocks> whoIsHere(){
		return GuestRocksAr;
	}
	
	public List<BRAllRocks> whoIsHereList(){
		return GuestRocksList;
	}
}
