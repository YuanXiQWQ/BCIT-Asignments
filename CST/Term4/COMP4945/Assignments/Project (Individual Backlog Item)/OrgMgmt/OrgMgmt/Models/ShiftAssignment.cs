using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

public class ShiftAssignment
{
    [Key] public Guid Id { get; set; } = Guid.NewGuid();

    [Required] public Guid ShiftId { get; set; }

    [ForeignKey(nameof(ShiftId))] public Shift? Shift { get; set; }

    [Required] public Guid EmployeeId { get; set; }

    [ForeignKey(nameof(EmployeeId))] public Employee? Employee { get; set; }

    public int WeekNumber { get; set; } = 1;

    public int DayOfWeek { get; set; }

    [DataType(DataType.Date)] public DateTime? StartDate { get; set; }

    [DataType(DataType.Date)] public DateTime? EndDate { get; set; }
}