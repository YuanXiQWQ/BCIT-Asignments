using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

public class Employee : Person
{
    [ForeignKey(nameof(ServiceId))]
    public Service? Service { get; set; }
    public decimal Salary { get; set; }
    public Guid? ServiceId { get; set; }
    public ICollection<ShiftAssignment> ShiftAssignments { get; set; } = new List<ShiftAssignment>();
}