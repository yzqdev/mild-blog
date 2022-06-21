using Furion.DynamicApiController;
using Microsoft.AspNetCore.Mvc;
using Mildblog.Application.System.Services;

namespace Mildblog.Application.Controller {
    /// <summary>
    /// 博客接口
    /// </summary>
    [Route("api/blog")]
    public class BlogController : IDynamicApiController {
        private readonly IBlogService _blogService;
        public BlogController(IBlogService blogService) {
            _blogService = blogService;
        }


        [HttpGet("blogInfo")]
        public string Get() {
            return $"Hello {nameof(Furion)}";
        }

        [HttpGet("blogs")]
        public object blog() {

            var blogs = _blogService.getBlogs().Result;
            return blogs;
        }
    }
}
