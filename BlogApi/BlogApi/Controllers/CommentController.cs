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
public class CommentController : ControllerBase
{
    private readonly BlogDbContext _context;

    public CommentController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("comment/paging")]
    public async Task<ActionResult<ApiResponse<PageResult<Comment>>>> GetCommentList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Comments.OrderByDescending(c => c.CommentCreateTime);
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

    [HttpPost("comment/isDel/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> UpdateCommentStatus(string id, [FromQuery] bool show)
    {
        var comment = await _context.Comments.FindAsync(id);
        if (comment == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "评论不存在"));
        }

        comment.CommentStatus = show;
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }

    [HttpDelete("comment/delete/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> DeleteComment(string id)
    {
        var comment = await _context.Comments.FindAsync(id);
        if (comment == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "评论不存在"));
        }

        _context.Comments.Remove(comment);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok("成功"));
    }
}
