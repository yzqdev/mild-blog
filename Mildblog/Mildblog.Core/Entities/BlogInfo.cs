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
    public class BlogInfo : IEntity  {
        [Key]
        [Column("blog_id")]
       public string BlogId{ get; set; }
        [Column("blog_title")]
        public string blogTitle { get; set; }
        [Column("blog_sub_url")]
        public String blogSubUrl { get; set; }
        [Column("blog_preface")]
        public String blogPreface { get; set; }
        [Column("blog_content")]
        public String blogContent { get; set; }
        [Column("blog_status")]
        public int blogStatus { get; set; }
        [Column("blog_views")]
        public long blogViews { get; set; }
        [Column("enable_comment")]

        public int enableComment { get; set; }
        [Column("is_deleted")]
   
    public  int isDeleted { get; set; }

        [Column("create_time")]
    public DateTime createTime { get; set; }
        [Column("update_time")]
       
    public DateTime updateTime { get; set; }

    }
}
