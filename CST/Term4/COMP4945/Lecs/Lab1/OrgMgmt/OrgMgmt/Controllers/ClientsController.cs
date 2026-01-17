using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models;

namespace OrgMgmt.Controllers
{
    public class ClientsController : Controller
    {
        private readonly OrgDbContext _context;

        public ClientsController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: Clients
        public async Task<IActionResult> Index()
        {
            var clients = await _context.Clients
                .Include(c => c.Services)
                .ToListAsync();

            var clientsPerCity = clients
                .GroupBy(c => c.Address)
                .Select(g => new { City = g.Key, Count = g.Count() })
                .OrderBy(x => x.City)
                .ToList();
            ViewBag.ClientsPerCity = clientsPerCity;

            ViewBag.ClientsWithoutServices = clients
                .Where(c => c.Services.Count == 0)
                .ToList();

            return View(clients);
        }

        // GET: Clients/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var client = await _context.Clients
                .FirstOrDefaultAsync(m => m.Id == id);
            if (client == null)
            {
                return NotFound();
            }

            return View(client);
        }

        // GET: Clients/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Clients/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Balance,Id,Name,Address,DateOfBirth,ServiceId")] Client client, IFormFile? PhotoFile)
        {
            if (ModelState.IsValid)
            {
                client.Id = Guid.NewGuid();
                
                if (PhotoFile != null && PhotoFile.Length > 0)
                {
                    using var ms = new MemoryStream();
                    await PhotoFile.CopyToAsync(ms);
                    client.Photo = ms.ToArray();
                }
                
                _context.Add(client);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }

            return View(client);
        }

        // GET: Clients/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var client = await _context.Clients.FindAsync(id);
            if (client == null)
            {
                return NotFound();
            }

            return View(client);
        }

        // POST: Clients/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Balance,Id,Name,Address,DateOfBirth,ServiceId")] Client client, IFormFile? PhotoFile, bool DeletePhoto = false)
        {
            if (id != client.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    var existingClient = await _context.Clients.AsNoTracking().FirstOrDefaultAsync(c => c.Id == id);
                    
                    if (DeletePhoto)
                    {
                        client.Photo = null;
                    }
                    else if (PhotoFile != null && PhotoFile.Length > 0)
                    {
                        using var ms = new MemoryStream();
                        await PhotoFile.CopyToAsync(ms);
                        client.Photo = ms.ToArray();
                    }
                    else
                    {
                        client.Photo = existingClient?.Photo;
                    }
                    
                    _context.Update(client);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ClientExists(client.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }

                return RedirectToAction(nameof(Index));
            }

            return View(client);
        }

        // GET: Clients/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var client = await _context.Clients
                .FirstOrDefaultAsync(m => m.Id == id);
            if (client == null)
            {
                return NotFound();
            }

            return View(client);
        }

        // POST: Clients/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var client = await _context.Clients.FindAsync(id);
            if (client != null)
            {
                _context.Clients.Remove(client);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ClientExists(Guid id)
        {
            return _context.Clients.Any(e => e.Id == id);
        }
    }
}