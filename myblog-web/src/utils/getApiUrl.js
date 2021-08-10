export const getApiUrl = (str='dev') => {

    const devArr = ["test.researchstar.cn", "localhost"];
    const apiArr = ["www.researchstar.cn"];
    let localUrl = "192.168.";

    let isDev =
      devArr.includes(document.domain) || document.domain.includes(localUrl);
    let isProd = apiArr.includes(document.domain);

    // 需要后端接   线上环境的域名组

    if (str === "socket") {
      if (isDev) {
        return "wss://dev.researchstar.cn/dobell-research/ws";
      }

      if (isProd) {
        return "wss://api.researchstar.cn/dobell-research/ws";
      }
    }

    if (isDev) {
      return "http://localhost:8023/api";
    }

    if (isProd) {
      return "https://api.researchstar.cn/dobell-research";
    }

};
