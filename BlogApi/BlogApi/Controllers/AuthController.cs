using BlogApi.Data;
using BlogApi.DTOs;
using BlogApi.Models;
using BlogApi.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlogApi.Controllers;

[ApiController]
[Route("v2/auth")]
public class AuthController : ControllerBase
{
    private readonly BlogDbContext _context;
    private readonly JwtService _jwtService;

    public AuthController(BlogDbContext context, JwtService jwtService)
    {
        _context = context;
        _jwtService = jwtService;
    }

    [HttpPost("login")]
    public async Task<ActionResult<ApiResponse<object>>> Login([FromBody] LoginRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.Username) || string.IsNullOrWhiteSpace(request.Password))
        {
            return BadRequest(ApiResponse<object>.Error(400, "用户名和密码不能为空"));
        }

        var user = await _context.AdminUsers.FirstOrDefaultAsync(u => u.Username == request.Username);
        if (user == null)
        {
            return Unauthorized(ApiResponse<object>.Error(401, "用户不存在"));
        }

        if (user.Locked)
        {
            return Unauthorized(ApiResponse<object>.Error(401, "账户已被冻结"));
        }

        // Simple password check (in production, use BCrypt)
        if (user.Password != request.Password)
        {
            return Unauthorized(ApiResponse<object>.Error(401, "密码错误"));
        }

        var token = _jwtService.GenerateToken(user.Id, user.Username);
        return Ok(ApiResponse<object>.Ok(token));
    }

    [HttpPost("reg")]
    public async Task<ActionResult<ApiResponse<object>>> Register([FromBody] RegisterRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.Username) || string.IsNullOrWhiteSpace(request.Password))
        {
            return BadRequest(ApiResponse<object>.Error(400, "用户名和密码不能为空"));
        }

        if (await _context.AdminUsers.AnyAsync(u => u.Username == request.Username))
        {
            return BadRequest(ApiResponse<object>.Error(400, "用户名已存在"));
        }

        var user = new AdminUser
        {
            Username = request.Username,
            Password = request.Password,
            Uuid = Guid.NewGuid().ToString(),
            Locked = true,
            Role = 0
        };

        _context.AdminUsers.Add(user);
        await _context.SaveChangesAsync();

        return Ok(ApiResponse<object>.Ok("注册成功"));
    }
}
