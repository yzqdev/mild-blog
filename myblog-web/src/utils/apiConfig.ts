import axios from "@/utils/axios";
import qs from "qs";

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
export const getTimeline = ({pageNum, pageSize}) => {
    return axios.post("/home/timeline", {pageNum, pageSize});
};export const getSearch = (keyword) => {
    return axios.get("/home/search/"+ keyword);
};
export const listComments = (data) => {
    return axios.get("/home/blog/listComment?" + qs.stringify(data));
};
export const submitComment = (data) => {
    return axios.postForm("/home/blog/comment", data);
};
export const getBlogById = (data) => {
    return axios.get("/home/blog/" + data);
};
export const getArticleByTag = (id, data) => {
    return axios.post("/home/tag/" + id, data);
};export const getArticleByCate = (id, data) => {
    return axios.post("/home/category/" + id, data);
};
export const loginApi = (username, password) => {
    return axios.post(`/admin/login?username=${username}&password=${password}`);
};
export const getUsers = () => {
    return axios.get(`/admin/users`);
};
export const delUsers = (id) => {
    return axios.post(`/admin/del/${id}`);
};
export const unlockUser = (id) => {
    return axios.post(`/admin/unlock/${id}`);
};
export const regApi = (username, password) => {
    return axios.post(`/admin/reg?username=${username}&password=${password}`);
};
export const getSystemInfo = () => {
    return axios.get("/admin/blogConfig/list");
};
export const addSystemInfo = (data) => {
    return axios.post("/admin/blogConfig/add", data);
};
export const editSystemInfo = (data) => {
    return axios.post("/admin/blogConfig/edit", data);
};
export const delSystemInfo = (data) => {
    return axios.delete("/admin/blogConfig/del/" + data);
};
export const getLinkList = ({page, limit}) => {
    return axios.get(`/admin/link/paging?page=${page}&limit=${limit}`);
};
export const editLink = (data) => {
    return axios.postForm("/admin/link/edit", data);
};
export const delLink = (data) => {
    return axios.postForm("/admin/link/isDel", data);
};
export const clearLink = (data) => {
    return axios.delete("/admin/link/clear/" + data);
};

export const getUserInfo = () => {
    return axios.get("/admin/getUser");
};
export const getBlogList = ({pageNum, pageSize}) => {
    return axios.get(`/admin/blog/list?pageNum=${pageNum}&pageSize=${pageSize}`);
};
export const getCommentList = ({page, limit}) => {
    return axios.get(`/admin/comment/paging?page=${page}&limit=${limit}`);
};
export const deleteCommentById = (id) => {
    return axios.delete(`/admin/comment/delete/${id}`);
};
export const checkCommentById = (id) => {
    return axios.post(`/admin/comment/delete/${id}`);
};
export const hideCommentById = (id) => {
    return axios.post(`/admin/comment/isDel/${id}`);
};
export const getTagList = () => {
    return axios.get(`/admin/tags/list`);
};
export const addTag = (data) => {
    return axios.postForm(`/admin/tags/add`, data);
};
export const EditTagList = (data) => {
    return axios.post(`/admin/tags/update`, data);
};
export const clearTagById = (data) => {
    return axios.post(`/admin/tags/clear/${data}`);
};
export const getCateList = () => {
    return axios.get(`/admin/category/list`);
};
export const addCateApi = (data) => {
    return axios.postForm(`/admin/category/add`, data);
};
export const clearCateApi = (data) => {
    return axios.post(`/admin/category/clear/${data}`);
};
export const getCatePaging = ({page, limit}) => {
    return axios.get(`/admin/category/paging?page=${page}&limit=${limit}`);
};
export const addBlog = (params) => {
    return axios.post("/admin/blog/edit", params);
};
export const clearBlog = (params) => {
    return axios.post("/admin/blog/clear/" + params);
};
export const deleteBlog = (params) => {
    return axios.post("/admin/blog/delete/" + params);
};
export const getAdminBlogById = (params) => {
    return axios.get("/admin/blog/get/" + params);
};
export const uploadImg = (params) => {
    return axios.post('/img/upload', params, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}
export const getImgs=() => {
  return axios.get("/img/list")
}
export const delImg=(id) => {
  return axios.delete("/img/del/"+id)
}
