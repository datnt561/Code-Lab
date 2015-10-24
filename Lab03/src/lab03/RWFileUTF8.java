package lab03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RWFileUTF8 {
	private static BufferedWriter out;
	private static BufferedReader in;
	
	public static String readFileUTF8(String nameFile) throws IOException{
		File file = new File(nameFile);		
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String text = "";
		String line;
		while((line = in.readLine())!= null){
			text += line + "\n";
		}
		in.close();
		return text;
	}
	
	public static void writeFileUTF8(String text, String nameFile) throws IOException{
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nameFile), "utf-8"));
		out.write(text);
		out.flush();
		out.close();
	}
	
}
