namespace OrgMgmt.Models;

public class Employee : Person
{
    public Service? MyService { get; set; }
    public decimal Salary { get; set; }
    public Guid? ServiceId { get; set; }
}