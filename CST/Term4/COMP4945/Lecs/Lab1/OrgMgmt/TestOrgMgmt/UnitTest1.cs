using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
namespace TestProject1
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test1()
        {
            string path = Directory.GetParent(Environment.CurrentDirectory).Parent.Parent.FullName;
            ChromeDriver driver = new ChromeDriver(path + @"\drivers\");
            string url = "http://localhost:5164";
            //ChromeDriver driver = new ChromeDriver();
            driver.Manage().Window.Maximize();
            driver.Navigate().GoToUrl(url);
            driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(5);
            driver.FindElement(By.LinkText("Clients")).Click();
            driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(5);
            driver.FindElement(By.LinkText("Create New")).Click();
            driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(5);
            driver.FindElement(By.Id("Name")).SendKeys("Homer J. Simpson");
            driver.FindElement(By.Id("Address")).SendKeys("Springfield");
            driver.FindElement(By.XPath("//Input[@type='submit']")).Click();
            Assert.Pass();
        }
        [TearDownAttribute]
        public void TearDown()
        {
        }
    }
}