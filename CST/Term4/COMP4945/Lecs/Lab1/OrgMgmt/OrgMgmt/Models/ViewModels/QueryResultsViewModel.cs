using System;
using System.Collections.Generic;

namespace OrgMgmt.Models.ViewModels
{
    public class CityClientCountViewModel
    {
        public string City { get; set; } = string.Empty;
        public int ClientCount { get; set; }
    }

    public class ClientWithoutServiceViewModel
    {
        public Guid ID { get; set; }
        public string Name { get; set; } = string.Empty;
        public string? Address { get; set; }
        public decimal Balance { get; set; }
    }

    public class QueryResultsViewModel
    {
        public List<CityClientCountViewModel> ClientsPerCity { get; set; } = new();
        public List<ClientWithoutServiceViewModel> ClientsWithoutServices { get; set; } = new();
    }
}