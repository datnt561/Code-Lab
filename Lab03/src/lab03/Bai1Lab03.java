package lab03;

import java.io.IOException;

import jvnpostag.MaxentTagger;
import jvnsegmenter.CRFSegmenter;
import jvnsensegmenter.JVnSenSegmenter;

public class Bai1Lab03 {

	public static void main(String[] agrs) throws IOException {

		// �?�?c nôi dung từ file van_ban.txt

		String text = RWFileUTF8.readFileUTF8("van_ban.txt");

		System.out.println("chuỗi đã nhập : " + text);

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
				
				output = ws.segmenting(sent) ;
				output = pt.tagging(output) + "\n";
				textTag.append(output);
				
			}
			RWFileUTF8.writeFileUTF8(textTag.toString(),"filename.txt");
		}

	}
}
