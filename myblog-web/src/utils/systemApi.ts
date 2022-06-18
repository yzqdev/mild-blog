import http from "./http";
import  qs from 'qs'
import { Result } from "@/interface/result";
export function clearAllSysLogApi() {
  return http.delete("/admin/log/clear");
}
export function dashboardApi(){
  return http.get("/admin/dashboard")
}
export  function addDictTypeApi(data:any){
  return http.post('/admin/dict/add',data)
}
export  function addDictDataApi( data:any):Promise<Result>{
  return http.post(`/admin/dictData/add`,data)
}
export  function getAllDictTypeApi(data: { page:number,limit:number }) :Promise<Result>{
  return http.get('/admin/dict/list?'+qs.stringify(data) )
}
export  function getAllDictDataApi(dictType:string,data: { page:number,limit:number }) :Promise<Result>{
  return http.get(`/admin/dictData/list/${dictType}?`+qs.stringify(data) )
}
export function clearDictTypeApi(dictType:string){
  return http.delete(`/admin/dict/clear/${dictType}`)
}
