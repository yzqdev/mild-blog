using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BlogApi.Models;

[Table("img")]
public class Img
{
    [Key]
    [Column("id")]
    public string Id { get; set; } = Guid.NewGuid().ToString("N");

    [Column("img_name")]
    public string? ImgName { get; set; }

    [Column("img_size")]
    public int? ImgSize { get; set; }

    [Column("img_path")]
    public string? ImgPath { get; set; }

    [Column("img_url")]
    public string? ImgUrl { get; set; }

    [Column("img_type")]
    public string? ImgType { get; set; }

    [Column("media_type")]
    public string? MediaType { get; set; }

    [Column("upload_time")]
    public DateTime? UploadTime { get; set; }

    [Column("thumbnail_path")]
    public string? ThumbnailPath { get; set; }

    [Column("md5")]
    public string? Md5 { get; set; }
}
