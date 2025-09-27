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
public class CategoryController : ControllerBase
{
    private readonly BlogDbContext _context;

    public CategoryController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("category/list")]
    public async Task<ActionResult<ApiResponse<List<Category>>>> CategoryList()
    {
        var categories = await _context.Categories
            .Where(c => c.Show)
            .OrderByDescending(c => c.CreateTime)
            .ToListAsync();
        return Ok(ApiResponse<List<Category>>.Ok(categories));
    }

    [HttpGet("category/paging")]
    public async Task<ActionResult<ApiResponse<PageResult<Category>>>> GetCategoryList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Categories.Where(c => c.CategoryId != "1").OrderBy(c => c.CategoryRank);
        var total = await query.CountAsync();
        var categories = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<Category>
        {
            List = categories,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<Category>>.Ok(result));
    }

    [HttpPost("category/add")]
    public async Task<ActionResult<ApiResponse<Category>>> AddCategory([FromForm] Category category)
    {
        category.CategoryId = Guid.NewGuid().ToString("N");
        category.Show = true;
        category.CreateTime = DateTime.Now;
        _context.Categories.Add(category);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<Category>.Ok(category));
    }

    [HttpPost("category/update")]
    public async Task<ActionResult<ApiResponse<string>>> UpdateCategory([FromForm] Category category)
    {
        var existing = await _context.Categories.FindAsync(category.CategoryId);
        if (existing != null)
        {
            existing.CategoryName = category.CategoryName;
            existing.CategoryIcon = category.CategoryIcon;
            existing.CategoryRank = category.CategoryRank;
            await _context.SaveChangesAsync();
        }
        return Ok(ApiResponse<string>.Ok("成功"));
    }

    [HttpPost("category/clear/{id}")]
    public async Task<ActionResult<ApiResponse<string>>> ClearCategory(string id)
    {
        var category = await _context.Categories.FindAsync(id);
        if (category == null)
        {
            return NotFound(ApiResponse<string>.Error(404, "分类不存在"));
        }

        _context.Categories.Remove(category);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<string>.Ok(id));
    }
}
