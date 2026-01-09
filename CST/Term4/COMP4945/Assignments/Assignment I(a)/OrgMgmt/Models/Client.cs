using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models
{
    [Table("Clients")]
    public class Client : Person
    {
        public Client()
        {
            Services = new HashSet<Service>();
        }
        public decimal Balance { get; set; }
        public virtual ICollection<Service> Services { get; set; }
    }
}