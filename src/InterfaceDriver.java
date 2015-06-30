/* 8921105 204785240 Steven (Zvi) Lapp */
/* InterfaceDriver: interface for subdriver classes in mapreduce project*/


public interface InterfaceDriver {
	/*************************************************************************
	 * function name: doJob 
	 * The Input: output for mapreduce subjob
 	 * The output: none 
 	 * The Function operation: instantiates objects to default *
	 *************************************************************************/
	public void doJob(String outPath,String outputPath) throws Exception;
	/*************************************************************************
	 * function name: getPathOutput 
	 * The Input: none
 	 * The output: sting object of output path 
 	 * The Function operation: returns output path private member*
	 *************************************************************************/
	public String getPathOutput();
	/*return  a string representation of the object.*/
	public String toString();
}
