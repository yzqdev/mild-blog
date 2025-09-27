using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("blog_config")]
public class BlogConfig
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("config_code")]
    public string? ConfigCode { get; set; }

    [Column("config_name")]
    public string? ConfigName { get; set; }

    [Column("config_value")]
    public string? ConfigValue { get; set; }

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
