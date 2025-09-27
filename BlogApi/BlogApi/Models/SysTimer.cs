using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("sys_timers")]
public class SysTimer
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("timer_name")]
    public string? TimerName { get; set; }

    [Column("action_class")]
    public string? ActionClass { get; set; }

    [Column("cron")]
    public string? Cron { get; set; }

    [Column("status")]
    public bool? Status { get; set; }

    [Column("remark")]
    public string? Remark { get; set; }

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
