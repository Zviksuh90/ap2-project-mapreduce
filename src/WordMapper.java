/* 8921105 204785240 Steven (Zvi) Lapp */

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordMapper extends Mapper<LongWritable, Text, Text, Text> {
	public static final String SEPARATOR = ",";
	// public static final Text DUMMYKEY = new Text("m");
	private Text word = new Text();
	private Text key = new Text();
	private Text value = new Text();

	/* map function */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		StringTokenizer tokenizer = new StringTokenizer(line);

		while (tokenizer.hasMoreTokens()) {
			word.set(tokenizer.nextToken());
			if (!Pattern.matches("\\p{Punct}", word.toString())) {
				long score = 0;
				try {
					score = Analyzer.getScore(word.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (score != 0) {
					this.key.set(((FileSplit) context.getInputSplit())
							.getPath().getName());
					this.value.set(word.toString() + SEPARATOR
							+ Long.toString(score));
					context.write(this.key, this.value);
				}
			}
		}
	}
}
