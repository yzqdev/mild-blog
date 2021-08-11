import axios from "@/utils/axios";
export const getIndex = () => {
  return axios.get("/home/index");
};

export const getConfigs = () => {
  return axios.get("/home/configs");
};
export const getLinks = () => {
  return axios.get("/home/link");
};
export const getBlogById = (data) => {
  return axios.get("/home/blog/" + data);
};
export const getArticleById = (data) => {
  return axios.get("/home/tag/" + data);
};
export const loginApi = (username,password) => {
  return axios.post(`/admin/login?username=${username}&password=${password}`,  );
};
export const regApi = (username,password) => {
  return axios.post(`/admin/reg?username=${username}&password=${password}`,  );
};
export const getSystemInfo=() => {
  return axios.get('/admin/blogConfig/list')
}
export const addSystemInfo=(data) => {
  return axios.post('/admin/blogConfig/add',data)
}
export const delSystemInfo=(data) => {
  return axios.delete('/admin/blogConfig/del/'+data)
}
export const getLinkList=({page,limit}) => {
  return axios.get(`/admin/link/paging?page=${page}&limit=${limit}`)
}
export const editLink=(data ) => {
  return axios.post("/admin/link/edit",data)
}
export const getUserInfo=(  ) => {
  return axios.get("/admin/getUser" )
}
export const getBlogList=({page,limit}) => {
  return axios.get(`/admin/blog/list?page=${page}&limit=${limit}`)
}
export const getCommentList=({page,limit}) => {
  return axios.get(`/admin/comment/paging?page=${page}&limit=${limit}`)
}
export const getTagList=( ) => {
  return axios.get(`/admin/tags/list`)
}
export const getCateList=( ) => {
  return axios.get(`/admin/category/list`)
}
export const addBlog=(params) => {
  return axios.post('/admin/blog/edit',params)
}
