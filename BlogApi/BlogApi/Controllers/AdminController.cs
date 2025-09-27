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
public class AdminController : ControllerBase
{
    private readonly BlogDbContext _context;

    public AdminController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("getUser")]
    public async Task<ActionResult<ApiResponse<AdminUser>>> GetUserInfo()
    {
        var userId = User.FindFirst("userId")?.Value;
        if (string.IsNullOrEmpty(userId))
        {
            return Unauthorized(ApiResponse<AdminUser>.Error(401, "请重新登录"));
        }

        var user = await _context.AdminUsers.FindAsync(userId);
        if (user == null)
        {
            return Unauthorized(ApiResponse<AdminUser>.Error(401, "用户不存在"));
        }

        return Ok(ApiResponse<AdminUser>.Ok(user));
    }

    [HttpGet("users")]
    public async Task<ActionResult<ApiResponse<List<AdminUser>>>> GetUsers()
    {
        var users = await _context.AdminUsers.ToListAsync();
        return Ok(ApiResponse<List<AdminUser>>.Ok(users));
    }

    [HttpGet("dashboard")]
    public async Task<ActionResult<ApiResponse<object>>> Dashboard()
    {
        var articleCount = await _context.BlogInfos.CountAsync();
        var commentCount = await _context.Comments.CountAsync();
        var views = await _context.BlogInfos.SumAsync(b => b.BlogViews);

        var res = new
        {
            articleCount,
            commentCount,
            viewsCount = views
        };

        return Ok(ApiResponse<object>.Ok(res));
    }
}
