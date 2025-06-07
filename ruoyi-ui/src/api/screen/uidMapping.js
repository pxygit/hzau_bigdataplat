import request from '@/utils/request'

// 查询用户ID映射关系列表
export function listUidmapping(query) {
  return request({
    url: '/screen/uidmapping/list',
    method: 'get',
    params: query
  })
}

// 查询用户ID映射关系详细
export function getUidmapping(dUserId) {
  return request({
    url: '/screen/uidmapping/' + dUserId,
    method: 'get'
  })
}

// 新增用户ID映射关系
export function addUidmapping(data) {
  return request({
    url: '/screen/uidmapping',
    method: 'post',
    data: data
  })
}

// 修改用户ID映射关系
export function updateUidmapping(data) {
  return request({
    url: '/screen/uidmapping',
    method: 'put',
    data: data
  })
}

// 删除用户ID映射关系
export function delUidmapping(dUserId) {
  return request({
    url: '/screen/uidmapping/' + dUserId,
    method: 'delete'
  })
}

// 获取大屏账号token 或检查当前用户是否已有大屏账号
export function checkUidMapping() {
  return request({
    url: '/screen/uidmapping/check',
    method: 'get'
  })
}
