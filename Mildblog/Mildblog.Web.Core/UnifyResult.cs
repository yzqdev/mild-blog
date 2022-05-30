using Furion.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mildblog.Web.Core {
    //
    // 摘要:
    //     RESTful 风格结果集
    //
    // 类型参数:
    //   T:
    [SuppressSniffer]
    public class RESTfulResult<T> {
        //
        // 摘要:
        //     状态码
        public int?  Code { get; set; }

        //
        // 摘要:
        //     数据
        public T Data { get; set; }

        //
        // 摘要:
        //     执行成功
        public bool Success { get; set; }

        //
        // 摘要:
        //     错误信息
        public object Errors { get; set; }

        //
        // 摘要:
        //     附加数据
        public object Extras { get; set; }

        //
        // 摘要:
        //     时间戳
        public long Timestamp { get; set; }
    }
}