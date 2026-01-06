using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models;

namespace OrgMgmt
{
    public class OrgDbContext : DbContext
    {
        public OrgDbContext(DbContextOptions<OrgDbContext> options) : base(options) {}
        public OrgDbContext() {}

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Client>().ToTable("Client");
        }
        public DbSet<Client> Clients { get; set; }
    }
}
