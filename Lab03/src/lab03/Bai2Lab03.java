package lab03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jvnpostag.MaxentTagger;
import jvnsegmenter.CRFSegmenter;
import jvnsensegmenter.JVnSenSegmenter;
	
public class Bai2Lab03 {
	public static String standerStringForSent(String s){
		String result = null;
		// Chữ hoa thành chữ thường
		result = s.toLowerCase();
	
		// Thay 1 số ký tự đặc biệt bằng tứ tự rỗng
		result = s.replaceAll("[-/[0-9]+%()><'\"&^%$#@~`\"]", "");
		return result;
	}
	public static void main(String[] agrs) throws IOException {
		// Document doc =
		// Jsoup.connect("http://dantri.com.vn/the-thao.htm").get();
		//
		//// if (doc != null) {
		//// System.out.println(doc.toString());
		//// }
		// String title = doc.title();
		// System.out.println(doc.tagName("a"));
		// //Elements content = doc.select(".mt3");
		// Elements content = doc.select("a[href$=.htm]");
		//// System.out.println(content.toString());
		// int size = content.size();
		// System.out.println(content.text());
		//// String doman = "http://dantri.com.vn/";
		//// ArrayList<String> links = new ArrayList<String>();
		//// String temp;
		////// // Tạo URL của mỗi bài báo lấy về
		//// for (Element c : content) {
		//// if (c != null) {
		//// temp = doman + c.attr("href");
		//// System.out.println(c.toString());
		//// links.add(temp);
		//// }
		////
		//// }
		//// // In ra URL mỗi bài báo
		//// for (String url : links) {
		//// System.out.println(url);
		//// }
		//
		//// Elements text = null;
		////
		//// for (String url : links) {
		//// System.out.println(url);
		//// doc = Jsoup.connect(url).userAgent("Mozilla").get();
		////
		//// text = doc.select("#divNewsContent > p ");
		//// if (text != null) {
		////
		//// for (Element e : text) {
		//// System.out.println(e.text());
		//// }
		//// }
		////
		//// else
		//// System.out.println("rông");
		////
		//// for (Element c : content) {
		//// System.out.println(c.text());
		////
		//// }
		//// System.out.println("=================================================================================");
		//// }
		//

		// Lấy dữ liệu từ file
		String text = RWFileUTF8.readFileUTF8("baoTheThao.txt");

		String[] sents = null;
		String output;

		HashMap<String, Integer> hm = new HashMap();
		if (!text.isEmpty()) {

			// Tách câu
			JVnSenSegmenter sd = new JVnSenSegmenter();
			sd.init("models/jvnsensegmenter");
			sents = sd.senSegment(text).split("\n");

			// Tách từ
			CRFSegmenter ws = new CRFSegmenter();
			ws.init("models/jvnsegmenter");
			for (String sent : sents) {

				// Chuẩn hóa cho câu
				sent = Bai2Lab03.standerStringForSent(sent);
				output = ws.segmenting(sent);

				// Chia câu thành các từ
				String[] arrSent = output.split("[ ,.:;!?]");

				// Thống kê các từ rồi ghi vào HashMap
				for (String s : arrSent) {

					if (!s.isEmpty() && s.charAt(0) == '_')
						s = s.substring(1);
					if (hm.containsKey(s) == false) {
						hm.put(s, 1);
					} else {
						hm.put(s, hm.get(s) + 1);
					}
				}
			}
			
			// Xóa các phần tử là rỗng
			hm.remove("");

			// Sắp xêp theo giá trị của HashMap
			Set<Entry<String, Integer>> set = hm.entrySet();
			List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});

			// Ghi ra 100 từ xuất hiện nhiều nhất ra file Count100Word.txt
			StringBuffer word  = new StringBuffer();
			
			int i = 1;
			for (Map.Entry<String, Integer> entry : list) {
				if (i > 100)
					break;
				
				word.append( entry.getKey().toString() + ":" + entry.getValue().toString() + "\t");
				
				
				i++;
			}
			RWFileUTF8.writeFileUTF8(word.toString(),"Count100Word.txt");
			
			
		}
	}

}