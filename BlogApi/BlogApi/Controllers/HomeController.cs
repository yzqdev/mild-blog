using BlogApi.Data;
using BlogApi.DTOs;
using BlogApi.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlogApi.Controllers;

[ApiController]
[Route("v2/home")]
public class HomeController : ControllerBase
{
    private readonly BlogDbContext _context;

    public HomeController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    [HttpGet("index")]
    public async Task<ActionResult<ApiResponse<PageResult<BlogInfo>>>> HomeIndex(
        [FromQuery] int pageNum = 1,
        [FromQuery] int pageSize = 5)
    {
        var query = _context.BlogInfos
            .Where(b => !b.Deleted && b.Show)
            .OrderByDescending(b => b.UpdateTime);

        var total = await query.CountAsync();
        var blogs = await query.Skip((pageNum - 1) * pageSize).Take(pageSize).ToListAsync();

        var result = new PageResult<BlogInfo>
        {
            List = blogs,
            Count = total,
            Page = pageNum,
            Limit = pageSize,
            TotalPage = (int)Math.Ceiling((double)total / pageSize)
        };

        return Ok(ApiResponse<PageResult<BlogInfo>>.Ok(result));
    }

    [HttpGet("blog/{blogId}")]
    [HttpGet("article/{blogId}")]
    public async Task<ActionResult<ApiResponse<object>>> GetBlogDetail(string blogId)
    {
        var blog = await _context.BlogInfos.FindAsync(blogId);
        if (blog == null)
        {
            return NotFound(ApiResponse<object>.Error(404, "文章不存在"));
        }

        // Increment views
        blog.BlogViews++;
        await _context.SaveChangesAsync();

        // Get tags
        var blogTags = await _context.BlogTags.Where(bt => bt.BlogId == blogId).ToListAsync();
        var tagIds = blogTags.Select(bt => bt.TagId).ToList();
        var tags = await _context.Tags.Where(t => tagIds.Contains(t.TagId)).ToListAsync();

        // Get comment count
        var commentCount = await _context.Comments
            .CountAsync(c => c.BlogId == blogId && c.CommentStatus && c.Deleted);

        var result = new
        {
            blogDetailVO = blog,
            tagList = tags,
            commentCount
        };

        return Ok(ApiResponse<object>.Ok(result));
    }

    [HttpGet("tags")]
    public async Task<ActionResult<ApiResponse<List<Tag>>>> GetHomeTags()
    {
        var tags = await _context.Tags.Where(t => t.Show).ToListAsync();
        return Ok(ApiResponse<List<Tag>>.Ok(tags));
    }

    [HttpGet("categories")]
    public async Task<ActionResult<ApiResponse<List<Category>>>> GetHomeCategories()
    {
        var categories = await _context.Categories
            .Where(c => c.Show)
            .OrderByDescending(c => c.CreateTime)
            .ToListAsync();
        return Ok(ApiResponse<List<Category>>.Ok(categories));
    }

    [HttpGet("configs")]
    public async Task<ActionResult<ApiResponse<Dictionary<string, string>>>> GetHomeConfigs()
    {
        var configs = await _context.BlogConfigs.ToListAsync();
        var dict = configs.ToDictionary(c => c.ConfigCode ?? "", c => c.ConfigValue ?? "");
        return Ok(ApiResponse<Dictionary<string, string>>.Ok(dict));
    }

    [HttpGet("link")]
    public async Task<ActionResult<ApiResponse<object>>> GetLinks()
    {
        var favoriteLinks = await _context.Links.Where(l => l.LinkType == 0 && l.Show).ToListAsync();
        var recommendLinks = await _context.Links.Where(l => l.LinkType == 1 && l.Show).ToListAsync();
        var personalLinks = await _context.Links.Where(l => l.LinkType == 2 && l.Show).ToListAsync();

        var result = new
        {
            pageName = "友情链接",
            favoriteLinks,
            recommendLinks,
            personalLinks
        };

        return Ok(ApiResponse<object>.Ok(result));
    }

    [HttpGet("blog/listComment")]
    public async Task<ActionResult<ApiResponse<PageResult<Comment>>>> ListComments(
        [FromQuery] string blogId,
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Comments
            .Where(c => c.BlogId == blogId && c.CommentStatus && c.Deleted)
            .OrderByDescending(c => c.CommentCreateTime);

        var total = await query.CountAsync();
        var comments = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<Comment>
        {
            List = comments,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<Comment>>.Ok(result));
    }

    [HttpPost("blog/comment")]
    public async Task<ActionResult<ApiResponse<Comment>>> SubmitComment([FromForm] Comment comment)
    {
        comment.CommentStatus = true;
        comment.Deleted = true;
        comment.CommentCreateTime = DateTime.Now;
        comment.CommentatorIp = HttpContext.Connection.RemoteIpAddress?.ToString();

        _context.Comments.Add(comment);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<Comment>.Ok(comment));
    }
}
