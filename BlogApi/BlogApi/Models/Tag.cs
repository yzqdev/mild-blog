using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("tag")]
public class Tag
{
    [Key]
    [Column("tag_id")]
    public string TagId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("tag_name")]
    public string TagName { get; set; } = string.Empty;

    [Column("create_time")]
    public DateTime CreateTime { get; set; }

    [Column("show")]
    public bool Show { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
