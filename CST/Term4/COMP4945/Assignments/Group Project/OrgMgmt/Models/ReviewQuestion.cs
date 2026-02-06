using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

public class ReviewQuestion
{
    [Key]
    public Guid Id { get; set; } = Guid.NewGuid();

    [Required]
    [MaxLength(500)]
    public string QuestionText { get; init; } = string.Empty;

    [MaxLength(200)]
    public string? Category { get; init; }

    public int DisplayOrder { get; init; }

    public bool IsRequired { get; init; } = true;
    
    public Guid ReviewCycleId { get; init; }

    [ForeignKey(nameof(ReviewCycleId))]
    public ReviewCycle? ReviewCycle { get; init; }
    
    public ICollection<ReviewResponse> Responses { get; init; } = new List<ReviewResponse>();
}
