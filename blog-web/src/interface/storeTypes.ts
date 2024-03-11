export interface ThemeState {
  dark: boolean
  contentPadding: boolean
  showFooter: boolean
}

export interface UserState {
  id: string
  token: string
  username: string
  nickname?: string
  email?: string
  role?: number
  telephone?: string
  avatar?: string
}
export interface SysConfig {
  sysAuthor: string
  sysAuthorImg: string
  sysCopyRight: string
  sysEmail: string
  sysUpdateTime: string
  sysUrl: string
  sysVersion: string
  websiteName: string
}
