/* 8921105 204785240 Steven (Zvi) Lapp */
/* InitialDriver: driver that setsup initial read of file of x,y vals */



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class ClosestDriver implements InterfaceDriver {
	private Path output;
	private static final String JOBNAME = "closest_results";
	/*************************************************************************
	 * function name: doJob 
	 * The Input: output for mapreduce subjob
 	 * The output: none 
 	 * The Function operation: instantiates objects to default *
	 *************************************************************************/
	@Override
	public void doJob(String inputPath,String outputPath) throws Exception {
		// initialize job
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, toString());

		job.setJarByClass(ClosestDriver.class);

		// output type for key
		job.setOutputKeyClass(Text.class);
		// output type for value
		job.setOutputValueClass(Text.class);
		
		// setting mapper and reducer
		job.setMapperClass(ClosestMapper.class);
		job.setReducerClass(ClosestReducer.class);

		// name job
		job.setJobName(JOBNAME);

		// setting input and output format
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// setting input  paths
		FileInputFormat.setInputPaths(job, new Path(inputPath));

		// saving output path and outputing
		output = new Path(outputPath+JOBNAME);
		FileOutputFormat.setOutputPath(job, output);

		// run job
		job.waitForCompletion(true);
	}
	/*************************************************************************
	 * function name: getPathOutput 
	 * The Input: none
 	 * The output: sting object of output path 
 	 * The Function operation: returns output path private member*
	 *************************************************************************/
	@Override
	public String getPathOutput() {
		return output.toString();
	}
	/*return  a string representation of the object.*/
	@Override
	public String toString(){
		return JOBNAME;
	}
}
