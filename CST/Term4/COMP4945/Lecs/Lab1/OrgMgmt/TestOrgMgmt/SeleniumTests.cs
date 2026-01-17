/*
 * Selenium UI Automation Tests for OrgMgmt Application.
 *
 * This test suite covers the following areas:
 * - ClientsTests: CRUD operations for Clients (Create, Read, Edit, Delete)
 * - EmployeesTests: CRUD operations for Employees (Create, Read, Edit, Delete)
 * - ServicesTests: CRUD operations for Services (Create, Read, Edit, Delete)
 * - PhotoUploadTests: Photo upload, replace for Clients and Employees
 * - ServiceAssociationTests: Assign/Remove services to/from Employees
 * - DatePickerTests: Automated date picker selection for DateOfBirth field
 *
 * Prerequisites:
 * - OrgMgmt application must be running on http://localhost:5123
 * - ChromeDriver must be present in the drivers folder
 */

using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

namespace TestOrgMgmt
{
    /// <summary>
    /// Tests for Client CRUD operations.
    /// </summary>
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

        /// <summary>
        /// Tests creating a single client and verifies it appears in the Index view.
        /// </summary>
        [Test]
        public void CreateOneClient_ShouldDisplayInIndex()
        {
            _driver.Navigate().GoToUrl(_baseUrl);
            _driver.FindElement(By.LinkText("Clients")).Click();
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Alice Test");
            _driver.FindElement(By.Id("Address")).SendKeys("Vancouver");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("100.50");

            // Date picker automation - set date via JavaScript for HTML5 date input
            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1990-05-15';", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Verify client appears in Index
            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Alice Test"));
            Assert.That(pageSource, Does.Contain("Vancouver"));
        }

        /// <summary>
        /// Tests creating three clients and verifies all appear in the Index view.
        /// </summary>
        [Test]
        public void CreateThreeClients_ShouldDisplayAllInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            var clients = new[]
            {
                ("Bob One", "Toronto", "200.00", "1985-03-10"),
                ("Carol Two", "Montreal", "300.00", "1992-07-22"),
                ("Dave Three", "Calgary", "400.00", "1988-11-05")
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
            Assert.That(pageSource, Does.Contain("Bob One"));
            Assert.That(pageSource, Does.Contain("Carol Two"));
            Assert.That(pageSource, Does.Contain("Dave Three"));
        }

        /// <summary>
        /// Tests editing a client and verifies changes appear in the Details view.
        /// </summary>
        [Test]
        public void EditSecondClient_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            // Create 3 clients first
            var clients = new[]
            {
                ("Edit One", "City A", "100.00"),
                ("Edit Two", "City B", "200.00"),
                ("Edit Three", "City C", "300.00")
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

            // Find and click Edit on the second row
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("Edit Two"));
            Assert.That(targetRow, Is.Not.Null, "Edit Two row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // Edit the client
            var nameField = _driver.FindElement(By.Id("Name"));
            nameField.Clear();
            nameField.SendKeys("Edited Client");

            var addressField = _driver.FindElement(By.Id("Address"));
            addressField.Clear();
            addressField.SendKeys("Edited City");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Navigate to Details of the edited client
            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("Edited Client"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Edited Client"));
            Assert.That(pageSource, Does.Contain("Edited City"));
        }

        /// <summary>
        /// Tests deleting a client and verifies it no longer appears in the Index view.
        /// </summary>
        [Test]
        public void DeleteThirdClient_ShouldNotAppearInIndex()
        {
            var uniqueId = Guid.NewGuid().ToString().Replace("-", "").Substring(0, 6).ToUpper().Replace("0", "A").Replace("1", "B").Replace("2", "C").Replace("3", "D")
                .Replace("4", "E").Replace("5", "F").Replace("6", "G").Replace("7", "H").Replace("8", "I").Replace("9", "J");
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");

            // Create 3 clients with unique names
            var clients = new[]
            {
                ($"DelA{uniqueId}", "City X", "100.00"),
                ($"DelB{uniqueId}", "City Y", "200.00"),
                ($"DelC{uniqueId}", "City Z", "300.00")
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

            // Find and click Delete on the third client by unique name
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains($"DelC{uniqueId}"));
            Assert.That(targetRow, Is.Not.Null, "DelC row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            // Confirm deletion
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Verify: DelA and DelB still exist, DelC is gone
            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain($"DelA{uniqueId}"));
            Assert.That(pageSource, Does.Contain($"DelB{uniqueId}"));
            Assert.That(pageSource, Does.Not.Contain($"DelC{uniqueId}"));
        }
    }

    /// <summary>
    /// Tests for Employee CRUD operations.
    /// </summary>
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

        /// <summary>
        /// Tests creating a single employee and verifies it appears in the Index view.
        /// </summary>
        [Test]
        public void CreateOneEmployee_ShouldDisplayInIndex()
        {
            _driver.Navigate().GoToUrl(_baseUrl);
            _driver.FindElement(By.LinkText("Employees")).Click();
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("John Emp");
            _driver.FindElement(By.Id("Address")).SendKeys("Burnaby");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("50000");

            // Date picker automation
            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1985-08-20';", dobInput);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("John Emp"));
            Assert.That(pageSource, Does.Contain("Burnaby"));
        }

        /// <summary>
        /// Tests creating three employees and verifies all appear in the Index view.
        /// </summary>
        [Test]
        public void CreateThreeEmployees_ShouldDisplayAllInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("Emp One", "City A", "40000", "1980-01-15"),
                ("Emp Two", "City B", "50000", "1985-06-20"),
                ("Emp Three", "City C", "60000", "1990-12-25")
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
            Assert.That(pageSource, Does.Contain("Emp One"));
            Assert.That(pageSource, Does.Contain("Emp Two"));
            Assert.That(pageSource, Does.Contain("Emp Three"));
        }

        /// <summary>
        /// Tests editing an employee and verifies changes appear in the Details view.
        /// </summary>
        [Test]
        public void EditSecondEmployee_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("EmpEdit One", "Loc A", "30000"),
                ("EmpEdit Two", "Loc B", "40000"),
                ("EmpEdit Three", "Loc C", "50000")
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
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EmpEdit Two") || r.Text.Contains("SvcEdit Two") || r.Text.Contains("Edit Two"));
            Assert.That(targetRow, Is.Not.Null, "Target row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var nameField = _driver.FindElement(By.Id("Name"));
            nameField.Clear();
            nameField.SendKeys("Edited Emp");

            var addressField = _driver.FindElement(By.Id("Address"));
            addressField.Clear();
            addressField.SendKeys("Edited Loc");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("Edited Emp"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Edited Emp"));
            Assert.That(pageSource, Does.Contain("Edited Loc"));
        }

        /// <summary>
        /// Tests deleting an employee and verifies it no longer appears in the Index view.
        /// </summary>
        [Test]
        public void DeleteThirdEmployee_ShouldNotAppearInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");

            var employees = new[]
            {
                ("EmpDel One", "Place X", "25000"),
                ("EmpDel Two", "Place Y", "35000"),
                ("EmpDel Three", "Place Z", "45000")
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
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EmpDel Three") || r.Text.Contains("SvcDel Three") || r.Text.Contains("Del Three"));
            Assert.That(targetRow, Is.Not.Null, "Target row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("EmpDel One"));
            Assert.That(pageSource, Does.Contain("EmpDel Two"));
            Assert.That(pageSource, Does.Not.Contain("EmpDel Three"));
        }
    }

    /// <summary>
    /// Tests for Service CRUD operations.
    /// </summary>
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

        /// <summary>
        /// Tests creating a single service and verifies it appears in the Index view.
        /// </summary>
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

        /// <summary>
        /// Tests creating three services and verifies all appear in the Index view.
        /// </summary>
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

        /// <summary>
        /// Tests editing a service and verifies changes appear in the Details view.
        /// </summary>
        [Test]
        public void EditSecondService_ShouldReflectInDetails()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");

            var services = new[]
            {
                ("SvcEdit One", "50.00"),
                ("SvcEdit Two", "60.00"),
                ("SvcEdit Three", "70.00")
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
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EmpEdit Two") || r.Text.Contains("SvcEdit Two") || r.Text.Contains("Edit Two"));
            Assert.That(targetRow, Is.Not.Null, "Target row should exist");
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            var typeField = _driver.FindElement(By.Id("Type"));
            typeField.Clear();
            typeField.SendKeys("Edited Svc");

            var rateField = _driver.FindElement(By.Id("Rate"));
            rateField.Clear();
            rateField.SendKeys("999.00");

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var editedRow = rows.FirstOrDefault(r => r.Text.Contains("Edited Svc"));
            Assert.That(editedRow, Is.Not.Null);
            var detailsLink = editedRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Edited Svc"));
            Assert.That(pageSource, Does.Contain("999"));
        }

        /// <summary>
        /// Tests deleting a service and verifies it no longer appears in the Index view.
        /// </summary>
        [Test]
        public void DeleteThirdService_ShouldNotAppearInIndex()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");

            var services = new[]
            {
                ("SvcDel One", "80.00"),
                ("SvcDel Two", "90.00"),
                ("SvcDel Three", "100.00")
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
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("EmpDel Three") || r.Text.Contains("SvcDel Three") || r.Text.Contains("Del Three"));
            Assert.That(targetRow, Is.Not.Null, "Target row should exist before deletion");
            var deleteLink = targetRow!.FindElement(By.LinkText("Delete"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", deleteLink);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("SvcDel One"));
            Assert.That(pageSource, Does.Contain("SvcDel Two"));
            Assert.That(pageSource, Does.Not.Contain("SvcDel Three"));
        }
    }

    /// <summary>
    /// Tests for photo upload functionality for Clients and Employees.
    /// </summary>
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

            // Create a test image file for upload tests
            _testImagePath = Path.Combine(Path.GetTempPath(), "test_photo.png");
            CreateTestImage(_testImagePath);
        }

        [TearDown]
        public void TearDown()
        {
            _driver.Quit();
            _driver.Dispose();

            if (File.Exists(_testImagePath))
            {
                File.Delete(_testImagePath);
            }
        }

        private void CreateTestImage(string path)
        {
            // Create a minimal valid PNG file (1x1 pixel)
            byte[] pngBytes = Convert.FromBase64String(
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
            File.WriteAllBytes(path, pngBytes);
        }

        /// <summary>
        /// Tests uploading a photo for a new client via file input.
        /// </summary>
        [Test]
        public void UploadClientPhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Photo Client");
            _driver.FindElement(By.Id("Address")).SendKeys("Photo City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("500");

            // Upload photo via file input - SendKeys with file path works for file inputs
            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Photo Client"));
        }

        /// <summary>
        /// Tests uploading a photo for a new employee via file input.
        /// </summary>
        [Test]
        public void UploadEmployeePhoto_ShouldSucceed()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Photo Emp");
            _driver.FindElement(By.Id("Address")).SendKeys("Photo Place");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("75000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Photo Emp"));
        }

        /// <summary>
        /// Tests replacing an existing client photo with a new one.
        /// </summary>
        [Test]
        public void ReplaceClientPhoto_ShouldSucceed()
        {
            // First create a client with a photo
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Replace Photo");
            _driver.FindElement(By.Id("Address")).SendKeys("Replace City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("600");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Now edit and replace the photo
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("Replace Photo"));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // Create a second test image
            var newImagePath = Path.Combine(Path.GetTempPath(), "test_photo_new.png");
            CreateTestImage(newImagePath);

            try
            {
                var editPhotoInput = _driver.FindElement(By.Id("Photo"));
                editPhotoInput.SendKeys(newImagePath);

                var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
                ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
                Thread.Sleep(500);

                var pageSource = _driver.PageSource;
                Assert.That(pageSource, Does.Contain("Replace Photo"));
            }
            finally
            {
                if (File.Exists(newImagePath))
                {
                    File.Delete(newImagePath);
                }
            }
        }

        /// <summary>
        /// Tests replacing an existing employee photo with a new one.
        /// </summary>
        [Test]
        public void ReplaceEmployeePhoto_ShouldSucceed()
        {
            // First create an employee with a photo
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Replace Emp Photo");
            _driver.FindElement(By.Id("Address")).SendKeys("Replace Emp City");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("65000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Now edit and replace the photo
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains("Replace Emp Photo"));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // Create a second test image
            var newImagePath = Path.Combine(Path.GetTempPath(), "test_photo_emp_new.png");
            CreateTestImage(newImagePath);

            try
            {
                var editPhotoInput = _driver.FindElement(By.Id("Photo"));
                editPhotoInput.SendKeys(newImagePath);

                var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
                ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
                Thread.Sleep(500);

                var pageSource = _driver.PageSource;
                Assert.That(pageSource, Does.Contain("Replace Emp Photo"));
            }
            finally
            {
                if (File.Exists(newImagePath))
                {
                    File.Delete(newImagePath);
                }
            }
        }

        /// <summary>
        /// Tests deleting an existing client photo using the DeletePhoto checkbox.
        /// </summary>
        [Test]
        public void DeleteClientPhoto_ShouldSucceed()
        {
            var uniqueId = Guid.NewGuid().ToString().Replace("-", "").Substring(0, 6).ToUpper()
                .Replace("0", "A").Replace("1", "B").Replace("2", "C").Replace("3", "D")
                .Replace("4", "E").Replace("5", "F").Replace("6", "G").Replace("7", "H")
                .Replace("8", "I").Replace("9", "J");
            var clientName = $"DelPhoto{uniqueId}";
            
            // First create a client with a photo
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys(clientName);
            _driver.FindElement(By.Id("Address")).SendKeys("Delete Photo City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("700");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Now edit and delete the photo
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains(clientName));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // Check the DeletePhoto checkbox
            var deletePhotoCheckbox = _driver.FindElement(By.Id("DeletePhoto"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", deletePhotoCheckbox);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(1000);

            // Navigate back to Index to verify client still exists
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain(clientName));

            // Go to edit again and verify photo is gone (no DeletePhoto checkbox should be visible)
            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            targetRow = rows.FirstOrDefault(r => r.Text.Contains(clientName));
            Assert.That(targetRow, Is.Not.Null, $"{clientName} row should exist after photo deletion");
            editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // The DeletePhoto checkbox should not be present if photo was deleted
            var deleteCheckboxes = _driver.FindElements(By.Id("DeletePhoto"));
            Assert.That(deleteCheckboxes.Count, Is.EqualTo(0), "DeletePhoto checkbox should not exist after photo deletion");
        }

        /// <summary>
        /// Tests deleting an existing employee photo using the DeletePhoto checkbox.
        /// </summary>
        [Test]
        public void DeleteEmployeePhoto_ShouldSucceed()
        {
            var uniqueId = Guid.NewGuid().ToString().Replace("-", "").Substring(0, 6).ToUpper()
                .Replace("0", "A").Replace("1", "B").Replace("2", "C").Replace("3", "D")
                .Replace("4", "E").Replace("5", "F").Replace("6", "G").Replace("7", "H")
                .Replace("8", "I").Replace("9", "J");
            var empName = $"DelPhotoEmp{uniqueId}";
            
            // First create an employee with a photo
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys(empName);
            _driver.FindElement(By.Id("Address")).SendKeys("Delete Photo Place");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("85000");

            var photoInput = _driver.FindElement(By.Id("Photo"));
            photoInput.SendKeys(_testImagePath);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Now edit and delete the photo
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var targetRow = rows.FirstOrDefault(r => r.Text.Contains(empName));
            Assert.That(targetRow, Is.Not.Null);
            var editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // Check the DeletePhoto checkbox
            var deletePhotoCheckbox = _driver.FindElement(By.Id("DeletePhoto"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", deletePhotoCheckbox);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(1000);

            // Navigate back to Index to verify employee still exists
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain(empName));

            // Go to edit again and verify photo is gone
            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            targetRow = rows.FirstOrDefault(r => r.Text.Contains(empName));
            Assert.That(targetRow, Is.Not.Null, $"{empName} row should exist after photo deletion");
            editLink = targetRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", editLink);

            // The DeletePhoto checkbox should not be present if photo was deleted
            var deleteCheckboxes = _driver.FindElements(By.Id("DeletePhoto"));
            Assert.That(deleteCheckboxes.Count, Is.EqualTo(0), "DeletePhoto checkbox should not exist after photo deletion");
        }
    }

    /// <summary>
    /// Tests for assigning and removing services to/from employees.
    /// </summary>
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

        /// <summary>
        /// Tests assigning a service to an employee during creation.
        /// </summary>
        [Test]
        public void AssignServiceToEmployee_ShouldSucceed()
        {
            // First create a service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("AssocService");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("120");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Get the service ID from the page (we need to extract it)
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("AssocService"));
            Assert.That(serviceRow, Is.Not.Null);

            // Get the service ID from the Edit link URL
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            // Now create an employee with this service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Assoc Emp");
            _driver.FindElement(By.Id("Address")).SendKeys("Assoc City");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("55000");
            _driver.FindElement(By.Id("ServiceId")).SendKeys(serviceId);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Assoc Emp"));
        }

        /// <summary>
        /// Tests removing a service from an employee by clearing the ServiceId field.
        /// </summary>
        [Test]
        public void RemoveServiceFromEmployee_ShouldSucceed()
        {
            // Create a service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("RemoveService");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("130");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("RemoveService"));
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            // Create employee with service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Name")).SendKeys("Remove Svc Emp");
            _driver.FindElement(By.Id("Address")).SendKeys("Remove City");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("60000");
            _driver.FindElement(By.Id("ServiceId")).SendKeys(serviceId);
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Edit employee to remove service
            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var empRow = rows.FirstOrDefault(r => r.Text.Contains("Remove Svc Emp"));
            var empEditLink = empRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", empEditLink);

            var serviceIdField = _driver.FindElement(By.Id("ServiceId"));
            serviceIdField.Clear();

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("Remove Svc Emp"));
        }

        /// <summary>
        /// Tests assigning a service to a client during creation.
        /// </summary>
        [Test]
        public void AssignServiceToClient_ShouldSucceed()
        {
            // First create a service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ClientAssocService");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("140");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Get the service ID from the page
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ClientAssocService"));
            Assert.That(serviceRow, Is.Not.Null);

            // Get the service ID from the Edit link URL
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            // Now create a client with this service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("Assoc Client");
            _driver.FindElement(By.Id("Address")).SendKeys("Assoc Cl ient City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("800");
            _driver.FindElement(By.Id("ServiceId")).SendKeys(serviceId);

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource2 = _driver.PageSource;
            Assert.That(pageSource2, Does.Contain("Assoc Client"));
        }

        /// <summary>
        /// Tests removing a service from a client by clearing the ServiceId field.
        /// </summary>
        [Test]
        public void RemoveServiceFromClient_ShouldSucceed()
        {
            // Create a service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Services");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Type")).SendKeys("ClientRemoveService");
            _driver.FindElement(By.Id("Rate")).Clear();
            _driver.FindElement(By.Id("Rate")).SendKeys("150");
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var serviceRow = rows.FirstOrDefault(r => r.Text.Contains("ClientRemoveService"));
            var editLink = serviceRow!.FindElement(By.LinkText("Edit")).GetAttribute("href");
            var serviceId = editLink.Split('/').Last();

            // Create client with service
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();
            _driver.FindElement(By.Id("Name")).SendKeys("Remove Svc Client");
            _driver.FindElement(By.Id("Address")).SendKeys("Remove Client City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("900");
            _driver.FindElement(By.Id("ServiceId")).SendKeys(serviceId);
            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Edit client to remove service
            rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var clientRow = rows.FirstOrDefault(r => r.Text.Contains("Remove Svc Client"));
            var clientEditLink = clientRow!.FindElement(By.LinkText("Edit"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", clientEditLink);

            var serviceIdField = _driver.FindElement(By.Id("ServiceId"));
            serviceIdField.Clear();

            var submitBtn = _driver.FindElement(By.XPath("//input[@type='submit']"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].click();", submitBtn);
            Thread.Sleep(500);

            var pageSource2 = _driver.PageSource;
            Assert.That(pageSource2, Does.Contain("Remove Svc Client"));
        }
    }

    /// <summary>
    /// Tests for automated date picker selection using JavaScript.
    /// </summary>
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

        /// <summary>
        /// Tests selecting a date of birth for a client using JavaScript.
        /// </summary>
        [Test]
        public void SelectDateOfBirth_UsingDatePicker_ForClient()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Clients");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("DatePick Client");
            _driver.FindElement(By.Id("Address")).SendKeys("DatePick City");
            _driver.FindElement(By.Id("Balance")).Clear();
            _driver.FindElement(By.Id("Balance")).SendKeys("700");

            // Automate date picker selection using JavaScript
            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));

            // Method 1: Direct value setting via JavaScript (works for HTML5 date inputs)
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1995-12-25';", dobInput);

            // Trigger change event to ensure the value is registered
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].dispatchEvent(new Event('change'));", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            // Verify in Details
            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var clientRow = rows.FirstOrDefault(r => r.Text.Contains("DatePick Client"));
            Assert.That(clientRow, Is.Not.Null);
            var detailsLink = clientRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DatePick Client"));
            // Date format may vary, check for year at minimum
            Assert.That(pageSource, Does.Contain("1995"));
        }

        /// <summary>
        /// Tests selecting a date of birth for an employee using JavaScript.
        /// </summary>
        [Test]
        public void SelectDateOfBirth_UsingDatePicker_ForEmployee()
        {
            _driver.Navigate().GoToUrl($"{_baseUrl}/Employees");
            _driver.FindElement(By.LinkText("Create New")).Click();

            _driver.FindElement(By.Id("Name")).SendKeys("DatePick Emp");
            _driver.FindElement(By.Id("Address")).SendKeys("DatePick Place");
            _driver.FindElement(By.Id("Salary")).Clear();
            _driver.FindElement(By.Id("Salary")).SendKeys("80000");

            var dobInput = _driver.FindElement(By.Id("DateOfBirth"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].value = '1988-07-04';", dobInput);
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].dispatchEvent(new Event('change'));", dobInput);

            _driver.FindElement(By.XPath("//input[@type='submit']")).Click();

            var rows = _driver.FindElements(By.CssSelector("table.table tbody tr"));
            var empRow = rows.FirstOrDefault(r => r.Text.Contains("DatePick Emp"));
            Assert.That(empRow, Is.Not.Null);
            var detailsLink = empRow!.FindElement(By.LinkText("Details"));
            ((IJavaScriptExecutor)_driver).ExecuteScript("arguments[0].scrollIntoView(true); arguments[0].click();", detailsLink);

            var pageSource = _driver.PageSource;
            Assert.That(pageSource, Does.Contain("DatePick Emp"));
            Assert.That(pageSource, Does.Contain("1988"));
        }
    }
}