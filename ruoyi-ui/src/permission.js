import router from './router'
import store from './store'
import {MessageBox} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import {isAuthenticated, keycloakLogout} from "./store/modules/keycloak";

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0 && isAuthenticated()) {
        // isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          // isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {

        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      if (isAuthenticated()){
        MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            keycloakLogout();
          })
        })
      }
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
