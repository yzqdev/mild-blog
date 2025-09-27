using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("category")]
public class Category
{
    [Key]
    [Column("category_id")]
    public string CategoryId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("category_name")]
    public string CategoryName { get; set; } = string.Empty;

    [Column("category_icon")]
    public string? CategoryIcon { get; set; }

    [Column("category_rank")]
    public int CategoryRank { get; set; }

    [Column("create_time")]
    public DateTime CreateTime { get; set; }

    [Column("show")]
    public bool Show { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
