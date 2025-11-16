import request from '@/utils/request'
import {setToken,getToken} from '@/utils/auth'
import Keycloak from 'keycloak-js/dist/keycloak';

let initOptions = {
  url: 'http://192.168.196.101:9080',
  realm: 'hzau',
  clientId: 'flow-frontend',
  //enableLogging: true
}

const keycloak = new Keycloak(initOptions);
let authenticated = false;
export function initKeycloak(){
  return keycloak.init({
    onLoad: 'login-required',
    checkLoginIframe: false,
    pkceMethod: 'S256',
    redirectUri: 'http://192.168.196.101:1024/',
  }).then(async (auth) => {
    if (!auth) {
      window.location.reload()
    }
    // 暂做保留
    // if( !getToken()) {
    //   try {
    //     const res = await request({
    //       url: '/login',
    //       headers: {
    //         Authorization: 'Bearer ' + keycloak.token
    //       },
    //       method: 'post',
    //     });
    //     if( res.token) {
    //       setToken(res.token);
    //       authenticated = true;
    //     }
    //   } catch (err) {
    //     console.log("Error during login request:", err);
    //   }
    // } else {
      setToken(keycloak.token);
      authenticated = true
    // }
  }).catch((error) => {
    console.error('Keycloak init error:', error,error?.stack);
  });
}

export function keycloakLogout() {
  keycloak.logout("/login");
}

export function setAuthenticated(flag) {
  authenticated = flag
}

export function isAuthenticated() {
  return authenticated;
}

export default keycloak
