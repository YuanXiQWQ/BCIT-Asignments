using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

public class ReviewResponse
{
    [Key] public Guid Id { get; set; } = Guid.NewGuid();

    public Guid PerformanceReviewId { get; init; }

    [ForeignKey(nameof(PerformanceReviewId))]
    public PerformanceReview? PerformanceReview { get; init; }

    public Guid ReviewQuestionId { get; init; }

    [ForeignKey(nameof(ReviewQuestionId))] public ReviewQuestion? ReviewQuestion { get; init; }

    [MaxLength(2000)] public string? ResponseText { get; init; }

    [Range(1, 5)] public int? Rating { get; init; }
}