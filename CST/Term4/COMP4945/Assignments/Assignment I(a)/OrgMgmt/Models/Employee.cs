using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models;

[Table("Employees")]
public class Employee : Person
{
    public Service? MyService { get; set; }
    public int Salary { get; set; }
    public int? ServiceId { get; set; }
}