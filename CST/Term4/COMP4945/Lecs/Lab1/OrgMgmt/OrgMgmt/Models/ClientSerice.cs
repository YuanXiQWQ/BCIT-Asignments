using System;

namespace OrgMgmt.Models;

public class ClientService
{
    public Guid ClientId { get; set; }
    public Client? Client { get; set; }

    public Guid ServiceId { get; set; }
    public Service? Service { get; set; }
}