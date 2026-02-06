using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

/// <summary>
/// Draft ←→ Submitted → Finalized
/// </summary>
public class PerformanceReview
{
    [Key] public Guid Id { get; set; } = Guid.NewGuid();

    public Guid EmployeeId { get; init; }

    [ForeignKey(nameof(EmployeeId))] public Employee? Employee { get; init; }

    public Guid? ReviewerId { get; init; }

    [ForeignKey(nameof(ReviewerId))] public Employee? Reviewer { get; init; }

    public ReviewerType ReviewerType { get; init; } = ReviewerType.Manager;

    public Guid ReviewCycleId { get; init; }

    [ForeignKey(nameof(ReviewCycleId))] public ReviewCycle? ReviewCycle { get; init; }

    public ReviewStatus Status { get; init; } = ReviewStatus.Draft;

    [DataType(DataType.Date)] public DateTime CreatedDate { get; init; } = DateTime.UtcNow;

    [DataType(DataType.Date)] public DateTime? SubmittedDate { get; init; }

    [DataType(DataType.Date)] public DateTime? FinalizedDate { get; init; }

    [MaxLength(2000)] public string? OverallComments { get; init; }

    [Range(1, 5)] public int? OverallRating { get; init; }

    public bool IsPublished { get; init; } = false;

    [DataType(DataType.Date)] public DateTime? PublishedDate { get; init; }

    public ICollection<ReviewResponse> Responses { get; init; } = new List<ReviewResponse>();
}