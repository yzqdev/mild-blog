
pg_dump.exe -U postgres -s mild_blog > V1_1_1__Create_blog_table.sql

pg_dump.exe -U postgres -t blog_config -a mild_blog > V1_1_2__add_blog_config.sql