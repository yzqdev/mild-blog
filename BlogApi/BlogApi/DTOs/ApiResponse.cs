namespace BlogApi.DTOs;

public class ApiResponse<T>
{
    public int ResultCode { get; set; }
    public string Message { get; set; } = string.Empty;
    public T? Data { get; set; }
    public bool? Success { get; set; }
    public long Timestamp { get; set; }

    public static ApiResponse<T> Ok(T data, string message = "success")
    {
        return new ApiResponse<T>
        {
            ResultCode = 200,
            Message = message,
            Data = data,
            Success = true,
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
        };
    }

    public static ApiResponse<T> Error(int code, string message)
    {
        return new ApiResponse<T>
        {
            ResultCode = code,
            Message = message,
            Success = false,
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
        };
    }
}

public class PageResult<T>
{
    public List<T> List { get; set; } = new();
    public long Count { get; set; }
    public int Page { get; set; }
    public int Limit { get; set; }
    public int TotalPage { get; set; }
}
