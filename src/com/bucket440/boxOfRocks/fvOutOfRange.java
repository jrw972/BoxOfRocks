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
 * fvOutOfRange :			This is my custom Exception.  Pretty self
 * 							explanatory.		
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

public class fvOutOfRange extends Exception {
	
	private static final long serialVersionUID = 1;

	public fvOutOfRange(String exceptn){
		super(exceptn);
	}

}
