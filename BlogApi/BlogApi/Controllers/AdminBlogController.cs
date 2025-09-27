using BlogApi.Data;
using BlogApi.DTOs;
using BlogApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlogApi.Controllers;

[ApiController]
[Route("v2/admin")]
[Authorize]
public class AdminBlogController : ControllerBase
{
    private readonly BlogDbContext _context;

    public AdminBlogController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("blog/get/{id}")]
    public async Task<ActionResult<ApiResponse<BlogInfo>>> GetBlogById(string id)
    {
        var blog = await _context.BlogInfos.FirstOrDefaultAsync(b => b.BlogId == id);
        if (blog == null)
        {
            return NotFound(ApiResponse<BlogInfo>.Error(404, "博客不存在"));
        }
        return Ok(ApiResponse<BlogInfo>.Ok(blog));
    }

    [HttpPost("blog/edit")]
    public async Task<ActionResult<ApiResponse<BlogInfo>>> SaveBlog([FromBody] BlogInfoDto dto)
    {
        var blog = new BlogInfo
        {
            BlogId = dto.BlogId ?? Guid.NewGuid().ToString("N"),
            BlogTitle = dto.BlogTitle,
            SubUrl = dto.SubUrl,
            Preface = dto.Preface,
            BlogContent = dto.BlogContent,
            BlogViews = 0,
            EnableComment = dto.EnableComment ?? true,
            Show = dto.Show ?? true,
            Deleted = false,
            CreateTime = DateTime.Now,
            UpdateTime = DateTime.Now
        };

        var existing = await _context.BlogInfos.FindAsync(blog.BlogId);
        if (existing != null)
        {
            existing.BlogTitle = blog.BlogTitle;
            existing.SubUrl = blog.SubUrl;
            existing.Preface = blog.Preface;
            existing.BlogContent = blog.BlogContent;
            existing.EnableComment = blog.EnableComment;
            existing.Show = blog.Show;
            existing.UpdateTime = DateTime.Now;
        }
        else
        {
            _context.BlogInfos.Add(blog);
        }
        await _context.SaveChangesAsync();

        // Save category
        var categoryId = dto.BlogCategoryId ?? "1";
        var existingCategory = await _context.BlogCategories.FirstOrDefaultAsync(bc => bc.BlogId == blog.BlogId);
        if (existingCategory != null)
        {
            existingCategory.CategoryId = categoryId;
        }
        else
        {
            _context.BlogCategories.Add(new BlogCategory
            {
                BlogId = blog.BlogId,
                CategoryId = categoryId,
                CreateTime = DateTime.Now
            });
        }

        // Save tags
        var existingTags = await _context.BlogTags.Where(bt => bt.BlogId == blog.BlogId).ToListAsync();
        _context.BlogTags.RemoveRange(existingTags);

        if (dto.BlogTagIds != null)
        {
            foreach (var tagId in dto.BlogTagIds)
            {
                _context.BlogTags.Add(new BlogTag
                {
                    BlogId = blog.BlogId,
                    TagId = tagId,
                    CreateTime = DateTime.Now
                });
            }
        }

        await _context.SaveChangesAsync();
        return Ok(ApiResponse<BlogInfo>.Ok(blog));
    }

    [HttpGet("blog/list")]
    public async Task<ActionResult<ApiResponse<PageResult<BlogInfo>>>> GetBlogList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10,
        [FromQuery] bool? deleted = null)
    {
        var query = _context.BlogInfos.AsQueryable();
        query = query.Where(b => b.Deleted == (deleted ?? false));
        query = query.OrderByDescending(b => b.UpdateTime);

        var total = await query.CountAsync();
        var blogs = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<BlogInfo>
        {
            List = blogs,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<BlogInfo>>.Ok(result));
    }

    [HttpPost("blog/show/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> UpdateBlogShow(string id, [FromQuery] bool show)
    {
        var blog = await _context.BlogInfos.FindAsync(id);
        if (blog == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "博客不存在"));
        }

        blog.Show = show;
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }

    [HttpPost("blog/delete/{id}")]
    public async Task<ActionResult<ApiResponse<BlogInfo>>> DeleteBlog(string id, [FromQuery] bool restore)
    {
        var blog = await _context.BlogInfos.FindAsync(id);
        if (blog == null)
        {
            return NotFound(ApiResponse<BlogInfo>.Error(404, "博客不存在"));
        }

        blog.Show = false;
        blog.UpdateTime = DateTime.Now;
        blog.Deleted = !restore;
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<BlogInfo>.Ok(blog));
    }

    [HttpPost("blog/clear/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> ClearBlog(string id)
    {
        var blog = await _context.BlogInfos.FindAsync(id);
        if (blog == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "博客不存在"));
        }

        // Remove related tags and categories
        var tags = await _context.BlogTags.Where(bt => bt.BlogId == id).ToListAsync();
        _context.BlogTags.RemoveRange(tags);

        var categories = await _context.BlogCategories.Where(bc => bc.BlogId == id).ToListAsync();
        _context.BlogCategories.RemoveRange(categories);

        _context.BlogInfos.Remove(blog);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok(id));
    }

    [HttpPost("blog/restore")]
    public async Task<ActionResult<ApiResponse<string>>> RestoreBlog([FromForm] string blogId)
    {
        var blog = await _context.BlogInfos.FindAsync(blogId);
        if (blog == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "博客不存在"));
        }

        blog.Show = true;
        blog.UpdateTime = DateTime.Now;
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }
}
