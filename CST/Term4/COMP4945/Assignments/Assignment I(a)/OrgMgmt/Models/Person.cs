using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models
{
    [Table("People")]
    public class Person
    {
        [Key] public Guid Id { get; set; } = Guid.NewGuid();

        [Required]
        [MaxLength(20)]
        [RegularExpression(@"^[^\d]+$", ErrorMessage = "Name cannot contain digits.")]
        public string Name { get; set; } = string.Empty;

        public string? Address { get; set; }

        [DataType(DataType.Date)] public DateTime? DateOfBirth { get; set; }
        public byte[]? Photo { get; set; }
    }
}