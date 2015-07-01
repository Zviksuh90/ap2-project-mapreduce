/* 8921105 204785240 Steven (Zvi) Lapp */
/*Driver: class that calls main and setsup project*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;

public class Driver {
	final static Logger logger = Logger.getLogger(Driver.class);
	final static String SUCCESS_FILE_NAME = "/_SUCCESS";
	final static String RESULT_FILE_NAME = "/part-r-00000";
	final static String TEMP_FILE_NAME = "/temp";
	public static long INPUT_SCORE = 0;

	/*************************************************************************
	 * function name: removeFile The Input: path in string to file The output:
	 * none The Function operation: deletes file *
	 *************************************************************************/
	private static void removeFile(String s) {
		new File(s).delete();
	}

	/*************************************************************************
	 * function name: removeDir The Input: path in string to dir The output:
	 * none The Function operation: deletes dir *
	 *************************************************************************/
	private static void removeDir(String s) throws IOException {
		FileUtils.deleteDirectory(new File(s));
	}

	/*************************************************************************
	 * function name: copyFile The Input: file objects of source and dest * The
	 * output: none The Function operation: copies from source to dest *
	 *************************************************************************/
	private static void copyFile(String string1, String string2)
			throws IOException {
		Files.copy(new File(string1).toPath(), new File(string2).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private static boolean renameFile(File sourceFile, String name)
			throws IOException {
		// File (or directory) with new name
		File file2 = new File(name);
		if (file2.exists()) {
			throw new java.io.IOException("file exists");
		}
		// Rename file (or directory)
		return sourceFile.renameTo(file2);

	}

	/*************************************************************************
	 * function name: main * The Input: args (input and output paths of
	 * mapreduce) The output: none The Function operation: generates various job
	 * objects and calls them to do job with givin input from path leaving
	 * output in desired path *
	 *************************************************************************/
	public static void drive(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		// instantiating objects for output paths of jobs


		ScoreDriver j1 = new ScoreDriver();
		j1.doJob(args[0],null);


		
		// Open the file
		FileInputStream fstream = new FileInputStream(j1.getPathOutput()
				+ RESULT_FILE_NAME);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine = br.readLine();
		if(strLine!=null){
		StringTokenizer tokenizer = new StringTokenizer(strLine);
		tokenizer.nextToken();
		// initialize score
		INPUT_SCORE=Long.parseLong(tokenizer.nextToken());		
		}else{
			INPUT_SCORE=1000;
		}
		// Close the input stream
		br.close();

		//deleting directory
		removeDir(j1.getPathOutput());

		 ClosestDriver j2 = new ClosestDriver();
		 j2.doJob("./movies/score_driver_data",args[1]);

		 

	}
}
