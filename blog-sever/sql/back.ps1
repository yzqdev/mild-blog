
pg_dump.exe -U postgres -s mild_blog > mild_blog_pg.sql

pg_dump.exe -U postgres -t blog_config -a mild_blog > mild_blog_config.sql