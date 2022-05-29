using Microsoft.AspNetCore.Mvc;
using Mildblog.Model;

namespace Mildblog.Controllers;

[ApiController]
[Route("blog")]
public class BlogInfoController : ControllerBase
{
    private static readonly string[] Summaries = new[]
    {
        "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };

    private readonly ILogger<BlogInfoController> _logger;

    public BlogInfoController(ILogger<BlogInfoController> logger)
    {
        _logger = logger;
    }

    [HttpGet("bloginfo")]
    public IEnumerable<BlogInfo> Get()
    {
        return Enumerable.Range(1, 5).Select(index => new BlogInfo
        {
            Date = DateTime.Now.AddDays(index),
            TemperatureC = Random.Shared.Next(-20, 55),
            Summary = Summaries[Random.Shared.Next(Summaries.Length)]
        })
        .ToArray();
    }
}
