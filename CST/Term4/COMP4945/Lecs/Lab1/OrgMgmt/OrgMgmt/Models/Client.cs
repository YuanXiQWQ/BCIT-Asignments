using System.ComponentModel.DataAnnotations.Schema;

namespace OrgMgmt.Models
{
    public class Client : Person
    {
        public Client()
        {
            Services = new HashSet<Service>();
        }
        public decimal Balance { get; set; }
        
        [ForeignKey(nameof(ServiceId))]
        public Service? Service { get; set; }
        public Guid? ServiceId { get; set; }
        
        public virtual ICollection<Service> Services { get; set; }
    }
}