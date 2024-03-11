export interface Result<T> {
  data: T
  message: string
  resultCode: number
  success: boolean
  timestamp: number
}
export interface ListData {
  code: number
  count: number
  list: any[]
  msg: string
}
export interface ArticleTag {
  tagId: string
  tagName: string
  show: boolean
  createTime: string
  updateTime: string
}
export interface Article {
  blogId: string
  blogTitle: string
  blogTagIds: ArticleTag[]
  blogCategoryId?: string
  blogContent: string
  preface: string
  subUrl: string
  show: boolean
  enableComment: boolean
}
