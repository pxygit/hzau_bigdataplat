import request from '@/utils/request'

// 查询大屏信息列表
export function listInfo(query) {
  return request({
    url: '/screen/info/list',
    method: 'get',
    params: query
  })
}

// 查询大屏信息详细
export function getInfo(id) {
  return request({
    url: '/screen/info/' + id,
    method: 'get'
  })
}

// 新增大屏信息
export function addInfo(data) {
  return request({
    url: '/screen/info',
    method: 'post',
    data: data
  })
}

// 修改大屏信息
export function updateInfo(data) {
  return request({
    url: '/screen/info',
    method: 'put',
    data: data
  })
}

// 删除大屏信息
export function delInfo(id) {
  return request({
    url: '/screen/info/' + id,
    method: 'delete'
  })
}

// 获取大屏数据复制信息
export function getCopy(resourceId) {
  return request({
    url: '/screen/info/copy',
    method: 'get',
    params: { resourceId }
  })
}
