using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models;

namespace OrgMgmt.Controllers
{
    public class ShiftAssignmentsController : Controller
    {
        private readonly OrgDbContext _context;

        public ShiftAssignmentsController(OrgDbContext context)
        {
            _context = context;
        }

        // GET: ShiftAssignments
        public async Task<IActionResult> Index()
        {
            var orgDbContext = _context.ShiftAssignments.Include(s => s.Employee).Include(s => s.Shift);
            return View(await orgDbContext.ToListAsync());
        }

        // GET: ShiftAssignments/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var shiftAssignment = await _context.ShiftAssignments
                .Include(s => s.Employee)
                .Include(s => s.Shift)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (shiftAssignment == null)
            {
                return NotFound();
            }

            return View(shiftAssignment);
        }

        // GET: ShiftAssignments/Create
        public IActionResult Create()
        {
            ViewData["EmployeeId"] = new SelectList(_context.Employees, "Id", "Name");
            ViewData["ShiftId"] = new SelectList(_context.Shifts, "Id", "Name");
            return View();
        }

        // POST: ShiftAssignments/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,ShiftId,EmployeeId,WeekNumber,StartDate,EndDate")] ShiftAssignment shiftAssignment)
        {
            if (ModelState.IsValid)
            {
                shiftAssignment.Id = Guid.NewGuid();
                _context.Add(shiftAssignment);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["EmployeeId"] = new SelectList(_context.Employees, "Id", "Name", shiftAssignment.EmployeeId);
            ViewData["ShiftId"] = new SelectList(_context.Shifts, "Id", "Name", shiftAssignment.ShiftId);
            return View(shiftAssignment);
        }

        // GET: ShiftAssignments/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var shiftAssignment = await _context.ShiftAssignments.FindAsync(id);
            if (shiftAssignment == null)
            {
                return NotFound();
            }
            ViewData["EmployeeId"] = new SelectList(_context.Employees, "Id", "Name", shiftAssignment.EmployeeId);
            ViewData["ShiftId"] = new SelectList(_context.Shifts, "Id", "Name", shiftAssignment.ShiftId);
            return View(shiftAssignment);
        }

        // POST: ShiftAssignments/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,ShiftId,EmployeeId,WeekNumber,StartDate,EndDate")] ShiftAssignment shiftAssignment)
        {
            if (id != shiftAssignment.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(shiftAssignment);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ShiftAssignmentExists(shiftAssignment.Id))
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
            ViewData["EmployeeId"] = new SelectList(_context.Employees, "Id", "Name", shiftAssignment.EmployeeId);
            ViewData["ShiftId"] = new SelectList(_context.Shifts, "Id", "Name", shiftAssignment.ShiftId);
            return View(shiftAssignment);
        }

        // GET: ShiftAssignments/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var shiftAssignment = await _context.ShiftAssignments
                .Include(s => s.Employee)
                .Include(s => s.Shift)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (shiftAssignment == null)
            {
                return NotFound();
            }

            return View(shiftAssignment);
        }

        // POST: ShiftAssignments/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var shiftAssignment = await _context.ShiftAssignments.FindAsync(id);
            if (shiftAssignment != null)
            {
                _context.ShiftAssignments.Remove(shiftAssignment);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        
        // GET: ShiftAssignments/Schedule
        public async Task<IActionResult> Schedule(int weekNumber = 1)
        {
            ViewData["Employees"] = await _context.Employees.ToListAsync();
            ViewData["Shifts"] = await _context.Shifts.ToListAsync();
            ViewData["WeekNumber"] = weekNumber;
            
            var assignments = await _context.ShiftAssignments
                .Include(s => s.Employee)
                .Include(s => s.Shift)
                .Where(s => s.WeekNumber == weekNumber)
                .ToListAsync();
            
            return View(assignments);
        }

        // POST: ShiftAssignments/Schedule
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Schedule(List<Guid> employeeIds, List<Guid> shiftIds, int weekNumber)
        {
            // Remove existing assignments for selected employees in this week
            var existingAssignments = await _context.ShiftAssignments
                .Where(s => employeeIds.Contains(s.EmployeeId) && s.WeekNumber == weekNumber)
                .ToListAsync();
            _context.ShiftAssignments.RemoveRange(existingAssignments);

            // Create new assignments
            foreach (var employeeId in employeeIds)
            {
                foreach (var shiftId in shiftIds)
                {
                    var assignment = new ShiftAssignment
                    {
                        EmployeeId = employeeId,
                        ShiftId = shiftId,
                        WeekNumber = weekNumber
                    };
                    _context.ShiftAssignments.Add(assignment);
                }
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Schedule), new { weekNumber });
        }

        // POST: ShiftAssignments/UpdateGrid
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> UpdateGrid(Dictionary<string, bool> assignments, int weekNumber)
        {
            foreach (var kvp in assignments)
            {
                var parts = kvp.Key.Split('_');
                if (parts.Length != 2) continue;
                
                var employeeId = Guid.Parse(parts[0]);
                var shiftId = Guid.Parse(parts[1]);
                
                var existing = await _context.ShiftAssignments
                    .FirstOrDefaultAsync(s => s.EmployeeId == employeeId && s.ShiftId == shiftId && s.WeekNumber == weekNumber);
                
                if (kvp.Value && existing == null)
                {
                    _context.ShiftAssignments.Add(new ShiftAssignment
                    {
                        EmployeeId = employeeId,
                        ShiftId = shiftId,
                        WeekNumber = weekNumber
                    });
                }
                else if (!kvp.Value && existing != null)
                {
                    _context.ShiftAssignments.Remove(existing);
                }
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Schedule), new { weekNumber });
        }

        private bool ShiftAssignmentExists(Guid id)
        {
            return _context.ShiftAssignments.Any(e => e.Id == id);
        }
    }
}
