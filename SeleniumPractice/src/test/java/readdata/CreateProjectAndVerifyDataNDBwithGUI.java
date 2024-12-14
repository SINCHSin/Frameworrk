package readdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.mysql.jdbc.Driver;

public class CreateProjectAndVerifyDataNDBwithGUI {

	public static void main(String[] args) throws InterruptedException, SQLException {
		Random r = new Random();
	int	count=r.nextInt(1000);
		System.out.println(count);
		String projectname = "fb_"+count;
		
WebDriver driver = new ChromeDriver();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
driver.get("http://49.249.28.218:8091");
driver.findElement(By.id("username")).sendKeys("rmgyantra");
driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
driver.findElement(By.xpath("//button[text()='Sign in']")).click();
driver.findElement(By.linkText("Projects")).click();
driver.findElement(By.xpath("//span[text()='Create Project']")).click();
driver.findElement(By.name("projectName")).sendKeys(projectname);
driver.findElement(By.name("createdBy")).sendKeys("deepak");

//driver.findElement(By.name("status"));
Select sel = new Select(driver.findElement(By.xpath("(//select[@name='status'])[2]")));

sel.selectByVisibleText("Created ");
driver.findElement(By.xpath("//input[@value='Add Project']")).click();
boolean flag = false;
Driver  driverref = new Driver();
DriverManager.registerDriver(driverref);
Connection con = DriverManager .getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm", "root", "root");
Statement stat =con.createStatement();
ResultSet rset =stat.executeQuery("select * from project where project_name='"+projectname+"';");
while(rset.next()) {
	String actualname = rset.getString(4);
	if(projectname.equalsIgnoreCase(actualname)) {
		flag = true;
		System.out.println(projectname + "is available");
	}
}
if(flag==false) {
	System.out.println(projectname + "is not available");
	  
}


con.close();




	}

}
