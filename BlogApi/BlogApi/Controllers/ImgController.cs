using System.Security.Cryptography;
using System.Text;
using BlogApi.Data;
using BlogApi.DTOs;
using BlogApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlogApi.Controllers;

[ApiController]
[Route("v2/admin/img")]
[Authorize]
public class ImgController : ControllerBase
{
    private readonly BlogDbContext _context;
    private readonly IWebHostEnvironment _env;

    public ImgController(BlogDbContext context, IWebHostEnvironment env)
    {
        _context = context;
        _env = env;
    }

    [HttpPost("upload")]
    public async Task<ActionResult<ApiResponse<object>>> UploadImage(IFormFile img)
    {
        if (img == null || img.Length == 0)
        {
            return BadRequest(ApiResponse<object>.Error(400, "请选择文件"));
        }

        var uploadDir = Path.Combine(_env.ContentRootPath, "uploads");
        if (!Directory.Exists(uploadDir))
        {
            Directory.CreateDirectory(uploadDir);
        }

        var ext = Path.GetExtension(img.FileName);
        var filename = $"{DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()}{ext}";
        var filePath = Path.Combine(uploadDir, filename);

        using (var stream = new FileStream(filePath, FileMode.Create))
        {
            await img.CopyToAsync(stream);
        }

        // Calculate MD5
        using var md5 = MD5.Create();
        using var stream2 = System.IO.File.OpenRead(filePath);
        var hash = await md5.ComputeHashAsync(stream2);
        var md5Sum = BitConverter.ToString(hash).Replace("-", "").ToLowerInvariant();

        var imgEntity = new Img
        {
            ImgName = filename,
            ImgPath = filePath,
            ImgSize = (int)img.Length,
            ImgUrl = $"upload/{filename}",
            Md5 = md5Sum,
            UploadTime = DateTime.Now
        };

        _context.Imgs.Add(imgEntity);
        await _context.SaveChangesAsync();

        var result = new
        {
            message = "上传成功",
            url = imgEntity.ImgUrl,
            img = imgEntity
        };

        return Ok(ApiResponse<object>.Ok(result));
    }

    [HttpPost("list")]
    public async Task<ActionResult<ApiResponse<PageResult<Img>>>> ListImages(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.Imgs.OrderByDescending(i => i.UploadTime);
        var total = await query.CountAsync();
        var images = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<Img>
        {
            List = images,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<Img>>.Ok(result));
    }

    [HttpDelete("del/{id}")]
    public async Task<ActionResult<ApiResponse<Img>>> DeleteImage(string id)
    {
        var img = await _context.Imgs.FindAsync(id);
        if (img == null)
        {
            return NotFound(ApiResponse<Img>.Error(404, "图片不存在"));
        }

        // Delete files
        if (!string.IsNullOrEmpty(img.ImgPath) && System.IO.File.Exists(img.ImgPath))
        {
            System.IO.File.Delete(img.ImgPath);
        }

        _context.Imgs.Remove(img);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<Img>.Ok(img));
    }
}
