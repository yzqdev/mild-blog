import axios from "@/utils/axios";
import qs from "qs";
export const baseUrl=`http://localhost:2801`;
export const getIndex = () => {
  return axios.get("/home/index");
};

export const getConfigs = () => {
  return axios.get("/home/configs");
};
export const getLinks = () => {
  return axios.get("/home/link");
};
export const getHomeTags = () => {
  return axios.get("/home/tags");
};
export const getHomeCates = () => {
  return axios.get("/home/categories");
};
export const getTimeline = (x: { pageNum: number; pageSize: number }) => {
  return axios.post("/home/timeline", {
    pageNum: x.pageNum,
    pageSize: x.pageSize,
  });
};
export const getSearch = (keyword: string) => {
  return axios.get("/home/search/" + keyword);
};
export const listComments = (data: object) => {
  return axios.get("/home/blog/listComment?" + qs.stringify(data));
};
export const submitComment = (data: object) => {
  return axios.postForm("/home/blog/comment", data);
};
export const getBlogById = (data: string) => {
  return axios.get("/home/blog/" + data);
};
export const getArticleByTag = (id: string, data: object) => {
  return axios.post("/home/tag/" + id, data);
};
export const getArticleByCate = (id: string, data: object) => {
  return axios.post("/home/category/" + id, data);
};
export const loginApi = (username: string, password: string) => {
  return axios.post(`/admin/login?username=${username}&password=${password}`);
};
export const getUsers = () => {
  return axios.get(`/admin/users`);
};
export const delUsers = (id: string) => {
  return axios.post(`/admin/del/${id}`);
};
export const unlockUser = (id: string) => {
  return axios.post(`/admin/unlock/${id}`);
};
export const regApi = (username: string, password: string) => {
  return axios.post(`/admin/reg?username=${username}&password=${password}`);
};
export const getSystemInfo = () => {
  return axios.get("/admin/blogConfig/list");
};
export const addSystemInfo = (data: object) => {
  return axios.post("/admin/blogConfig/add", data);
};
export const editSystemInfo = (data: object) => {
  return axios.post("/admin/blogConfig/edit", data);
};
export const delSystemInfo = (data: string) => {
  return axios.delete("/admin/blogConfig/del/" + data);
};
export const getLinkList = (data: { page: number; limit: number }) => {
  return axios.get(`/admin/link/paging?page=${data.page}&limit=${data.limit}`);
};
export const editLink = (data: object) => {
  return axios.postForm("/admin/link/edit", data);
};
export const delLink = (data: object) => {
  return axios.postForm("/admin/link/isDel", data);
};
export const clearLink = (data: string) => {
  return axios.delete("/admin/link/clear/" + data);
};

export const getUserInfo = () => {
  return axios.get("/admin/getUser");
};
export const getBlogList = (data: { pageNum: number; pageSize: number }) => {
  return axios.get(
    `/admin/blog/list?pageNum=${data.pageNum}&pageSize=${data.pageSize}`
  );
};
export const getCommentList = (data: { page: number; limit: number }) => {
  return axios.get(
    `/admin/comment/paging?page=${data.page}&limit=${data.limit}`
  );
};
export const deleteCommentById = (id: string) => {
  return axios.delete(`/admin/comment/delete/${id}`);
};
export const checkCommentById = (id: string) => {
  return axios.post(`/admin/comment/delete/${id}`);
};
export const hideCommentById = (id: string) => {
  return axios.post(`/admin/comment/isDel/${id}`);
};
export const getTagList = () => {
  return axios.get(`/admin/tags/list`);
};
export const addTag = (data: object) => {
  return axios.postForm(`/admin/tags/add`, data);
};
export const EditTagList = (data: object) => {
  return axios.post(`/admin/tags/update`, data);
};
export const clearTagById = (data: object) => {
  return axios.post(`/admin/tags/clear/${data}`);
};
export const getCateList = () => {
  return axios.get(`/admin/category/list`);
};
export const addCateApi = (data: object) => {
  return axios.postForm(`/admin/category/add`, data);
};
export const clearCateApi = (data: string) => {
  return axios.post(`/admin/category/clear/${data}`);
};
export const getCatePaging = (data: { page: number; limit: number }) => {
  return axios.get(
    `/admin/category/paging?page=${data.page}&limit=${data.limit}`
  );
};
export const addBlog = (params: object) => {
  return axios.post("/admin/blog/edit", params);
};
export const clearBlog = (params: string) => {
  return axios.post("/admin/blog/clear/" + params);
};
export const deleteBlog = (params: string) => {
  return axios.post("/admin/blog/delete/" + params);
};
export const getAdminBlogById = (params: string) => {
  return axios.get("/admin/blog/get/" + params);
};
export const uploadUrl=`${import.meta.env.VITE_APP_URL}/img/upload`
export const uploadImg = (params: object) => {
  return axios.post("/img/upload", params, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
export const getImgs = () => {
  return axios.get("/img/list");
};
export const delImg = (id: string) => {
  return axios.delete("/img/del/" + id);
};
