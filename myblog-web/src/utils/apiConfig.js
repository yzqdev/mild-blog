import axios from '@/utils/axios'

export const getIndex=() => {
  return axios.get("/home/index")
}

export const getConfigs=() => {
  return axios.get("/home/configs")
}
