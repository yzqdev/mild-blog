import {api} from './axios';

export const getIndex = () => {
    return api.get("/home/index");
};
