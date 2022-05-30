using Microsoft.EntityFrameworkCore;
using Mildblog.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mildblog.Application.System.Services {
public interface IBlogService {
        Task<DbSet<BlogInfo>> getBlogs();
    }
}
