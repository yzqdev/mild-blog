using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("link")]
public class Link
{
    [Key]
    [Column("link_id")]
    public string LinkId { get; set; } = Guid.NewGuid().ToString("N");

    [Column("link_type")]
    public short LinkType { get; set; }

    [Column("link_name")]
    public string LinkName { get; set; } = string.Empty;

    [Column("link_url")]
    public string LinkUrl { get; set; } = string.Empty;

    [Column("link_description")]
    public string LinkDescription { get; set; } = string.Empty;

    [Column("link_rank")]
    public int LinkRank { get; set; }

    [Column("show")]
    public bool Show { get; set; }

    [Column("create_time")]
    public DateTime CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
