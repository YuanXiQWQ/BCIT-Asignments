using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models;

namespace OrgMgmt
{
    public class OrgDbContext : DbContext
    {
        public OrgDbContext(DbContextOptions<OrgDbContext> options) : base(options)
        {
        }

        public DbSet<Person> People => Set<Person>();
        public DbSet<Client> Clients => Set<Client>();
        public DbSet<Employee> Employees => Set<Employee>();
        public DbSet<Service> Services => Set<Service>();
        public DbSet<ClientService> ClientServices => Set<ClientService>();

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Person>()
                .HasDiscriminator<string>("PersonType")
                .HasValue<Person>("Person")
                .HasValue<Client>("Client")
                .HasValue<Employee>("Employee");

            modelBuilder.Entity<Service>()
                .HasOne(service => service.Employee)
                .WithMany(employee => employee.Services)
                .HasForeignKey(service => service.EmployeeId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<ClientService>()
                .HasKey(clientService => new { clientService.ClientId, clientService.ServiceId });

            modelBuilder.Entity<ClientService>()
                .HasOne(clientService => clientService.Client)
                .WithMany(client => client.ClientServices)
                .HasForeignKey(clientService => clientService.ClientId);

            modelBuilder.Entity<ClientService>()
                .HasOne(clientService => clientService.Service)
                .WithMany(service => service.ClientServices)
                .HasForeignKey(clientService => clientService.ServiceId);
        }
    }
}