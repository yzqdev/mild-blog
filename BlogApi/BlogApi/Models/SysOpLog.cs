using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("sys_op_log")]
public class SysOpLog
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("name")]
    public string? Name { get; set; }

    [Column("op_type")]
    public string? OpType { get; set; }

    [Column("message")]
    public string? Message { get; set; }

    [Column("ip")]
    public string? Ip { get; set; }

    [Column("location")]
    public string? Location { get; set; }

    [Column("browser")]
    public string? Browser { get; set; }

    [Column("os")]
    public string? Os { get; set; }

    [Column("url")]
    public string? Url { get; set; }

    [Column("class_name")]
    public string? ClassName { get; set; }

    [Column("method_name")]
    public string? MethodName { get; set; }

    [Column("req_method")]
    public string? ReqMethod { get; set; }

    [Column("param")]
    public string? Param { get; set; }

    [Column("result")]
    public string? Result { get; set; }

    [Column("op_time")]
    public DateTime? OpTime { get; set; }

    [Column("account")]
    public string? Account { get; set; }
}
