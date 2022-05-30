using Furion.DatabaseAccessor;
using Microsoft.EntityFrameworkCore;

namespace Mildblog.EntityFramework.Core {
    [AppDbContext("PgConnectionString", DbProvider.Npgsql)]
    public class DefaultDbContext : AppDbContext<DefaultDbContext> {
        public DefaultDbContext(DbContextOptions<DefaultDbContext> options) : base(options) {
        }
    }
}