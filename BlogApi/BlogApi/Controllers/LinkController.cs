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
public class LinkController : ControllerBase
{
    private readonly BlogDbContext _context;

    public LinkController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("linkType/list")]
    public ActionResult<ApiResponse<object>> LinkTypeList()
    {
        var links = new object[]
        {
            new { linkType = 0, linkName = "友情链接" },
            new { linkType = 1, linkName = "推荐网站" },
            new { linkType = 2, linkName = "个人网站" }
        };
        return Ok(ApiResponse<object>.Ok(links));
    }

    [HttpGet("link/paging")]
    public async Task<ActionResult<ApiResponse<PageResult<Link>>>> GetLinkList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Links.OrderBy(l => l.LinkRank).ThenBy(l => l.CreateTime);
        var total = await query.CountAsync();
        var links = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<Link>
        {
            List = links,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<Link>>.Ok(result));
    }

    [HttpPost("link/edit")]
    public async Task<ActionResult<ApiResponse<string>>> EditLink([FromForm] Link link)
    {
        link.CreateTime = DateTime.Now;

        if (!string.IsNullOrEmpty(link.LinkId))
        {
            var existing = await _context.Links.FindAsync(link.LinkId);
            if (existing != null)
            {
                existing.LinkType = link.LinkType;
                existing.LinkName = link.LinkName;
                existing.LinkUrl = link.LinkUrl;
                existing.LinkDescription = link.LinkDescription;
                existing.LinkRank = link.LinkRank;
                existing.Show = link.Show;
                existing.UpdateTime = DateTime.Now;
            }
        }
        else
        {
            link.LinkId = Guid.NewGuid().ToString("N");
            _context.Links.Add(link);
        }

        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok(link.LinkId));
    }

    [HttpDelete("link/clear/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> ClearLink(string id)
    {
        var link = await _context.Links.FindAsync(id);
        if (link == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "链接不存在"));
        }

        _context.Links.Remove(link);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok(id));
    }
}
