using System.ComponentModel.DataAnnotations;

namespace OrgMgmt.Models;

public enum RecurrenceType
{
    Daily,
    Weekly,
    Monthly,
    Yearly
}

public class Shift
{
    [Key] public Guid Id { get; set; } = Guid.NewGuid();

    [Required] [MaxLength(50)] public string Name { get; init; } = string.Empty;

    [Required] public TimeSpan StartTime { get; init; }

    [Required] public TimeSpan EndTime { get; init; }

    [Required] public RecurrenceType RecurrenceType { get; init; }

    [MaxLength(50)] public string? RecurrencePattern { get; init; }

    public ICollection<ShiftAssignment> ShiftAssignments { get; init; } = new List<ShiftAssignment>();
}