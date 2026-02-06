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
    public class ReviewQuestionsController : Controller
    {
        private readonly OrgDbContext _context;

        public ReviewQuestionsController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: ReviewQuestions
        public async Task<IActionResult> Index()
        {
            var orgDbContext = _context.ReviewQuestions.Include(r => r.ReviewCycle);
            return View(await orgDbContext.ToListAsync());
        }

        // GET: ReviewQuestions/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewQuestion = await _context.ReviewQuestions
                .Include(r => r.ReviewCycle)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewQuestion == null)
            {
                return NotFound();
            }

            return View(reviewQuestion);
        }

        // GET: ReviewQuestions/Create
        public IActionResult Create()
        {
            ViewData["ReviewCycleId"] = new SelectList(_context.ReviewCycles, "Id", "Name");
            return View();
        }

        // POST: ReviewQuestions/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,QuestionText,Category,DisplayOrder,IsRequired,ReviewCycleId")] ReviewQuestion reviewQuestion)
        {
            if (ModelState.IsValid)
            {
                reviewQuestion.Id = Guid.NewGuid();
                _context.Add(reviewQuestion);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["ReviewCycleId"] = new SelectList(_context.ReviewCycles, "Id", "Name", reviewQuestion.ReviewCycleId);
            return View(reviewQuestion);
        }

        // GET: ReviewQuestions/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewQuestion = await _context.ReviewQuestions.FindAsync(id);
            if (reviewQuestion == null)
            {
                return NotFound();
            }
            ViewData["ReviewCycleId"] = new SelectList(_context.ReviewCycles, "Id", "Name", reviewQuestion.ReviewCycleId);
            return View(reviewQuestion);
        }

        // POST: ReviewQuestions/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,QuestionText,Category,DisplayOrder,IsRequired,ReviewCycleId")] ReviewQuestion reviewQuestion)
        {
            if (id != reviewQuestion.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(reviewQuestion);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ReviewQuestionExists(reviewQuestion.Id))
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
            ViewData["ReviewCycleId"] = new SelectList(_context.ReviewCycles, "Id", "Name", reviewQuestion.ReviewCycleId);
            return View(reviewQuestion);
        }

        // GET: ReviewQuestions/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewQuestion = await _context.ReviewQuestions
                .Include(r => r.ReviewCycle)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewQuestion == null)
            {
                return NotFound();
            }

            return View(reviewQuestion);
        }

        // POST: ReviewQuestions/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var reviewQuestion = await _context.ReviewQuestions.FindAsync(id);
            if (reviewQuestion != null)
            {
                _context.ReviewQuestions.Remove(reviewQuestion);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ReviewQuestionExists(Guid id)
        {
            return _context.ReviewQuestions.Any(e => e.Id == id);
        }
    }
}
