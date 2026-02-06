using System.ComponentModel.DataAnnotations;

namespace OrgMgmt.Models;

/// <summary>
/// Represents a review cycle/period (e.g., Q1 2026, Annual 2026)
/// </summary>
public class ReviewCycle
{
    [Key]
    public Guid Id { get; set; } = Guid.NewGuid();

    [Required]
    [MaxLength(100)]
    public string Name { get; set; } = string.Empty;

    [DataType(DataType.Date)]
    public DateTime StartDate { get; set; }

    [DataType(DataType.Date)]
    public DateTime EndDate { get; set; }

    [MaxLength(500)]
    public string? Description { get; set; }

    public bool IsActive { get; set; } = true;

    // Navigation property
    public ICollection<ReviewQuestion> Questions { get; set; } = new List<ReviewQuestion>();
    public ICollection<PerformanceReview> PerformanceReviews { get; set; } = new List<PerformanceReview>();
}
