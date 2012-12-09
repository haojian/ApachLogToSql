package logParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import utils.DBUtil;

public class LogParser {

	private static String path = "/Users/Sunny/Haojian/eclipse/ApachLogToSql/log_nov_1.txt";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		parse_v8(path);
	}
	

	public static void parse_v8(String rawLogFile) throws Exception {
		//String dir = "/Library/WebServer/CGI-Executables/log/";
		//String rawLogFile = dir + "history.log";

		//String dir = "D:\\datasync\\";
		//String rawLogFile = dir + "relevance.txt";
		
		//String rawLogFile = dir + "recalltagger_history.log";
		// String pageContentDir = dir + "data/";
		BufferedReader br = new BufferedReader(new FileReader(rawLogFile));
		String firstLine = br.readLine();
		br.close();
		if (firstLine.contains("uid=")) {
			// the log is for Android.
			br = new BufferedReader(new FileReader(rawLogFile));
			DBUtil db1 = new DBUtil();
			String line;
			db1.execute("DROP TABLE emu_android_success;");
			HashMap<String, String> tableFieldType = new HashMap<String, String>();
			// ---------------------
			tableFieldType.put("uid", "text");
			tableFieldType.put("v", "numeric");
			tableFieldType.put("time", "bigint");
			tableFieldType.put("ev", "text");
			tableFieldType.put("IP", "text");
			tableFieldType.put("Action", "text");
			tableFieldType.put("vx0", "numeric");
			tableFieldType.put("vx1", "numeric");
			tableFieldType.put("vx2", "numeric");
			tableFieldType.put("vx3", "numeric");
			tableFieldType.put("vx4", "numeric");
			tableFieldType.put("vy0", "numeric");
			tableFieldType.put("vy1", "numeric");
			tableFieldType.put("vy2", "numeric");
			tableFieldType.put("vy3", "numeric");
			tableFieldType.put("vy4", "numeric");
			tableFieldType.put("StartUrl", "text");
			tableFieldType.put("FinishUrl", "text");
			tableFieldType.put("HomeUrl", "text");
			tableFieldType.put("OldScale", "numeric");
			tableFieldType.put("NewScale", "numeric");
			tableFieldType.put("OldTabIndex", "integer");
			tableFieldType.put("NewTabIndex", "integer");
			tableFieldType.put("ScrollX", "integer");
			tableFieldType.put("ScrollY", "integer");
			tableFieldType.put("KeyValue", "integer");
			tableFieldType.put("OldViewWidth", "integer");
			tableFieldType.put("OldViewHeight", "integer");
			tableFieldType.put("NewViewWidth", "integer");
			tableFieldType.put("NewViewHeight", "integer");
			tableFieldType.put("TouchPointNum", "integer");
			tableFieldType.put("SearchStr", "text");
			tableFieldType.put("ShortStr", "text");
			tableFieldType.put("LongStr", "text");
			tableFieldType.put("EditText", "text");
			tableFieldType.put("OldOrient", "integer");
			tableFieldType.put("NewOrient", "integer");
			tableFieldType.put("BookmarkUrl", "text");
			tableFieldType.put("BookmarkName", "text");
			tableFieldType.put("MenuItem", "text");
			tableFieldType.put("InputState", "text");
			tableFieldType.put("KeyboardShown", "text");
			tableFieldType.put("MainMenuShown", "text");
			tableFieldType.put("Progress", "integer");
			
			//added by Haojian.
			tableFieldType.put("taskuid", "text");
			tableFieldType.put("taskid", "text");
			tableFieldType.put("serpindex", "text");
			tableFieldType.put("EstimationTimeSeconds", "int");
			tableFieldType.put("pressure0", "numeric");
			tableFieldType.put("pressure1", "numeric");
			tableFieldType.put("pressure2", "numeric");
			tableFieldType.put("pressure3", "numeric");
			tableFieldType.put("pressure4", "numeric");
			tableFieldType.put("touchsize0", "numeric");
			tableFieldType.put("touchsize1", "numeric");
			tableFieldType.put("touchsize2", "numeric");
			tableFieldType.put("touchsize3", "numeric");
			tableFieldType.put("touchsize4", "numeric");
			tableFieldType.put("FrustrationLvl", "numeric");
			tableFieldType.put("RelevanceRate", "numeric");
			tableFieldType.put("val", "numeric");
			tableFieldType.put("UrlStr", "text");
			tableFieldType.put("ShortStr", "text");
			// add more fields here...
			
			line = "CREATE TABLE emu_android_success ( ";
			for (String name : tableFieldType.keySet()) {
				line += name + " " + tableFieldType.get(name) + ", ";
			}
			line = line.substring(0, line.length() - 2) + ");";
			// ---------------------
			db1.execute(line);// create the table
			
			String dbURLNameStr = "starturl";
			String dbURLValueStr = "";
			
			while ((line = br.readLine()) != null) {
				int name_start = 0, name_end = 0;
				int value_start = 0, value_end = 0;
				String dbNameStr = "", dbValueStr = "", name = "", value = "", uid = "";
				name_start = line.indexOf('?', name_start) + 1;
				while (name_start < line.length()) {
					name_end = line.indexOf('=', name_start);
					if (name_end < 0)
						break;// end of events in this line.
					name = line.substring(name_start, name_end);
					if (name.equals("uid")) {// one uid can be used for many
												// events.
						value_start = name_end + 1;
						value_end = line.indexOf('&', value_start);
						if(value_end == -1)
							value_end = line.length()-1;
						value = line.substring(value_start, value_end);
						uid = value;
					} else if (name.equals("[E]")) {// end of this event.
						value_start = name_end + 1;
						value_end = value_start;
						
						/*
						if(!dbNameStr.contains(dbURLNameStr)){
							dbNameStr += dbURLNameStr + ",";
							dbValueStr += "'" + dbURLValueStr + "',";
							//System.out.println(dbValueStr);
						}
						*/
						
						dbNameStr += "uid";
						dbValueStr +=  "'" + uid + "'";// uid is a bigint in the db.
						String sql = "INSERT INTO emu_android_success (" + dbNameStr
								+ ")" + " VALUES (" + dbValueStr + ");";
						// System.out.println(sql);
						db1.executeUpdateSQL(sql);
						dbNameStr = dbValueStr = "";
					} else {
						value_start = name_end + 1;
						value_end = line.indexOf('&', value_start);
						if(value_end == -1)
							value_end = line.length()-1;
						value = line.substring(value_start, value_end);
						if(name.equals("EstimationTimeSeconds") && value.length() == 1){
							name = "RelevanceRate";
							System.out.println(dbValueStr);
							dbValueStr = dbValueStr.replace("SelfEstimation", "RelevanceReport");
							System.out.println(dbValueStr);
						}
						dbNameStr += name + ",";
						System.out.println(name);
						if (tableFieldType.get(name).equals("text")){
							dbValueStr += "'" + value + "',";
						}
						else
							dbValueStr += "" + value + ",";
					}
					name_start = value_end + 1;
				}
			}
			db1.rundown();
		} else {
			// the log is for platforms other than Android.
			// parseRawLog(rawLogFile, null);
			// insertPageContent(pageContentDir, null);
			// assignPageId(null);
			// updatePageWithContentID(null);
		}
		System.err.println("% end...");
	}


}
