using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models.ViewModels;

namespace OrgMgmt.Controllers
{
    public class QueriesController : Controller
    {
        private readonly OrgDbContext _context;

        public QueriesController(OrgDbContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {
            var clientsPerCity = await _context.Clients
                .GroupBy(client => client.Address ?? "(Unknown)")
                .Select(group => new CityClientCountViewModel
                {
                    City = group.Key,
                    ClientCount = group.Count()
                })
                .OrderBy(result => result.City)
                .ToListAsync();

            var clientsWithoutServices = await _context.Clients
                .Where(client => !client.ClientServices.Any())
                .Select(client => new ClientWithoutServiceViewModel
                {
                    ID = client.ID,
                    Name = client.Name,
                    Address = client.Address,
                    Balance = client.Balance
                })
                .ToListAsync();

            var viewModel = new QueryResultsViewModel
            {
                ClientsPerCity = clientsPerCity,
                ClientsWithoutServices = clientsWithoutServices
            };

            return View(viewModel);
        }
    }
}