package JavaHw;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class KeelungSightsCrawler {

	public static final String TARGET_URL = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";

	private static Sight[] sights;

	public Sight[] getItems(String zone) throws IOException, IndexOutOfBoundsException {
		// 啟動jsoup
		Connection.Response res = Jsoup.connect(TARGET_URL) // 去爬TARGET_URL的網址內容
				.timeout(8000) // 連線8秒
				.validateTLSCertificates(false)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0")
				.method(Method.GET).execute();

		Document doc = res.parse(); // 分析網頁

		/* 爬蟲的部分通通都從html裡面分析，找位置！ */

		// 取得html裡面id為guide-point，Class為box的part，獲取包含地區文字的部分
		Elements locationNames = doc.getElementById("guide-point").getElementsByClass("box");
		Elements sightURL = locationNames.select("h4:contains(" + zone + ")"); // 找到傳進來參數地區名
		String sightsURL[] = getSightsURL(sightURL.next()); // 找到地區下的所有景點

		return getKeelungSightsInfo(sightsURL); // 獲得景點的所有資訊
	}

	// 獲得傳入地區的每個景點的網址內容
	public static Sight[] getKeelungSightsInfo(String[] sightsURL) throws IOException {
		ArrayList<Sight> sightsList = new ArrayList<Sight>(); // 存景點的內容
		for (String web : sightsURL) {
			// 啟動jsoup
			Connection.Response res = Jsoup.connect(web) // 去爬TARGET_URL的網址內容
					.timeout(8000) // 連線8秒
					.validateTLSCertificates(false)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0")
					.method(Method.GET).execute();

			Document doc = res.parse(); // 分析網頁

			Elements sightWeb = doc.getAllElements(); // 獲得整個網頁的元素

			String zone = getZone(doc); // 獲得地區名
			String sightName = getSightName(doc); // 獲得個別地區的景點名
			String category = getCategory(sightWeb); // 獲得景點種類
			String photoURL = getPhotoURL(doc); // 獲得景點照片網址
			String description = getDescription(doc); // 獲得景點介紹
			String address = getAddress(doc); // 獲得景點地址

			Sight sight = new Sight();
			sight.setZone(zone);
			sight.setSightName(sightName);
			sight.setCategory(category);
			sight.setPhotoURL(photoURL);
			sight.setDescription(description);
			sight.setAddress(address);
			sightsList.add(sight); // 存入sightsList裡
		}
		// 將sightsList轉換成sightArray
		sights = new Sight[sightsList.size()];
		sights = sightsList.toArray(sights);
		return sights;
	}

	private static String[] getSightsURL(Elements webElements) // 獲取景點網址
	{
		ArrayList<String> sightsHtmlList = new ArrayList<String>(); // 存景點網址

		Elements sightHtmlWithTag = webElements.select("ul").select("a"); // 選取tag為ul為a的部分，準備選取網址
		for (Element node : sightHtmlWithTag) // 把景點網址放進arraylist
		{
			String text = node.attr("href"); // 抓取href的內容
			sightsHtmlList.add("https://www.travelking.com.tw" + text); // 個別存進arraylist
			// 注意，這裡的網址只有相對路徑，要改成絕對路徑
		}
		// 將sightsList轉換成StringArray
		String[] realSightHtml = new String[sightsHtmlList.size()];
		realSightHtml = sightsHtmlList.toArray(realSightHtml);
		return realSightHtml;
	}

	private static String getZone(Document webElements) // 獲取地區名
	{
		Elements zoneWithTag = webElements.getElementsByClass("bc_last").select("a"); // 獲取有地區名的行數內容
		String zone = zoneWithTag.text(); // 過濾標籤，變純文字
		return zone;
	}

	private static String getSightName(Document webElements) // 獲取景點名
	{
		// 獲取有景點名的行數內容
		Elements sightNameWithTag = webElements.getElementsByClass("h1").select("span");
		String sightName = sightNameWithTag.text(); // 過濾標籤，變純文字
		return sightName;
	}

	public static String getCategory(Elements webElements) // 獲得景點種類
	{
		Element categoryWithTag = webElements.select("strong").get(0); // 拿到有景點種類的行數內容
		String category = categoryWithTag.text(); // 過濾tag，變純文字
		return category;
	}

	public static String getPhotoURL(Document webElements) // 獲得景點地址
	{
		// 獲取有景點地址的行數內容
		Element photoURLWithTag = webElements.getElementById("point_area").select("meta").get(2);
		String photoURL = photoURLWithTag.attr("content"); // 過濾tag，且找到content的內容
		return photoURL;
	}

	public static String getDescription(Document webElements) // 獲得景點描述
	{
		// 獲取有景點描述的行數內容
		Element descriptionWithTag = webElements.getElementById("point_area").select("meta").get(3);
		String description = descriptionWithTag.attr("content"); // 過濾tag，且找到content的內容
		return description;
	}

	public static String getAddress(Document webElements) // 獲得景點地址
	{
		// 獲取有景點地址的行數內容
		Element addressWithTag = webElements.getElementById("point_area").select("meta").get(4);
		String address = addressWithTag.attr("content"); // 過濾tag，且找到content的內容
		return address;
	}
}
