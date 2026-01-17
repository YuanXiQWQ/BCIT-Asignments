/*
 * Selenium UI Automation Tests for OrgMgmt Application.
 * http://localhost:5123
 *
 * This test suite covers the following areas:
 * - ClientsTests: CRUD operations for Clients (Create, Read, Edit, Delete)
 * - EmployeesTests: CRUD operations for Employees (Create, Read, Edit, Delete)
 * - ServicesTests: CRUD operations for Services (Create, Read, Edit, Delete)
 * - PhotoUploadTests: Photo upload, replace, delete for Clients and Employees
 * - ServiceAssociationTests: Assign/Remove services to/from Clients and Employees
 * - DatePickerTests: Automated date picker selection for DateOfBirth field
 */

using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;

namespace TestOrgMgmt
{
    // Tests for Client CRUD operations.
    [TestFixture]
    public class ClientsTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests creating a single client and verifies it appears in the Index view.
        [Test]
        public void CreateOneClient_ShouldDisplayInIndex()
        {
            _driver.Navigate().GoToUrl(_baseUrl);
            _driver.FindElement(By.LinkText("Clients")).Click();
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("ClientA");
            _driver.FindElement(By.Id("Address")).SendKeys("Vancouver");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("100.50");

            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1999-12-31';", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("ClientA"));
            Assert.That(pageSource, Does.Contain("Vancouver"));
        }

        // Tests creating three clients and verifies all appear in the Index view.
        [Test]
        public void CreateThreeClients_ShouldDisplayAllInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            var clients = new[]
            {
                ("ClientB", "Surrey", "200.00", "2000-01-01"),
                ("ClientC", "Burnaby", "300.00", "1998-06-15"),
                ("ClientD", "Richmond", "400.00", "2001-03-20")
            };

            foreach (var (name, address, balance, dob) in clients)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Balance")).Clear();
                _driver.FindElement(By.Id("Balance")).SendKeys(balance);

                var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
                ((IJavaScriptExecutor)_driver).ExecuteScript($"arguments[0].value = '{dob}';", dobInput);

                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("ClientB"));
            Assert.That(pageSource, Does.Contain("ClientC"));
            Assert.That(pageSource, Does.Contain("ClientD"));
        }

        // Tests editing a client and verifies changes appear in the Details view.
        [Test]
        public void EditSecondClient_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            var clients = new[]
            {
                ("EditClientA", "Coquitlam", "100.00"),
                ("EditClientB", "Toronto", "200.00"),
                ("EditClientC", "Ottawa", "300.00")
            };

            foreach (var (name, address, balance) in clients)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Balance")).Clear();
                _driver.FindElement(By.Id("Balance")).SendKeys(balance);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EditClientB"));
            Assert.That(targetRow, Is.Not.Null, "EditClientB row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var nameField = _driver.FindElement(By.Id("Name"));
            nameField.Clear();
            nameField.SendKeys("Edited Client");

            var addressField = _driver.FindElement(By.Id("Address"));
            addressField.Clear();
            addressField.SendKeys("Montreal");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("Edited Client"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Edited Client"));
            Assert.That(pageSource, Does.Contain("Montreal"));
        }

        // Tests deleting a client and verifies it no longer appears in the Index view.
        [Test]
        public void DeleteThirdClient_ShouldNotAppearInIndex()
        {
            var uniqueId = Guid.NewGuid().ToString().Replace("-", "").Substring(0, 6).ToUpper().Replace("0", "A").Replace("1", "B").Replace("2", "C").Replace("3", "D")
                .Replace("4", "E").Replace("5", "F").Replace("6", "G").Replace("7", "H").Replace("8", "I").Replace("9", "J");
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            var clients = new[]
            {
                ($"DelClientA{uniqueId}", "Vancouver", "100.00"),
                ($"DelClientB{uniqueId}", "Surrey", "200.00"),
                ($"DelClientC{uniqueId}", "Burnaby", "300.00")
            };

            foreach (var (name, address, balance) in clients)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Balance")).Clear();
                _driver.FindElement(By.Id("Balance")).SendKeys(balance);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains($"DelClientC{uniqueId}"));
            Assert.That(targetRow, Is.Not.Null, "DelClientC row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain($"DelClientA{uniqueId}"));
            Assert.That(pageSource, Does.Contain($"DelClientB{uniqueId}"));
            Assert.That(pageSource, Does.Not.Contain($"DelClientC{uniqueId}"));
        }
    }

    // Tests for Employee CRUD operations.
    [TestFixture]
    public class EmployeesTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests creating a single employee and verifies it appears in the Index view.
        [Test]
        public void CreateOneEmployee_ShouldDisplayInIndex()
        {
            _driver.Navigate().GoToUrl(_baseUrl);
            _driver.FindElement(By.LinkText("Employees")).Click();
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("EmployeeA");
            _driver.FindElement(By.Id("Address")).SendKeys("Richmond");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("50000");

            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1995-07-15';", dobInput);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("EmployeeA"));
            Assert.That(pageSource, Does.Contain("Richmond"));
        }

        // Tests creating three employees and verifies all appear in the Index view.
        [Test]
        public void CreateThreeEmployees_ShouldDisplayAllInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("EmployeeB", "Coquitlam", "40000", "1997-02-28"),
                ("EmployeeC", "Toronto", "50000", "2000-01-01"),
                ("EmployeeD", "Ottawa", "60000", "2002-11-11")
            };

            foreach (var (name, address, salary, dob) in employees)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Salary")).Clear();
                _driver.FindElement(By.Id("Salary")).SendKeys(salary);

                var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
                ((IJavaScriptExecutor)_driver).ExecuteScript($"arguments[0].value = '{dob}';", dobInput);

                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("EmployeeB"));
            Assert.That(pageSource, Does.Contain("EmployeeC"));
            Assert.That(pageSource, Does.Contain("EmployeeD"));
        }

        // Tests editing an employee and verifies changes appear in the Details view.
        [Test]
        public void EditSecondEmployee_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("EditEmpA", "Vancouver", "30000"),
                ("EditEmpB", "Surrey", "40000"),
                ("EditEmpC", "Burnaby", "50000")
            };

            foreach (var (name, address, salary) in employees)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Salary")).Clear();
                _driver.FindElement(By.Id("Salary")).SendKeys(salary);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EditEmpB"));
            Assert.That(targetRow, Is.Not.Null, "EditEmpB row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var nameField = _driver.FindElement(By.Id("Name"));
            nameField.Clear();
            nameField.SendKeys("EditedEmployee");

            var addressField = _driver.FindElement(By.Id("Address"));
            addressField.Clear();
            addressField.SendKeys("Calgary");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("EditedEmployee"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("EditedEmployee"));
            Assert.That(pageSource, Does.Contain("Calgary"));
        }

        // Tests deleting an employee and verifies it no longer appears in the Index view.
        [Test]
        public void DeleteThirdEmployee_ShouldNotAppearInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("DelEmpA", "Richmond", "25000"),
                ("DelEmpB", "Coquitlam", "35000"),
                ("DelEmpC", "Toronto", "45000")
            };

            foreach (var (name, address, salary) in employees)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Name")).SendKeys(name);
                _driver.FindElement(By.Id("Address")).SendKeys(address);
                _driver.FindElement(By.Id("Salary")).Clear();
                _driver.FindElement(By.Id("Salary")).SendKeys(salary);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("DelEmpC"));
            Assert.That(targetRow, Is.Not.Null, "DelEmpC row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DelEmpA"));
            Assert.That(pageSource, Does.Contain("DelEmpB"));
            Assert.That(pageSource, Does.Not.Contain("DelEmpC"));
        }
    }

    // Tests for Service CRUD operations.
    [TestFixture]
    public class ServicesTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests creating a single service and verifies it appears in the Index view.
        [Test]
        public void CreateOneService_ShouldDisplayInIndex()
        {
            _driver.Navigate().GoToUrl(_baseUrl);
            _driver.FindElement(By.LinkText("Services")).Click();
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Type")).SendKeys("Consulting");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("150.00");

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Consulting"));
            Assert.That(pageSource, Does.Contain("150"));
        }

        // Tests creating three services and verifies all appear in the Index view.
        [Test]
        public void CreateThreeServices_ShouldDisplayAllInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");

            var services = new[]
            {
                ("Design", "100.00"),
                ("Development", "200.00"),
                ("Testing", "75.00")
            };

            foreach (var (type, rate) in services)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Type")).SendKeys(type);
                _driver.FindElement(By.Id("Rate")).Clear();
                _driver.FindElement(By.Id("Rate")).SendKeys(rate);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Design"));
            Assert.That(pageSource, Does.Contain("Development"));
            Assert.That(pageSource, Does.Contain("Testing"));
        }

        // Tests editing a service and verifies changes appear in the Details view.
        [Test]
        public void EditSecondService_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");

            var services = new[]
            {
                ("EditSvcA", "50.00"),
                ("EditSvcB", "60.00"),
                ("EditSvcC", "70.00")
            };

            foreach (var (type, rate) in services)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Type")).SendKeys(type);
                _driver.FindElement(By.Id("Rate")).Clear();
                _driver.FindElement(By.Id("Rate")).SendKeys(rate);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EditSvcB"));
            Assert.That(targetRow, Is.Not.Null, "EditSvcB row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var typeField = _driver.FindElement(By.Id("Type"));
            typeField.Clear();
            typeField.SendKeys("EditedService");

            var rateField = _driver.FindElement(By.Id("Rate"));
            rateField.Clear();
            rateField.SendKeys("999.00");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("EditedService"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("EditedService"));
            Assert.That(pageSource, Does.Contain("999"));
        }

        // Tests deleting a service and verifies it no longer appears in the Index view.
        [Test]
        public void DeleteThirdService_ShouldNotAppearInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");

            var services = new[]
            {
                ("DelSvcA", "80.00"),
                ("DelSvcB", "90.00"),
                ("DelSvcC", "100.00")
            };

            foreach (var (type, rate) in services)
            {
                _driver.FindElement(By.LinkText("Create New")).Click();
                _driver.FindElement(By.Id("Type")).SendKeys(type);
                _driver.FindElement(By.Id("Rate")).Clear();
                _driver.FindElement(By.Id("Rate")).SendKeys(rate);
                _driver.FindElement(By.XPath("//input[@type='submit']")).Click();
            }

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("DelSvcC"));
            Assert.That(targetRow, Is.Not.Null, "DelSvcC row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DelSvcA"));
            Assert.That(pageSource, Does.Contain("DelSvcB"));
            Assert.That(pageSource, Does.Not.Contain("DelSvcC"));
        }
    }

    // Tests for photo upload functionality for Clients and Employees.
    [TestFixture]
    public class PhotoUploadTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;
        private string _testImagePath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);

            var testImgFolder = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "testImg");
            var imageFiles = new[] { "1.jpg", "2.png", "3.png", "4.png" };
            var random = new Random();
            _testImagePath = Path.Combine(testImgFolder, imageFiles[random.Next(imageFiles.Length)]);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests uploading a photo for a new client via file input.
        [Test]
        public void UploadClientPhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("PhotoClientA");
            _driver.FindElement(By.Id("Address")).SendKeys("Vancouver");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("500");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("PhotoClientA"));
        }

        // Tests uploading a photo for a new employee via file input.
        [Test]
        public void UploadEmployeePhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("PhotoEmpA");
            _driver.FindElement(By.Id("Address")).SendKeys("Surrey");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("75000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("PhotoEmpA"));
        }

        // Tests replacing an existing client photo with a new one.
        [Test]
        public void ReplaceClientPhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("ReplacePhotoClient");
            _driver.FindElement(By.Id("Address")).SendKeys("Burnaby");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("600");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("ReplacePhotoClient"));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var testImgFolder = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "testImg");
            var imageFiles = new[] { "1.jpg", "2.png", "3.png", "4.png" };
            var random = new Random();
            var newImagePath = Path.Combine(testImgFolder, imageFiles[random.Next(imageFiles.Length)]);

            var editPhotoInput = _driver.FindElement(By.Id("Photo"));
            editPhotoInput.SendKeys(newImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("ReplacePhotoClient"));
        }

        // Tests replacing an existing employee photo with a new one.
        [Test]
        public void ReplaceEmployeePhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("ReplacePhotoEmp");
            _driver.FindElement(By.Id("Address")).SendKeys("Richmond");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("65000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("ReplacePhotoEmp"));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var testImgFolder = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "testImg");
            var imageFiles = new[] { "1.jpg", "2.png", "3.png", "4.png" };
            var random = new Random();
            var newImagePath = Path.Combine(testImgFolder, imageFiles[random.Next(imageFiles.Length)]);

            var editPhotoInput = _driver.FindElement(By.Id("Photo"));
            editPhotoInput.SendKeys(newImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("ReplacePhotoEmp"));
        }

        // Tests deleting an existing client photo using the DeletePhoto checkbox.
        [Test]
        public void DeleteClientPhoto_ShouldSucceed()
        {
            var random = new Random();
            var uniqueId = new string(Enumerable.Range(0, 6).Select(_ => (char)('A' + random.Next(26))).ToArray());
            var clientName = $"DelPhoto{uniqueId}";

            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys(clientName);
            _driver.FindElement(By.Id("Address")).SendKeys("Coquitlam");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("700");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains(clientName));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var deletePhotoCheckbox = _driver.FindElement(By.Id("DeletePhoto"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", deletePhotoCheckbox);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(1000);

            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain(clientName));

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            targetRow = rows.FirstOrDefault(r => r.Text.Contains(clientName));
            Assert.That(targetRow, Is.Not.Null, $"{clientName} row should exist after photo deletion");
            editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var deleteCheckboxes = _driver.FindElements(By.Id("DeletePhoto"));
            Assert.That(deleteCheckboxes.Count, Is.EqualTo(0), "DeletePhoto checkbox should not exist after photo deletion");
        }

        // Tests deleting an existing employee photo using the DeletePhoto checkbox.
        [Test]
        public void DeleteEmployeePhoto_ShouldSucceed()
        {
            var random = new Random();
            var uniqueId = new string(Enumerable.Range(0, 6).Select(_ => (char)('A' + random.Next(26))).ToArray());
            var empName = $"DelPhotoEmp{uniqueId}";

            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys(empName);
            _driver.FindElement(By.Id("Address")).SendKeys("Toronto");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("85000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains(empName));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var deletePhotoCheckbox = _driver.FindElement(By.Id("DeletePhoto"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", deletePhotoCheckbox);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(1000);

            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain(empName));

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            targetRow = rows.FirstOrDefault(r => r.Text.Contains(empName));
            Assert.That(targetRow, Is.Not.Null, $"{empName} row should exist after photo deletion");
            editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var deleteCheckboxes = _driver.FindElements(By.Id("DeletePhoto"));
            Assert.That(deleteCheckboxes.Count, Is.EqualTo(0), "DeletePhoto checkbox should not exist after photo deletion");
        }
    }

    // Tests for assigning and removing services to/from employees.
    [TestFixture]
    public class ServiceAssociationTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests assigning a service to an employee during creation.
        [Test]
        public void AssignServiceToEmployee_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ServiceA");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("120");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ServiceA"));
            Assert.That(serviceRow, Is.Not.Null);

            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("AssocEmpA");
            _driver.FindElement(By.Id("Address")).SendKeys("Vancouver");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("55000");
            var serviceSelect = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelect.SelectByValue(serviceId);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("AssocEmpA"));
        }

        // Tests removing a service from an employee by clearing the ServiceId field.
        [Test]
        public void RemoveServiceFromEmployee_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ServiceB");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("130");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ServiceB"));
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Name")).SendKeys("RemoveSvcEmp");
            _driver.FindElement(By.Id("Address")).SendKeys("Surrey");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("60000");
            var serviceSelect = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelect.SelectByValue(serviceId);
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var empRow = rows.FirstOrDefault(r => r.Text.Contains("RemoveSvcEmp"));
            var empEditLink = empRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", empEditLink);

            var serviceSelectEdit = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelectEdit.SelectByIndex(0);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("RemoveSvcEmp"));
        }

        // Tests assigning a service to a client during creation.
        [Test]
        public void AssignServiceToClient_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ServiceC");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("140");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ServiceC"));
            Assert.That(serviceRow, Is.Not.Null);

            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("AssocClientA");
            _driver.FindElement(By.Id("Address")).SendKeys("Burnaby");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("800");
            var serviceSelect = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelect.SelectByValue(serviceId);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource2 = _driver.PageSource;
            Assert.That(pageSource2, Does.Contain("AssocClientA"));
        }

        // Tests removing a service from a client by clearing the ServiceId field.
        [Test]
        public void RemoveServiceFromClient_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ServiceD");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("150");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ServiceD"));
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Name")).SendKeys("RemoveSvcClient");
            _driver.FindElement(By.Id("Address")).SendKeys("Richmond");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("900");
            var serviceSelect = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelect.SelectByValue(serviceId);
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var clientRow = rows.FirstOrDefault(r => r.Text.Contains("RemoveSvcClient"));
            var clientEditLink = clientRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", clientEditLink);

            var serviceSelectEdit = new SelectElement(_driver.FindElement(By.Id("ServiceId")));
            serviceSelectEdit.SelectByIndex(0);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource2 = _driver.PageSource;
            Assert.That(pageSource2, Does.Contain("RemoveSvcClient"));
        }
    }

    // Tests for automated date picker selection using JavaScript.
    [TestFixture]
    public class DatePickerTests
    {
        private ChromeDriver _driver = null!;
        private string _baseUrl = "http://localhost:5123";
        private string _driverPath = null!;

        [SetUp]
        public void Setup()
        {
            _driverPath = Path.Combine(Directory.GetParent(Environment.CurrentDirectory)!.Parent!.Parent!.FullName, "drivers");
            var options = new ChromeOptions();
            _driver = new ChromeDriver(_driverPath, options);
            _driver.Manage().Window.Maximize();
            _driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();
        }

        // Tests selecting a date of birth for a client using JavaScript.
        [Test]
        public void SelectDateOfBirth_UsingDatePicker_ForClient()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("DatePickClient");
            _driver.FindElement(By.Id("Address")).SendKeys("Ottawa");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("700");

            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '2000-12-25';", dobInput);
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].dispatchEvent(new Event('change'));", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var clientRow = rows.FirstOrDefault(r => r.Text.Contains("DatePickClient"));
            Assert.That(clientRow, Is.Not.Null);
            var detailsLink = clientRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DatePickClient"));
            Assert.That(pageSource, Does.Contain("2000"));
        }

        // Tests selecting a date of birth for an employee using JavaScript.
        [Test]
        public void SelectDateOfBirth_UsingDatePicker_ForEmployee()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("DatePickEmp");
            _driver.FindElement(By.Id("Address")).SendKeys("Calgary");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("80000");

            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1999-07-04';", dobInput);
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].dispatchEvent(new Event('change'));", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var empRow = rows.FirstOrDefault(r => r.Text.Contains("DatePickEmp"));
            Assert.That(empRow, Is.Not.Null);
            var detailsLink = empRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DatePickEmp"));
            Assert.That(pageSource, Does.Contain("1999"));
        }
    }
}