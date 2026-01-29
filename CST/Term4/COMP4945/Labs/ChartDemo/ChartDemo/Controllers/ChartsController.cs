using ChartDemo.Models;
using Microsoft.AspNetCore.Mvc;

namespace ChartDemo.Controllers
{
    public class ChartsController : Controller
    {
        public IActionResult Index(string type = "Line")
        {
            List<MonthlyCategorySalesViewModel> monthlySales =
            [
                new() { Month = "Jan", Electronics = 40, Clothing = 55, Home = 30 },
                new() { Month = "Feb", Electronics = 45, Clothing = 60, Home = 28 },
                new() { Month = "Mar", Electronics = 50, Clothing = 58, Home = 35 },
                new() { Month = "Apr", Electronics = 55, Clothing = 65, Home = 32 }
            ];

            return type switch
            {
                "Line" => View("Line", monthlySales),
                "Pie" => View("Pie", monthlySales[0]),
                "Bar" => View("Bar", monthlySales),
                "StackedBar" => View("StackedBar", monthlySales),
                _ => View("Line", monthlySales)
            };
        }
    }
}