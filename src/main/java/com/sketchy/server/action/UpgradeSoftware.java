/*
    Sketchy
    Copyright (C) 2015 Matthew Havlovick
    http://www.quickdrawbot.com

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

Contact Info:

Matt Havlovick
QuickDraw
470 I St
Washougal, WA 98671

matt@quickdrawbot.com
http://www.quickdrawbot.com

This General Public License does not permit incorporating your program into
proprietary programs.  If your program is a subroutine library, you may
consider it more useful to permit linking proprietary applications with the
library.  If this is what you want to do, use the GNU Lesser General
Public License instead of this License.
*/

package com.sketchy.server.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.sketchy.server.JSONServletResult;
import com.sketchy.server.ServletAction;
import com.sketchy.server.JSONServletResult.Status;

public class UpgradeSoftware extends ServletAction {
	@Override
	public JSONServletResult execute(HttpServletRequest request) throws Exception {
		JSONServletResult jsonServletResult = new JSONServletResult(Status.SUCCESS);
		try{
			File upgradeFile = new File("Sketchy.jar.ready");
			if (!upgradeFile.exists()){
				throw new Exception("Sketchy Upgrade File not found!");
			}
			
			// rename old file
			File sketchyFile = new File("Sketchy.jar");
			File archiveSketchyFile = new File("Sketchy.jar.old");
			
			boolean renameSuccessful = sketchyFile.renameTo(archiveSketchyFile);
			if (!renameSuccessful){
				throw new Exception("Error archiving existing Sketchy.jar file!");
			}
			
			renameSuccessful = upgradeFile.renameTo(sketchyFile);
			if (!renameSuccessful){
				archiveSketchyFile.renameTo(sketchyFile);
				throw new Exception("Error upgrading Sketchy.jar File!");
			}
		} catch (Throwable t){
			jsonServletResult = new JSONServletResult(Status.ERROR, "Error Saving Hardware Settings! " + t.getMessage());
		}
		return jsonServletResult;
	}

}
