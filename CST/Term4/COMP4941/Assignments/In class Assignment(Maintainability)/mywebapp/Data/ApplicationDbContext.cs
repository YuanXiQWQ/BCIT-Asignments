using Microsoft.EntityFrameworkCore;

namespace mywebapp.Data;

public class ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : DbContext(options);
