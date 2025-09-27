using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("comment")]
public class Comment
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("blog_id")]
    public string BlogId { get; set; } = string.Empty;

    [Column("commentator")]
    public string Commentator { get; set; } = string.Empty;

    [Column("email")]
    public string Email { get; set; } = string.Empty;

    [Column("website_url")]
    public string? WebsiteUrl { get; set; }

    [Column("comment_body")]
    public string CommentBody { get; set; } = string.Empty;

    [Column("comment_create_time")]
    public DateTime CommentCreateTime { get; set; }

    [Column("commentator_ip")]
    public string? CommentatorIp { get; set; }

    [Column("reply_body")]
    public string? ReplyBody { get; set; }

    [Column("reply_create_time")]
    public DateTime? ReplyCreateTime { get; set; }

    [Column("comment_status")]
    public bool CommentStatus { get; set; }

    [Column("user_agent")]
    public string? UserAgent { get; set; }

    [Column("os")]
    public string? Os { get; set; }

    [Column("deleted")]
    public bool Deleted { get; set; }
}
