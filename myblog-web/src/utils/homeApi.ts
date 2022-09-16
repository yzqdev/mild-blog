import http from '@/utils/http'
import qs from 'qs'
import { Result } from "@/interface/result";
export const getIndex = () => {
  return http.get('/home/index')
}

export const getConfigs = () => {
  return http.get('/home/configs')
}
export const getLinks = () => {
  return http.get('/home/link')
}
export const getHomeTags = () => {
  return http.get('/home/tags')
}
export const getHomeCates = () => {
  return http.get('/home/categories')
}
export const getTimeline = (x: { pageNum: number; pageSize: number }) => {
  return http.post('/home/timeline', {
    pageNum: x.pageNum,
    pageSize: x.pageSize,
  })
}
export const getSearch = (keyword: string) => {
  return http.get('/home/search/' + keyword)
}
export const listComments = (data: object) => {
  return http.get('/home/blog/listComment?' + qs.stringify(data))
}
export const submitComment = (data: object):Promise<Result<any>> => {
  return http.postForm('/home/blog/comment', data)
}
export const getBlogById = (data: string) => {
  return http.get('/home/blog/' + data)
}
export const getArticleByTag = (id: string, data: object) => {
  return http.post('/home/tag/' + id, data)
}
export const getArticleByCate = (id: string, data: object) => {
  return http.post('/home/category/' + id, data)
}
