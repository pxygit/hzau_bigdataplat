import request from '@/utils/request'

// 查询数据源申请授权列表
export function listMydatasource(query) {
  return request({
    url: '/screen/mydatasource/list',
    method: 'get',
    params: query
  })
}

// 查询数据源申请授权详细
export function getMydatasource(id) {
  return request({
    url: '/screen/mydatasource/' + id,
    method: 'get'
  })
}

// 新增数据源申请授权
export function addMydatasource(data) {
  return request({
    url: '/screen/mydatasource',
    method: 'post',
    data: data
  })
}

// 修改数据源申请授权
export function updateMydatasource(data) {
  return request({
    url: '/screen/mydatasource',
    method: 'put',
    data: data
  })
}

// 删除数据源申请授权
export function delMydatasource(id) {
  return request({
    url: '/screen/mydatasource/' + id,
    method: 'delete'
  })
}
