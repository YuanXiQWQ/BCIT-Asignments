using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using OrgMgmt;
using OrgMgmt.Models;

namespace OrgMgmt.Controllers
{
    public class ClientServicesController : Controller
    {
        private readonly OrgDbContext _context;

        public ClientServicesController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: ClientServices
        public async Task<IActionResult> Index()
        {
            var orgDbContext = _context.ClientServices.Include(c => c.Client).Include(c => c.Service);
            return View(await orgDbContext.ToListAsync());
        }

        // GET: ClientServices/Details/5
        public async Task<IActionResult> Details(Guid? clientId, Guid? serviceId)
        {
            if (clientId == null || serviceId == null)
            {
                return NotFound();
            }

            var clientService = await _context.ClientServices
                .Include(c => c.Client)
                .Include(c => c.Service)
                .FirstOrDefaultAsync(m => m.ClientId == clientId && m.ServiceId == serviceId);

            if (clientService == null)
            {
                return NotFound();
            }

            return View(clientService);
        }

        // GET: ClientServices/Create
        public IActionResult Create()
        {
            ViewData["ClientId"] = new SelectList(_context.Clients, "ID", "Name");
            ViewData["ServiceId"] = new SelectList(_context.Services, "ID", "Type");
            return View();
        }

        // POST: ClientServices/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ClientId,ServiceId")] ClientService clientService)
        {
            if (ModelState.IsValid)
            {
                var exists = await _context.ClientServices.AnyAsync(link =>
                    link.ClientId == clientService.ClientId &&
                    link.ServiceId == clientService.ServiceId);

                if (!exists)
                {
                    _context.Add(clientService);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }

                ModelState.AddModelError(string.Empty, "This client is already registered for the selected service.");
            }

            ViewData["ClientId"] = new SelectList(_context.Clients, "ID", "Name", clientService.ClientId);
            ViewData["ServiceId"] = new SelectList(_context.Services, "ID", "Type", clientService.ServiceId);
            return View(clientService);
        }

        // GET: ClientServices/Edit/5
        public async Task<IActionResult> Edit(Guid? clientId, Guid? serviceId)
        {
            if (clientId == null || serviceId == null)
            {
                return NotFound();
            }

            var clientService = await _context.ClientServices
                .AsNoTracking()
                .FirstOrDefaultAsync(m => m.ClientId == clientId && m.ServiceId == serviceId);

            if (clientService == null)
            {
                return NotFound();
            }
            ViewData["ClientId"] = new SelectList(_context.Clients, "ID", "Name", clientService.ClientId);
            ViewData["ServiceId"] = new SelectList(_context.Services, "ID", "Type", clientService.ServiceId);
            return View(clientService);
        }

        // POST: ClientServices/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid clientId, Guid serviceId,
            [Bind("ClientId,ServiceId")] ClientService clientService)
        {
            if (!ModelState.IsValid)
            {
                ViewData["ClientId"] = new SelectList(_context.Clients, "ID", "Name", clientService.ClientId);
                ViewData["ServiceId"] = new SelectList(_context.Services, "ID", "Type", clientService.ServiceId);
                return View(clientService);
            }

            var oldLink = await _context.ClientServices
                .FirstOrDefaultAsync(m => m.ClientId == clientId && m.ServiceId == serviceId);

            if (oldLink == null)
            {
                return NotFound();
            }

            var newClientId = clientService.ClientId;
            var newServiceId = clientService.ServiceId;

            if (newClientId == clientId && newServiceId == serviceId)
            {
                return RedirectToAction(nameof(Index));
            }

            var duplicate = await _context.ClientServices.AnyAsync(m =>
                m.ClientId == newClientId && m.ServiceId == newServiceId);

            if (duplicate)
            {
                ModelState.AddModelError(string.Empty, "The selected client-service pair already exists.");
                ViewData["ClientId"] = new SelectList(_context.Clients, "ID", "Name", clientService.ClientId);
                ViewData["ServiceId"] = new SelectList(_context.Services, "ID", "Type", clientService.ServiceId);
                return View(clientService);
            }

            _context.ClientServices.Remove(oldLink);
            _context.ClientServices.Add(new ClientService
            {
                ClientId = newClientId,
                ServiceId = newServiceId
            });

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        // GET: ClientServices/Delete/5
        public async Task<IActionResult> Delete(Guid? clientId, Guid? serviceId)
        {
            if (clientId == null || serviceId == null)
            {
                return NotFound();
            }

            var clientService = await _context.ClientServices
                .Include(c => c.Client)
                .Include(c => c.Service)
                .FirstOrDefaultAsync(m => m.ClientId == clientId && m.ServiceId == serviceId);

            if (clientService == null)
            {
                return NotFound();
            }

            return View(clientService);
        }

        // POST: ClientServices/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid clientId, Guid serviceId)
        {
            var clientService = await _context.ClientServices
                .FirstOrDefaultAsync(m => m.ClientId == clientId && m.ServiceId == serviceId);

            if (clientService != null)
            {
                _context.ClientServices.Remove(clientService);
                await _context.SaveChangesAsync();
            }

            return RedirectToAction(nameof(Index));
        }
    }
}