using Microsoft.EntityFrameworkCore;
using OrgMgmt.Models;

namespace OrgMgmt
{
    public class OrgDbContext : DbContext
    {
        public OrgDbContext(DbContextOptions<OrgDbContext> options) : base(options)
        {
        }

        public OrgDbContext()
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Configure Client -> Service relationship (single service via ServiceId)
            modelBuilder.Entity<Client>()
                .HasOne(c => c.Service)
                .WithMany()
                .HasForeignKey(c => c.ServiceId)
                .OnDelete(DeleteBehavior.SetNull);

            // Configure ReviewResponse relationships to avoid cascade cycles
            modelBuilder.Entity<ReviewResponse>()
                .HasOne(r => r.PerformanceReview)
                .WithMany(p => p.Responses)
                .HasForeignKey(r => r.PerformanceReviewId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<ReviewResponse>()
                .HasOne(r => r.ReviewQuestion)
                .WithMany(q => q.Responses)
                .HasForeignKey(r => r.ReviewQuestionId)
                .OnDelete(DeleteBehavior.Restrict);

            // Configure PerformanceReview -> Reviewer relationship
            modelBuilder.Entity<PerformanceReview>()
                .HasOne(p => p.Reviewer)
                .WithMany()
                .HasForeignKey(p => p.ReviewerId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<PerformanceReview>()
                .HasOne(p => p.Employee)
                .WithMany()
                .HasForeignKey(p => p.EmployeeId)
                .OnDelete(DeleteBehavior.Restrict);
        }

        public DbSet<Person> People { get; set; }
        public DbSet<Client> Clients { get; set; }
        public DbSet<Employee> Employees { get; set; }
        public DbSet<Service> Services { get; set; }
        public DbSet<Shift> Shifts { get; set; }
        public DbSet<ShiftAssignment> ShiftAssignments { get; set; }
        
        // Performance Review related
        public DbSet<ReviewCycle> ReviewCycles { get; set; }
        public DbSet<ReviewQuestion> ReviewQuestions { get; set; }
        public DbSet<PerformanceReview> PerformanceReviews { get; set; }
        public DbSet<ReviewResponse> ReviewResponses { get; set; }
    }
}
