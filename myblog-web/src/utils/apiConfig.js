import axios from "@/utils/axios";
// 网站信息
export const getSiteInfo = () => {
  return axios.get("/siteinfo");
};
// 获取分类列表
export const getCategories = () => {
  return axios.get("/categories");
};
// 获取文章列表
export const getArticles = () => {
  return axios.get("/articles");
};
// 获取文章信息
export const getArticle = (id) => {
  return axios.get(`/articles/info/${id}`);
};
// 删除文章
export const deleteArticle = (id) => {
  return axios.delete(`articles/delete/${id}`);
}; // 获取积分排名前10的用户
export const getTop10 = () => {
  return axios.get("/user/score/top10");
};
// 获取积分排名前100的用户
export const getTop100 = () => {
  return axios.get('"/user/score/top100');
};
export const getUserArticles = (id) => {
  return axios.get(`/articles/user/${id}`);
}; // 获取当前登录用户信息
export const getUserInfo = () => {
  return axios.get("/user/info");
}; // 新建文章
export const createArticle = () => {
  return axios.post("/articles/create");
};
// 登陆

export const signIn = (type, data) => {
  return axios.post(`/signin?loginType=${type}`, data);
};
export const signUp = (data) => {
  return axios.post("/signup", data);
};
export const getMessages = () => {
  return axios.get("/messages/unread");
};
export const getMaxComment = () => {
  // 回复最多的话题
  return axios.get("/articles/max/bycomment");
};
export const getMaxBrowse = () => {
  // 浏览最多的话题
  return axios.get("/articles/max/bybrowse");
};
export const updateArticle = () => {
  // 浏览最多的话题
  return axios.put("/articles/update");
};

export const changePwd = () => {
  // 修改密码
  return axios.post("/user/password/update");
};
export const sendEmailPwd = (data) => {
  // 忘记密码邮箱确认
  return axios.post("/reset/sendmail", data);
};
export const resetPwd = (id, key) => {
  // 重置密码
  return axios.post(`/reset/password/${id}/${key}`);
};
export const activeUser = () => {
  // 账号激活
  return axios.post(`/active/user/${id}/:{key}`);
};
export const verifyUrl = () => {
  // 验证重置密码链接是否失效
  return axios.post("/reset/verify/:id/:key");
};
export const commentCreate = () => {
  // 提交评论
  return axios.post("/comments/create");
};
export const commentEdit = () => {
  // 编辑评论
  return axios.post("/comments/update");
};
export const deleteComment = () => {
  // 删除评论
  return axios.post("/comments/delete/:id");
};

export const logout = () => {
  // 退出登录
  return axios.post("/signout");
};
export const createVote = () => {
  // 创建投票
  return axios.post("/votes/create");
};
export const updateVote = () => {
  // 编辑投票
  return axios.post("/votes/update");
};
export const deleteVote = () => {
  // 删除投票
  return axios.post("/votes/delete/:id");
};
export const editVoteItem = () => {
  // 编辑投票项
  return axios.post("/votes/item/edit");
};
export const addVoteItem = () => {
  // 新增投票项
  return axios.post("/votes/item/create");
};
export const deleteVoteItem = () => {
  // 删除投票项
  return axios.post("/votes/item/delete/:id");
};
export const getVotes = () => {
  // 获取投票列表
  return axios.get("/votes");
};
export const getVote = () => {
  // 获取投票列表
  return axios.post("/votes/info/:id");
};
export const userVote = () => {
  // 投票
  return axios.post("/votes/uservote/:id");
};
export const getVoteMaxBrowse = () => {
  // 浏览量最多的投票
  return axios.post("/votes/max/bybrowse");
};
export const getVoteMaxComment = () => {
  // 回复最多的投票
  return axios.post("/votes/max/bycomment");
};
export const getTopList = () => {
  // 获取置顶文章列表
  return axios.post("/articles/top/global");
};
export const setTop = () => {
  // 设置置顶
  return axios.post("/articles/top/:id");
};
export const delTop = () => {
  // 取消置顶
  return axios.post("/articles/deltop/:id");
};
export const getMineComment = () => {
  // 获取当前用户回复
  return axios.post("/comments/user/:userID");
};
export const getMineVote = () => {
  // 获取当前用户投票
  return axios.post("/votes/user/:userID");
};
export const getPublicUser = () => {
  // 获取其他用户信息
  return axios.post("/user/info/public/:id");
};
export const sendmail = () => {
  // 发送邮件
  return axios.post("/active/sendmail");
};
export const userInfoDetail = () => {
  // 获取用户详情
  return axios.post("/user/info/detail");
};
export const updateInfo = () => {
  // 修改用户信息
  return axios.post("/user/update/:type");
};
export const schoolAdd = () => {
  // 添加教育经历
  return axios.post("/user/school/add");
};
export const schoolDelete = () => {
  // 删除教育经历
  return axios.post("/user/school/delete/:id");
};
export const careerAdd = () => {
  // 增加工作经历
  return axios.post("/user/career/add");
};
export const careerDelete = () => {
  // 删除工作经历
  return axios.post("/user/career/delete/:id");
};
export const getCollectDirList = () => {
  // 查询用户的收藏夹列表
  return axios.post("/collects/user/:userID/folders");
};
export const getFoldersSource = () => {
  return axios.post("/collects/folders/withsource"); // 查询用户的收藏夹列表，并且返回每个收藏夹中收藏了哪些话题或投票
};
export const createCollectDir = () => {
  // 创建收藏夹
  return axios.post("/collects/folder/create");
};
export const createCollect = () => {
  // 收藏文章或收藏投票
  return axios.post("/collects/create"); // collect_source_article收藏文章; collect_source_vote收藏投票
};
export const cancelCollect = () => {
  // 取消收藏
  return axios.post("/collects/delete/:id");
};
export const collectList = () => {
  // 获取收藏夹下的话题
  return axios.post("/collects");
};

export const readMessage = (id) => {
  // 将消息标记为已读
  return axios.post(`/messages/read/${id}`);
};
export const getBooks = () => {
  // 获取图书列表
  return axios.get("/books");
};
export const getBook = () => {
  // 获取图书信息
  return axios.post("/books/info/:id");
};
export const createBook = () => {
  // 创建图书
  return axios.post("/books");
};
export const updateBook = () => {
  // 更新图书
  return axios.post("/books/update");
};
export const updateBookName = () => {
  // 更新图书的名称
  return axios.post("/books/updatename");
};
export const publishBook = () => {
  // 发布图书
  return axios.post("/books/publish/:bookID");
};
export const getBookChapters = () => {
  // 获取图书的所有章节
  return axios.post("/books/chapters/:id");
};
export const getBookChapter = () => {
  // 获取章节
  return axios.post("/books/chapter/:chapterID");
};
export const createBookChapter = () => {
  // 创建图书的章节
  return axios.post("/books/chapters");
};
export const updateBookChapterName = () => {
  // 更新图书的章节的名称
  return axios.post("/books/chapters/updatename");
};
export const saveBookChapterContent = () => {
  // 保存图书的章节内容
  return axios.post("/books/chapters/content");
};
export const deleteBookChapter = () => {
  // 删除图书的章节
  return axios.post("/books/chapters/:chapterID");
};
