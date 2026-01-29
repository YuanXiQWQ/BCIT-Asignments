using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using ChartDemo.Models;

namespace ChartDemo.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Pie()
        {
            return RedirectToAction("Index", "Charts", new { type = "Pie" });
        }

        public IActionResult Bar()
        {
            return RedirectToAction("Index", "Charts", new { type = "Bar" });
        }

        public IActionResult Line()
        {
            return RedirectToAction("Index", "Charts", new { type = "Line" });
        }

        public IActionResult StackedBar()
        {
            return RedirectToAction("Index", "Charts", new { type = "StackedBar" });
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}