namespace OrgMgmt.Models
{
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