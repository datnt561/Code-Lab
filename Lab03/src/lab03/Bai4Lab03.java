package lab03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Bai4Lab03 {

	public static void main(String[] args) throws IOException {

		BufferedReader in;
		String line = null;
		
		// File Top100NP là file chua cac cau da được gán nhãn
		File file = new File("Top100NP.txt");
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String[] word = null;
		StringBuffer sents = new StringBuffer();
		HashMap<String, Integer> hm = new HashMap();
		while ((line = in.readLine()) != null) {
			// System.out.println(line);
			if (line.contains("/Np") && line.contains("/V") && line.contains("/Np")) {
				word = line.split(" ");

				for (int i = 0; i < word.length - 3; i++) {
					if (word[i].contains("/Np") && word[i + 1].contains("/V") && word[i + 2].contains("/Np")) {
						sents.append(line + "\n");
						break;
					}

				}
				

			}

		}
		RWFileUTF8.writeFileUTF8(sents.toString(),"1000Sent.txt");
		
	}
}
