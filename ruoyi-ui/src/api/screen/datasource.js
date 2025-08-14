import request from '@/utils/request'

// 查询数据源信息列表
export function listDatasource(query) {
  return request({
    url: '/screen/datasource/list',
    method: 'get',
    params: query
  })
}

// 查询数据源信息详细
export function getDatasource(id) {
  return request({
    url: '/screen/datasource/' + id,
    method: 'get'
  })
}

// 在我的数据源中查询可用且已授权的数据源信息
export function showMyDatasourceInfo(id) {
  return request({
    url: '/screen/datasource/showmydatasource/' + id,
    method: 'get'
  })
}

// 新增数据源信息
export function addDatasource(data) {
  return request({
    url: '/screen/datasource',
    method: 'post',
    data: data
  })
}

// 修改数据源信息
export function updateDatasource(data) {
  return request({
    url: '/screen/datasource',
    method: 'put',
    data: data
  })
}

// 删除数据源信息
export function delDatasource(id) {
  return request({
    url: '/screen/datasource/' + id,
    method: 'delete'
  })
}
