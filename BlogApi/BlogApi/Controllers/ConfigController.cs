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
public class ConfigController : ControllerBase
{
    private readonly BlogDbContext _context;

    public ConfigController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("blogConfig/list")]
    public async Task<ActionResult<ApiResponse<PageResult<BlogConfig>>>> GetBlogConfig()
    {
        var query = _context.BlogConfigs.OrderByDescending(c => c.UpdateTime);
        var total = await query.CountAsync();
        var configs = await query.ToListAsync();

        var result = new PageResult<BlogConfig>
        {
            List = configs,
            Count = total
        };

        return Ok(ApiResponse<PageResult<BlogConfig>>.Ok(result));
    }

    [HttpPost("blogConfig/edit")]
    public async Task<ActionResult<ApiResponse<string>>> UpdateBlogConfig([FromBody] BlogConfig config)
    {
        var existing = await _context.BlogConfigs.FindAsync(config.Id);
        if (existing != null)
        {
            existing.ConfigCode = config.ConfigCode;
            existing.ConfigName = config.ConfigName;
            existing.ConfigValue = config.ConfigValue;
            existing.UpdateTime = DateTime.Now;
            await _context.SaveChangesAsync();
        }
        return Ok(ApiResponse<string>.Ok("成功"));
    }

    [HttpPost("blogConfig/add")]
    public async Task<ActionResult<ApiResponse<string>>> AddBlogConfig([FromBody] BlogConfig config)
    {
        config.Id = Guid.NewGuid().ToString("N");
        config.CreateTime = DateTime.Now;
        config.UpdateTime = DateTime.Now;
        _context.BlogConfigs.Add(config);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }

    [HttpDelete("blogConfig/del/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> DeleteBlogConfig(string id)
    {
        var config = await _context.BlogConfigs.FindAsync(id);
        if (config == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "配置不存在"));
        }

        _context.BlogConfigs.Remove(config);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }
}
