import request from '@/utils/request'

// 查询大屏授权状态列表
export function listAuth(query) {
  return request({
    url: '/screen/auth/list',
    method: 'get',
    params: query
  })
}

// 查询大屏授权状态详细
export function getAuth(id) {
  return request({
    url: '/screen/auth/' + id,
    method: 'get'
  })
}

// 新增大屏授权状态
export function addAuth(data) {
  return request({
    url: '/screen/auth',
    method: 'post',
    data: data
  })
}

// 修改大屏授权状态
export function updateAuth(data) {
  return request({
    url: '/screen/auth',
    method: 'put',
    data: data
  })
}

// 删除大屏授权状态
export function delAuth(id) {
  return request({
    url: '/screen/auth/' + id,
    method: 'delete'
  })
}
