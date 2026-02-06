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
    public class ReviewResponsesController : Controller
    {
        private readonly OrgDbContext _context;

        public ReviewResponsesController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: ReviewResponses
        public async Task<IActionResult> Index()
        {
            var orgDbContext = _context.ReviewResponses.Include(r => r.PerformanceReview).Include(r => r.ReviewQuestion);
            return View(await orgDbContext.ToListAsync());
        }

        // GET: ReviewResponses/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewResponse = await _context.ReviewResponses
                .Include(r => r.PerformanceReview)
                .Include(r => r.ReviewQuestion)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewResponse == null)
            {
                return NotFound();
            }

            return View(reviewResponse);
        }

        // GET: ReviewResponses/Create
        public IActionResult Create()
        {
            ViewData["PerformanceReviewId"] = new SelectList(_context.PerformanceReviews, "Id", "Id");
            ViewData["ReviewQuestionId"] = new SelectList(_context.ReviewQuestions, "Id", "QuestionText");
            return View();
        }

        // POST: ReviewResponses/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,PerformanceReviewId,ReviewQuestionId,ResponseText,Rating")] ReviewResponse reviewResponse)
        {
            if (ModelState.IsValid)
            {
                reviewResponse.Id = Guid.NewGuid();
                _context.Add(reviewResponse);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["PerformanceReviewId"] = new SelectList(_context.PerformanceReviews, "Id", "Id", reviewResponse.PerformanceReviewId);
            ViewData["ReviewQuestionId"] = new SelectList(_context.ReviewQuestions, "Id", "QuestionText", reviewResponse.ReviewQuestionId);
            return View(reviewResponse);
        }

        // GET: ReviewResponses/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewResponse = await _context.ReviewResponses.FindAsync(id);
            if (reviewResponse == null)
            {
                return NotFound();
            }
            ViewData["PerformanceReviewId"] = new SelectList(_context.PerformanceReviews, "Id", "Id", reviewResponse.PerformanceReviewId);
            ViewData["ReviewQuestionId"] = new SelectList(_context.ReviewQuestions, "Id", "QuestionText", reviewResponse.ReviewQuestionId);
            return View(reviewResponse);
        }

        // POST: ReviewResponses/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,PerformanceReviewId,ReviewQuestionId,ResponseText,Rating")] ReviewResponse reviewResponse)
        {
            if (id != reviewResponse.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(reviewResponse);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ReviewResponseExists(reviewResponse.Id))
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
            ViewData["PerformanceReviewId"] = new SelectList(_context.PerformanceReviews, "Id", "Id", reviewResponse.PerformanceReviewId);
            ViewData["ReviewQuestionId"] = new SelectList(_context.ReviewQuestions, "Id", "QuestionText", reviewResponse.ReviewQuestionId);
            return View(reviewResponse);
        }

        // GET: ReviewResponses/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reviewResponse = await _context.ReviewResponses
                .Include(r => r.PerformanceReview)
                .Include(r => r.ReviewQuestion)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (reviewResponse == null)
            {
                return NotFound();
            }

            return View(reviewResponse);
        }

        // POST: ReviewResponses/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var reviewResponse = await _context.ReviewResponses.FindAsync(id);
            if (reviewResponse != null)
            {
                _context.ReviewResponses.Remove(reviewResponse);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ReviewResponseExists(Guid id)
        {
            return _context.ReviewResponses.Any(e => e.Id == id);
        }
    }
}
