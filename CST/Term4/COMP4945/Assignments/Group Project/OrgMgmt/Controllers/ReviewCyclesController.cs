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
    public class ReviewCyclesController : Controller
    {
        private readonly OrgDbContext _context;

        public ReviewCyclesController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: ReviewCycles
        public async Task<IActionResult> Index()
        {
            return View(await _context.ReviewCycles.ToListAsync());
        }

        // GET: ReviewCycles/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewCycle = await _context.ReviewCycles
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewCycle == null)
            {
                return NotFound();
            }

            return View(reviewCycle);
        }

        // GET: ReviewCycles/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: ReviewCycles/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Name,StartDate,EndDate,Description,IsActive")] ReviewCycle reviewCycle)
        {
            if (ModelState.IsValid)
            {
                reviewCycle.Id = Guid.NewGuid();
                _context.Add(reviewCycle);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(reviewCycle);
        }

        // GET: ReviewCycles/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewCycle = await _context.ReviewCycles.FindAsync(id);
            if (reviewCycle == null)
            {
                return NotFound();
            }
            return View(reviewCycle);
        }

        // POST: ReviewCycles/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,Name,StartDate,EndDate,Description,IsActive")] ReviewCycle reviewCycle)
        {
            if (id != reviewCycle.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(reviewCycle);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ReviewCycleExists(reviewCycle.Id))
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
            return View(reviewCycle);
        }

        // GET: ReviewCycles/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewCycle = await _context.ReviewCycles
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewCycle == null)
            {
                return NotFound();
            }

            return View(reviewCycle);
        }

        // POST: ReviewCycles/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var reviewCycle = await _context.ReviewCycles.FindAsync(id);
            if (reviewCycle != null)
            {
                _context.ReviewCycles.Remove(reviewCycle);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ReviewCycleExists(Guid id)
        {
            return _context.ReviewCycles.Any(e => e.Id == id);
        }
    }
}
