using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("sys_dict_data")]
public class SysDictData
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("type_id")]
    public string TypeId { get; set; } = string.Empty;

    [Column("value")]
    public string Value { get; set; } = string.Empty;

    [Column("code")]
    public string Code { get; set; } = string.Empty;

    [Column("sort")]
    public int Sort { get; set; }

    [Column("remark")]
    public string? Remark { get; set; }

    [Column("status")]
    public bool Status { get; set; }

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
