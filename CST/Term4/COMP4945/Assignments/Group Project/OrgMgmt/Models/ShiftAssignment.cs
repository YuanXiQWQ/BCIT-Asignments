using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

public class ShiftAssignment
{
    [Key] public Guid Id { get; set; } = Guid.NewGuid();

    [Required] public Guid ShiftId { get; init; }

    [ForeignKey(nameof(ShiftId))] public Shift? Shift { get; init; }

    [Required] public Guid EmployeeId { get; init; }

    [ForeignKey(nameof(EmployeeId))] public Employee? Employee { get; init; }

    public int WeekNumber { get; init; } = 1;

    [DataType(DataType.Date)] public DateTime? StartDate { get; init; }

    [DataType(DataType.Date)] public DateTime? EndDate { get; init; }
}