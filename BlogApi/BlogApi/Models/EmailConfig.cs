using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("email_config")]
public class EmailConfig
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("email")]
    public string? Email { get; set; }

    [Column("email_key")]
    public string? EmailKey { get; set; }

    [Column("email_url")]
    public string? EmailUrl { get; set; }

    [Column("port")]
    public string? Port { get; set; }

    [Column("email_name")]
    public string? EmailName { get; set; }

    [Column("enable")]
    public bool Enable { get; set; }
}
