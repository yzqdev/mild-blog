using Furion;
using Microsoft.Extensions.DependencyInjection;

namespace Mildblog.EntityFramework.Core {
    public class Startup : AppStartup {
        public void ConfigureServices(IServiceCollection services) {
            services.AddDatabaseAccessor(options => {
                options.AddDbPool<DefaultDbContext>();
            }, "Mildblog.Database.Migrations");
        }
    }
}