using BlogApi.Models;
using Microsoft.EntityFrameworkCore;

namespace BlogApi.Data;

public class BlogDbContext : DbContext
{
    public BlogDbContext(DbContextOptions<BlogDbContext> options) : base(options) { }

    public DbSet<AdminUser> AdminUsers => Set<AdminUser>();
    public DbSet<BlogInfo> BlogInfos => Set<BlogInfo>();
    public DbSet<Category> Categories => Set<Category>();
    public DbSet<Tag> Tags => Set<Tag>();
    public DbSet<BlogTag> BlogTags => Set<BlogTag>();
    public DbSet<BlogCategory> BlogCategories => Set<BlogCategory>();
    public DbSet<Comment> Comments => Set<Comment>();
    public DbSet<Link> Links => Set<Link>();
    public DbSet<BlogConfig> BlogConfigs => Set<BlogConfig>();
    public DbSet<Img> Imgs => Set<Img>();
    public DbSet<EmailConfig> EmailConfigs => Set<EmailConfig>();
    public DbSet<SysOpLog> SysOpLogs => Set<SysOpLog>();
    public DbSet<SysTimer> SysTimers => Set<SysTimer>();
    public DbSet<SysDictType> SysDictTypes => Set<SysDictType>();
    public DbSet<SysDictData> SysDictDatas => Set<SysDictData>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<AdminUser>()
            .HasIndex(e => e.Username)
            .IsUnique();
    }
}
