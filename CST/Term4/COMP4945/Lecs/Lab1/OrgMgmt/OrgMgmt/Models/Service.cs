using System.ComponentModel.DataAnnotations;

namespace OrgMgmt.Models;

public class Service
{
    public Service()
    {
        Clients = new HashSet<Client>();
        Employees = new HashSet<Employee>();
    }

    public Guid Id { get; set; }
    [MaxLength(20)] public string Type { get; set; } = string.Empty;

    [Range(typeof(decimal), "0", "99999")] public decimal Rate { get; set; }

    public virtual ICollection<Client> Clients { get; set; }
    public virtual ICollection<Employee> Employees { get; set; }
}