export const getApiUrl = (str='dev') => {

    const devArr = [  "localhost"];
    const apiArr = ["http://81.69.227.146:8080/"];
    let localUrl = "192.168.";

    let isDev =
      devArr.includes(document.domain) || document.domain.includes(localUrl);
    let isProd = apiArr.includes(document.domain);



    if (isDev) {
      return "http://localhost:2800/v2";
    }

    if (isProd) {
      return "http://81.69.227.146:2800/v2";
    }


};
