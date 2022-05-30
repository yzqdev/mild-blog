using Furion.DatabaseAccessor;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mildblog.Core.Entities {
    [Table("tb_blog_info")]
    public class BlogInfo : Entity  {
        [Key]
       private int BlogId{ get; set; }
        private string blogTitle { get; set; }
        private String blogSubUrl { get; set; }
        private String blogPreface { get; set; }
        private String blogContent { get; set; }
        private int blogStatus { get; set; }
        private long blogViews { get; set; }

        private int enableComment { get; set; } 

   
    private  int isDeleted { get; set; } 

        
    private DateTime createTime { get; set; }

       
    private DateTime updateTime { get; set; }

    }
}
