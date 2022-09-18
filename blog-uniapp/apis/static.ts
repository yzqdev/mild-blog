import ajax from '../ajax'

export function getMihoyo(){
	return ajax.get('/cat')
}
export function loginApi(username:string,password:string){
	return ajax.post(`/auth/login`,{username,password})
}