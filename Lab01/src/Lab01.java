import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Lab01 {
	public static void main(String[] args) throws IOException {
		// Bài 1 phân tích các bài báo nói về Mỹ Linh ở baomoi.com
		try {
			Document doc;
			doc = Jsoup.connect("http://www.baomoi.com/tim-kiem/M%E1%BB%B9-Linh.epi").get();

			Elements elements = doc.select("section.content-list").select("article");

			for (Element e : elements) {
				System.out.println("Link ảnh :\n" + e.select("figure > a > img").attr("src"));
				System.out.println("Tiêu đề :\n" + e.select("a").text() + "\n");
				System.out.println("Tác giả :\n" + e.select("p.meta > a").text() + "\n");
				System.out.println("Thời gian đăng :\n" + e.select("time").attr("datetime") + "\n");
				System.out.println("Tóm tắt :\n" + e.select("p.summary").text() + "\n");
				System.out.println("======================================================================");

			}
			System.out.println("Tổng số page về 'Mỹ Linh' là " + elements.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bài 2 thu thập thông tin của các album nhạc Việt trong Zing MP3
		try {
			Document doc2;
			doc2 = Jsoup.connect("http://mp3.zing.vn/the-loai-album/Viet-Nam/IWZ9Z08I.html").get();

			Elements elements2 = doc2.select("div.item");

			for (Element e : elements2) {
				System.out.println("Link ảnh : \n" + e.select("a.thumb img").attr("src"));
				System.out.println("Mô tả : \n" + e.select(" div.description a").text());
				System.out.println("======================================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bài 3 thu thập các link liên kết ảnh tại trang: xomnhiepanh.com
		try {
			Document doc3;

			doc3 = Jsoup.connect("http://xomnhiepanh.com/?mod=gallery").userAgent("Mozilla").get();

			Elements elements3 = doc3.getElementsByTag("img");

			for (Element e : elements3) {
				System.out.println("Link ảnh : \n" + e.attr("src"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Bài 4 thu thập thông tin về điện thoại tại trang thegioididong.com
		try {
			Document doc4;

			doc4 = Jsoup.connect("https://www.thegioididong.com/").userAgent("Mozilla").get();

			Elements elements4 = doc4.select("ul.homeproduct li");

			for (Element e : elements4) {
				System.out.println("Link ảnh : " + e.select("img").attr("src"));
				System.out.println("Tên điện thoại : " + e.select("figure.bginfo h3").text());
				System.out.println("Giá : " + e.select("figure.bginfo strong").text());
				System.out.println(" Thông số kỹ thuật : \n");

				Elements info = e.select("figure.bginfo span");
				for (Element i : info)
					System.out.println(i.text());
				System.out.println("======================================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}