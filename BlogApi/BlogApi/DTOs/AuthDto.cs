namespace BlogApi.DTOs;

public class LoginRequest
{
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}

public class RegisterRequest
{
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}

public class BlogInfoDto
{
    public string? BlogId { get; set; }
    public string BlogTitle { get; set; } = string.Empty;
    public string? SubUrl { get; set; }
    public string? Preface { get; set; }
    public string BlogContent { get; set; } = string.Empty;
    public string? BlogCategoryId { get; set; }
    public List<string>? BlogTagIds { get; set; }
    public bool? Show { get; set; }
    public bool? EnableComment { get; set; }
}
