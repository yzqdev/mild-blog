using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("blog_info")]
public class BlogInfo
{
    [Key]
    [Column("blog_id")]
    public string BlogId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("blog_title")]
    public string BlogTitle { get; set; } = string.Empty;

    [Column("sub_url")]
    public string? SubUrl { get; set; }

    [Column("preface")]
    public string? Preface { get; set; }

    [Column("blog_content")]
    public string BlogContent { get; set; } = string.Empty;

    [Column("blog_views")]
    public long BlogViews { get; set; }

    [Column("enable_comment")]
    public bool EnableComment { get; set; }

    [Column("create_time")]
    public DateTime CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }

    [Column("show")]
    public bool Show { get; set; }

    [Column("deleted")]
    public bool Deleted { get; set; }
}
