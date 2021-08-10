import axios from '@/utils/axios'

export const getIndex=() => {
  return axios.get("/home/index")
}

export const getConfigs=() => {
  return axios.get("/home/configs")
}
export const getLinks=() => {
  return axios.get('/home/link')
}
export const getBlogById=(data) => {
  return axios.get('/home/blog/'+data)
}
export const getArticleById=(data) => {
  return axios.get('/home/tag/'+data)
}
