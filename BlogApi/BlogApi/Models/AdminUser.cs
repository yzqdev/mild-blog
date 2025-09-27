using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("admin_user")]
public class AdminUser
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("username")]
    public string Username { get; set; } = string.Empty;

    [Column("password")]
    public string Password { get; set; } = string.Empty;

    [Column("nickname")]
    public string? Nickname { get; set; }

    [Column("locked")]
    public bool Locked { get; set; }

    [Column("role")]
    public short Role { get; set; }

    [Column("avatar")]
    public string? Avatar { get; set; }

    [Column("email")]
    public string? Email { get; set; }

    [Column("uuid")]
    public string? Uuid { get; set; }

    [Column("create_time")]
    public DateTime? CreateTime { get; set; }

    [Column("update_time")]
    public DateTime? UpdateTime { get; set; }
}
