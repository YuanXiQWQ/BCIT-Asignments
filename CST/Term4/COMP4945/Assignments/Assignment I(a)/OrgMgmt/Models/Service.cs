namespace OrgMgmt.Models;

public class Service
{
    public Service()
    {
        Clients = new HashSet<Client>();
        Employees = new HashSet<Employee>();
    }

    public int ID { get; set; }
    public string Type { get; set; } = string.Empty;
    public decimal Rate { get; set; }
    public virtual ICollection<Client> Clients { get; set; }
    public virtual ICollection<Employee> Employees { get; set; }
}