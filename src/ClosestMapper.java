/* 8921105 204785240 Steven (Zvi) Lapp */


import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class ClosestMapper extends Mapper<LongWritable, Text, Text, Text> {
	public static final String SEPARATOR = ",";
	public static final Text DUMMYKEY = new Text("blabla");
	
	private Text value = new Text();
	private Long input_score = null;
	/* map function */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		StringTokenizer tokenizer = new StringTokenizer(line);

		String fileName=tokenizer.nextToken();
		if(input_score==null){
			input_score=new Long(Driver.INPUT_SCORE);
		}
		long dist =Math.abs(Long.parseLong(tokenizer.nextToken())-input_score); 
		this.value.set(fileName+SEPARATOR+Long.toString(dist));
		
		context.write(DUMMYKEY, this.value);
	}
}
