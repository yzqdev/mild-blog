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
    [Table("blog_info")]
    public class BlogInfo : IEntity  {
        [Key]
        [Column("blog_id")]
       public string BlogId{ get; set; }
        [Column("blog_title")]
        public string blogTitle { get; set; }
        [Column("sub_url")]
        public String blogSubUrl { get; set; }
        [Column("preface")]
        public String blogPreface { get; set; }
        [Column("blog_content")]
        public String blogContent { get; set; }
        [Column("show")]
        public bool show { get; set; }
        [Column("blog_views")]
        public long blogViews { get; set; }
        [Column("enable_comment")]

        public bool enableComment { get; set; }
        [Column("deleted")]
   
    public bool deleted { get; set; }

        [Column("create_time")]
    public DateTime createTime { get; set; }
        [Column("update_time")]
       
    public DateTime updateTime { get; set; }

    }
}
