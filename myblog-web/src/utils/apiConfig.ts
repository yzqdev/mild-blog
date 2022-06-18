import http from "@/utils/http";
import qs from "qs";
import axios from "axios";

export const baseUrl = () => {
  axios
    .get("/config.json")
    .then((res) => {
      return res.data.url;
    })
    .catch((err) => {
      return "http://localhost:2801";
    });
  return "http://localhost:2801";
};
export const loginApi = (username: string, password: string) => {
  return http.post(`/auth/login?username=${username}&password=${password}`);
};
export const getUsers = () => {
  return http.get(`/admin/users`);
};
export const editUser = (user: any) => {
  return http.postForm(`/admin/userEdit`, user);
};
export const delUsers = (id: string) => {
  return http.post(`/admin/del/${id}`);
};
export const unlockUser = (id: string) => {
  return http.post(`/admin/unlock/${id}`);
};
export const regApi = (username: string, password: string) => {
  return http.post(`/auth/reg?username=${username}&password=${password}`);
};
export const getSystemInfo = () => {
  return http.get("/admin/blogConfig/list");
};
export const addSystemInfo = (data: object) => {
  return http.post("/admin/blogConfig/add", data);
};
export const editSystemInfo = (data: object) => {
  return http.post("/admin/blogConfig/edit", data);
};
export const delSystemInfo = (data: string) => {
  return http.delete("/admin/blogConfig/del/" + data);
};
export const getLinkList = (data: { page: number; limit: number }) => {
  return http.get(`/admin/link/paging?` + qs.stringify(data));
};
export const editLink = (data: object) => {
  return http.postForm("/admin/link/edit", data);
};
export const delLink = (data: object) => {
  return http.postForm("/admin/link/hide", data);
};
export const clearLink = (data: string) => {
  return http.delete("/admin/link/clear/" + data);
};

export const getUserInfo = () => {
  return http.get("/admin/getUser");
};
export const getBlogList = (data: {
  page: number;
  limit: number;
  deleted: boolean;
}) => {
  return http.get(
    `/admin/blog/list?page=${data.page}&limit=${data.limit}&deleted=${data.deleted}`
  );
};
export const getCommentList = (data: { page: number; limit: number }) => {
  return http.get(
    `/admin/comment/paging?page=${data.page}&limit=${data.limit}`
  );
};
export const deleteCommentById = (id: string) => {
  return http.delete(`/admin/comment/delete/${id}`);
};
export const checkCommentById = (id: string) => {
  return http.post(`/admin/comment/delete/${id}`);
};
export const hideCommentById = (id: string, show: boolean) => {
  return http.post(`/admin/comment/isDel/${id}?show=${show}`);
};
export const getTagList = (data: {
  page: number;
  limit: number;
  show?: boolean;
}) => {
  return http.get(`/admin/tags/list?` + qs.stringify(data));
};
export const addTag = (data: object) => {
  return http.postForm(`/admin/tags/add`, data);
};
export const EditTagList = (data: object) => {
  return http.post(`/admin/tags/update`, data);
};
export const clearTagById = (data: object) => {
  return http.post(`/admin/tags/clear/${data}`);
};
export const getCateList = () => {
  return http.get(`/admin/category/list`);
};
export const addCateApi = (data: object) => {
  return http.postForm(`/admin/category/add`, data);
};
export const clearCateApi = (data: string) => {
  return http.post(`/admin/category/clear/${data}`);
};
export const getCatePaging = (data: { page: number; limit: number }) => {
  return http.get(
    `/admin/category/paging?page=${data.page}&limit=${data.limit}`
  );
};
export const addBlog = (params: object) => {
  return http.post("/admin/blog/edit", params);
};
export const clearBlog = (params: string) => {
  return http.post("/admin/blog/clear/" + params);
};
export const deleteBlog = (id: string, restore: boolean = false) => {
  return http.post(`/admin/blog/delete/${id}?restore=${restore}`);
};
export const hideBlog = (id: string, show: boolean = false) => {
  return http.post(`/admin/blog/show/${id}?show=${show}`);
};
export const getAdminBlogById = (params: string) => {
  return http.get("/admin/blog/get/" + params);
};
export function getSysLogApi(data: { page: number; limit: number }) {
  return http.get("/admin/log?" + qs.stringify(data));
}
export const uploadUrl = `${import.meta.env.VITE_APP_URL}/img/upload`;
export const uploadImg = (params: object) => {
  return http.post("/img/upload", params, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
export const getImgs = () => {
  return http.get("/img/list");
};
export const delImg = (id: string) => {
  return http.delete("/img/del/" + id);
};
