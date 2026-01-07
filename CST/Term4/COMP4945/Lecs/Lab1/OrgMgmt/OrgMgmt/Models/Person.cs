using System;

namespace OrgMgmt.Models
{
    public abstract class Person
    {
        public Guid ID { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public DateTime DateOfBirth { get; set; }
        public byte[] Photo { get; set; }
    }
}