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
public class SysLogController : ControllerBase
{
    private readonly BlogDbContext _context;

    public SysLogController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpPost("log")]
    public async Task<ActionResult<ApiResponse<PageResult<SysOpLog>>>> GetSysLogs(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.SysOpLogs.OrderByDescending(l => l.OpTime);
        var total = await query.CountAsync();
        var logs = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<SysOpLog>
        {
            List = logs,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<SysOpLog>>.Ok(result));
    }

    [HttpDelete("log/clear")]
    public async Task<ActionResult<ApiResponse<string>>> ClearAll()
    {
        await _context.SysOpLogs.ExecuteDeleteAsync();
        return Ok(ApiResponse<string>.Ok(""));
    }
}
