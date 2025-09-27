package model

type Result struct {
	ResultCode int         `json:"resultCode"`
	Message    string      `json:"message"`
	Data       interface{} `json:"data"`
	Success    *bool       `json:"success"`
	Timestamp  int64       `json:"timestamp"`
}

type PageResult struct {
	List      interface{} `json:"list"`
	Count     int64       `json:"count"`
	Page      int         `json:"page"`
	Limit     int         `json:"limit"`
	TotalPage int         `json:"totalPage"`
}

func SuccessResult(data interface{}) *Result {
	success := true
	return &Result{
		ResultCode: 200,
		Message:    "success",
		Data:       data,
		Success:    &success,
		Timestamp:  getCurrentTimestamp(),
	}
}

func ErrorResult(message string) *Result {
	success := false
	return &Result{
		ResultCode: 500,
		Message:    message,
		Success:    &success,
		Timestamp:  getCurrentTimestamp(),
	}
}

func UnauthorizedResult(message string) *Result {
	success := false
	return &Result{
		ResultCode: 401,
		Message:    message,
		Success:    &success,
		Timestamp:  getCurrentTimestamp(),
	}
}

func BadRequestResult(message string) *Result {
	success := false
	return &Result{
		ResultCode: 400,
		Message:    message,
		Success:    &success,
		Timestamp:  getCurrentTimestamp(),
	}
}

func getCurrentTimestamp() int64 {
	return int64(0) // Will be set properly in controller
}
