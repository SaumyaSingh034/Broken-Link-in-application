package snippet;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLink_Amazon {
	static int brokenList=0;
	static int workingLink=0;
	
	public static void main(String[] args)
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\FrameworkPractice\\BrokenLinks\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
		driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
		driver.manage().window().maximize();
		
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("No. of links presnt in application is :: "+links.size());
		
		List<String> urlLinks = new ArrayList<String>();
		for(WebElement e : links)
		{
			String url = e.getAttribute("href");
			 urlLinks.add(url);
			//checkBrokenLinkMethod(url);
		}
		long stTime = System.currentTimeMillis();
		urlLinks.parallelStream().forEach(e -> checkBrokenLinkMethod(e));
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time Taken for execution is :: "+(endTime-stTime));
		System.out.println("Among the total prresent Link "+links.size()+" No. of Broken Links are :: "+brokenList+" and Number of working Links are :: "+workingLink);
		
		driver.quit();
	}
	
	public static void checkBrokenLinkMethod(String linkurl)
	{
		try
		{
			URL url = new URL(linkurl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.connect();
			if(httpURLConnection.getResponseCode() >= 400)
			{
				System.out.println(linkurl + " ----> "+httpURLConnection.getResponseMessage()+" is a Broken Link");
				brokenList++;
			}else
			{
				System.out.println(linkurl+" -------> "+httpURLConnection.getResponseMessage());
				workingLink++;
			}
			
		}
		catch(Exception e)
		{
			
		}
		
		
	}

}
