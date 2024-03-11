import { boot } from 'quasar/wrappers'
import dayjs from 'dayjs'
export default boot(  ({ app, router, store }) => {
    // something to do
    app.config.globalProperties.$dayjs = dayjs;
})
