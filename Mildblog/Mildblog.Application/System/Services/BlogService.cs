using Furion.DatabaseAccessor;
using Furion.DependencyInjection;
using Microsoft.EntityFrameworkCore;
using Mildblog.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mildblog.Application.System.Services {
    public class BlogService:IBlogService, ITransient {
        private readonly IRepository<BlogInfo> _blogRepository;

        public BlogService(IRepository<BlogInfo>  blogRepository) {
            _blogRepository= blogRepository; 
        }
     public  async Task<DbSet<BlogInfo>> getBlogs() {
            var blogs =    _blogRepository.Entities;
            return blogs ;
        }
    }
}
