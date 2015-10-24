package lab03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jvnpostag.MaxentTagger;
import jvnsegmenter.CRFSegmenter;
import jvnsensegmenter.JVnSenSegmenter;

public class Bai3Lab03 {
	public static void main(String[] agrs) throws IOException {
		String text = RWFileUTF8.readFileUTF8("baoVanHoa.txt");

		// System.out.println("chuỗi đã nhập : " + text);

		if (!text.isEmpty()) {

			// Tách câu
			JVnSenSegmenter sd = new JVnSenSegmenter();
			sd.init("models/jvnsensegmenter");
			String[] sents = sd.senSegment(text).split("\n");

			// Tách từ
			CRFSegmenter ws = new CRFSegmenter();
			ws.init("models/jvnsegmenter");

			String output;
			StringBuffer textTag = new StringBuffer();
			MaxentTagger pt = new MaxentTagger("models/jvnpostag/maxent");
			for (String sent : sents) {

				output = ws.segmenting(sent);
				output = pt.tagging(output) + "\n";
				textTag.append(output);

			}
			RWFileUTF8.writeFileUTF8(textTag.toString(), "Top100NP.txt");
		}

		// Đọc từng dòng tim ra danh từ riêng
		BufferedReader in;
		String line = null;
		File file = new File("Top100NP.txt");
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		text = "";
		String[] np = null;
		HashMap<String, Integer> hm = new HashMap();
		while ((line = in.readLine()) != null) {
			System.out.println(line);

			System.out.println("-----------------------------------------");
			line = line.replaceAll("\\p{Punct}(?=\\s|$)", "");
			np = line.split(" ");
			for (String s : np) {
				if (s.contains("/Np")) {
					if (!s.isEmpty() && s.charAt(0) == '_')
						s = s.substring(1);
					s = s.replaceAll("/Np", "");

					if (hm.containsKey(s) == false) {
						hm.put(s, 1);
					} else {
						hm.put(s, hm.get(s) + 1);
					}
				}
			}
		}
		in.close();
		
		// Sắp xếp HashMap
		Set<Entry<String, Integer>> set = hm.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Tạo text để ghi ra file
		StringBuffer word = new StringBuffer();
		int i = 1;
		for (Map.Entry<String, Integer> entry : list) {
			if (i > 100)
				break;

			word.append(entry.getKey().toString() + ":" + entry.getValue().toString() + "\t");

			i++;
		}
		
		// Ghi ra file
		RWFileUTF8.writeFileUTF8(word.toString(), "100Np.txt");
	}
}
