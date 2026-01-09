using System.Collections.Generic;

namespace OrgMgmt.Models
{
    public class Client : Person
    {
        public decimal Balance { get; set; }
        public ICollection<ClientService> ClientServices { get; set; } = new List<ClientService>();
    }
}