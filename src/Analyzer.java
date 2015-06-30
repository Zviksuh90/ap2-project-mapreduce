import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Analyzer {
	static public long getScore(String word) throws Exception {
		long score = 0;
		// load json
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			//System.out.println("Working Directory = " +
		      //        System.getProperty("user.dir"));
			Object obj = parser.parse(new FileReader("./words.json"));

			jsonObject = (JSONObject) obj;
			if (jsonObject.get(word.toLowerCase()) != null) {
				score = (long) jsonObject.get(word.toLowerCase());
			} else {
				score=0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * if(jsonObject!=null){ Set<?> keys= jsonObject.keySet(); Iterator<?>
		 * it=keys.iterator(); while( it.hasNext() ) { String key =
		 * (String)it.next(); if ( jsonObject.get(key) instanceof JSONObject ) {
		 * //checking if word contained in message int lastIndex = 0; int count
		 * = 0; while(lastIndex != -1){
		 * 
		 * lastIndex = message.indexOf(key,lastIndex);
		 * 
		 * if(lastIndex != -1){ count ++; lastIndex += key.length(); } } int
		 * weight=(int)jsonObject.get(key); score+=(count*weight); } } }
		 */

		return score;
	}
}
