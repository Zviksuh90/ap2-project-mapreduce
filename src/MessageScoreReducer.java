/* 8921105 204785240 Steven (Zvi) Lapp */

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MessageScoreReducer extends Reducer<Text, Text, Text, Text> {

	private Text score = new Text();
	private String temp;

	/* reduce function */
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		long sum = 0;
		while (values.iterator().hasNext()) {
			temp = values.iterator().next().toString();
			sum+=Long.parseLong(temp.split(WordMapper.SEPARATOR)[1]);
		}
		score.set(Long.toString(sum));
		context.write(key, score);
	}
}
