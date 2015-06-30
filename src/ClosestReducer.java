/* 8921105 204785240 Steven (Zvi) Lapp */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClosestReducer extends Reducer<Text, Text, Text, Text> {
	private ArrayList<MapEntry<String, Long>> list = new ArrayList<>();
	private Text key = new Text();
	private Text value = new Text();
	private String temp;

	/* reduce function */
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		long dist = 0;

		while (values.iterator().hasNext()) {
			temp = values.iterator().next().toString();
			String filename = temp.split(WordMapper.SEPARATOR)[0];
			dist=Long.parseLong(temp.split(WordMapper.SEPARATOR)[1]);
			list.add(new MapEntry<String,Long>(filename,dist));
		}
		
		
		Collections.sort(list,new EntryComparator());
		int positive=0;
		int negative=0;
		for(int i=0;i<10;i++){
			if(list.get(i).getKey().contains("pos")){
				positive++;
			}else{
				negative++;
			}
		}
		if(negative==positive){
			for(int i=10;i<20;i++){
				if(list.get(i).getKey().contains("pos")){
					positive++;
				}else{
					negative++;
				}
			}
		}
		if(negative<positive){
			this.value.set("positive");
		}else{
			this.value.set("negative");
		}
		this.key.set("result:");
		
		context.write(this.key,this.value);
		
		
	}
}
