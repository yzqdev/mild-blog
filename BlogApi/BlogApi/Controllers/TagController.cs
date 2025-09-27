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
public class TagController : ControllerBase
{
    private readonly BlogDbContext _context;

    public TagController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("tags/list")]
    public async Task<ActionResult<ApiResponse<PageResult<Tag>>>> TagsList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Tags.AsQueryable();
        var total = await query.CountAsync();
        var tags = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<Tag>
        {
            List = tags,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<Tag>>.Ok(result));
    }

    [HttpPost("tags/add")]
    public async Task<ActionResult<ApiResponse<Tag>>> AddTag([FromForm] Tag tag)
    {
        tag.TagId = Guid.NewGuid().ToString("N");
        tag.Show = true;
        tag.CreateTime = DateTime.Now;
        tag.UpdateTime = DateTime.Now;
        _context.Tags.Add(tag);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<Tag>.Ok(tag));
    }

    [HttpPost("tags/update")]
    public async Task<ActionResult<ApiResponse<Tag>>> UpdateTag([FromBody] Tag tag)
    {
        var existing = await _context.Tags.FindAsync(tag.TagId);
        if (existing != null)
        {
            existing.TagName = tag.TagName;
            existing.Show = tag.Show;
            existing.UpdateTime = DateTime.Now;
            await _context.SaveChangesAsync();
        }
        return Ok(ApiResponse<Tag>.Ok(tag));
    }

    [HttpPost("tags/clear/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> ClearTag(string id)
    {
        var tag = await _context.Tags.FindAsync(id);
        if (tag == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "标签不存在"));
        }

        // Remove related blog-tag relationships
        var blogTags = await _context.BlogTags.Where(bt => bt.TagId == id).ToListAsync();
        _context.BlogTags.RemoveRange(blogTags);

        _context.Tags.Remove(tag);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok(tag.TagName));
    }
}
