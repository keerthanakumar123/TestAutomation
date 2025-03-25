import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static java.lang.Thread.sleep;

public class AmazonCheckoutTest {
    WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
        sleep(2000);
        driver.navigate().refresh();
    }

    @Test(priority = 1)
    public void signIn() throws InterruptedException
    {
        driver.findElement(By.xpath("//span[contains(text(), 'sign in')]")).click();
        sleep(2000);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("keerthanakumar722@gmail.com");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys("TestAutomation@1");
        driver.findElement(By.xpath("//input[@id='signInSubmit']")).click();
        sleep(2000);
    }

    @Test(priority = 2)
    public void searchAndAddToCart() throws InterruptedException {
        // Locate the search box and search for "Mobile Phone"
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("Mobile Phone");
        searchBox.sendKeys(Keys.ENTER);

        // Click on the first product
        WebElement firstProduct = driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
        firstProduct.click();

        // Assert that the cart has been updated
        sleep(2000);
        WebElement cartCount = driver.findElement(By.id("nav-cart-count"));
        Assert.assertNotEquals(cartCount.getText(), "0", "Cart is empty after adding product!");
    }

    @Test(priority = 3)
    public void proceedToCheckout() throws InterruptedException {
        // Go to cart
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
        sleep(2000);
        driver.findElement(By.id("nav-cart")).click();

        // Click on "Proceed to Buy"
        WebElement proceedToBuyButton = driver.findElement(By.name("proceedToRetailCheckout"));
        proceedToBuyButton.click();
    }

    @Test(priority = 4)
    public void verifyPayment() throws InterruptedException {
        boolean isPayNowDisplayed = driver.findElement(By.xpath("//*[contains(text(),'Use this payment method')]")).isDisplayed();
        Assert.assertTrue(isPayNowDisplayed);
    }

    @Test(priority = 5)
    public void verifyPaymentFalseCase() throws InterruptedException {
        boolean isPayNowDisplayed = driver.findElement(By.xpath("//*[contains(text(),'Use this payment method')]")).isDisplayed();
        Assert.assertFalse(isPayNowDisplayed);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
