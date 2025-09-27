using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("blog_category")]
public class BlogCategory
{
    [Key]
    [Column("relation_id")]
    public string RelationId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("blog_id")]
    public string BlogId { get; set; } = string.Empty;

    [Column("category_id")]
    public string CategoryId { get; set; } = string.Empty;

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }
}
