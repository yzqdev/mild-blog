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
public class SysDictController : ControllerBase
{
    private readonly BlogDbContext _context;

    public SysDictController(BlogDbContext context)
    {
        _context = context;
    }

    [HttpGet("dict/list")]
    public async Task<ActionResult<ApiResponse<PageResult<SysDictType>>>> GetDictList(
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.SysDictTypes.AsQueryable();
        var total = await query.CountAsync();
        var dictTypes = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<SysDictType>
        {
            List = dictTypes,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<SysDictType>>.Ok(result));
    }

    [HttpPost("dict/add")]
    public async Task<ActionResult<ApiResponse<SysDictType>>> AddDict([FromBody] SysDictType dictType)
    {
        dictType.Id = Guid.NewGuid().ToString("N");
        dictType.Status = true;
        _context.SysDictTypes.Add(dictType);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<SysDictType>.Ok(dictType));
    }

    [HttpDelete("dict/clear/{dictType}")]
    public async Task<ActionResult<ApiResponse<bool>>> ClearDictType(string dictType)
    {
        await _context.SysDictDatas.Where(d => d.TypeId == dictType).ExecuteDeleteAsync();
        await _context.SysDictTypes.Where(t => t.Id == dictType).ExecuteDeleteAsync();
        return Ok(ApiResponse<bool>.Ok(true));
    }

    [HttpPost("dictData/add")]
    public async Task<ActionResult<ApiResponse<SysDictData>>> AddDictData([FromBody] SysDictData dictData)
    {
        dictData.Id = Guid.NewGuid().ToString("N");
        dictData.Status = true;
        dictData.CreateTime = DateTime.Now;
        dictData.UpdateTime = DateTime.Now;
        _context.SysDictDatas.Add(dictData);
        await _context.SaveChangesAsync();
        return Ok(ApiResponse<SysDictData>.Ok(dictData));
    }

    [HttpGet("dictData/list/{dictType}")]
    public async Task<ActionResult<ApiResponse<PageResult<SysDictData>>>> GetDictDataList(
        string dictType,
        [FromQuery] int page = 1,
        [FromQuery] int limit = 10)
    {
        var query = _context.SysDictDatas.Where(d => d.TypeId == dictType);
        var total = await query.CountAsync();
        var dictData = await query.Skip((page - 1) * limit).Take(limit).ToListAsync();

        var result = new PageResult<SysDictData>
        {
            List = dictData,
            Count = total,
            Page = page,
            Limit = limit,
            TotalPage = (int)Math.Ceiling((double)total / limit)
        };

        return Ok(ApiResponse<PageResult<SysDictData>>.Ok(result));
    }
}
