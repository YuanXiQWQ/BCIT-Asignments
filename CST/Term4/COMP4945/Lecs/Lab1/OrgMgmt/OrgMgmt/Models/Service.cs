using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace OrgMgmt.Models;

public class Service
{
    [Key] public Guid ID { get; set; }

    [Required] public string Type { get; set; } = string.Empty;

    [Range(0, double.MaxValue, ErrorMessage = "Rate cannot be negative.")]
    public decimal Rate { get; set; }

    public Guid EmployeeId { get; set; }
    public Employee? Employee { get; set; }

    public ICollection<ClientService> ClientServices { get; set; } = new List<ClientService>();
}