using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("blog_tag")]
public class BlogTag
{
    [Key]
    [Column("relation_id")]
    public string RelationId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("blog_id")]
    public string BlogId { get; set; } = string.Empty;

    [Column("tag_id")]
    public string TagId { get; set; } = string.Empty;

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }
}
