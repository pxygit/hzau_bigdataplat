<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入申请人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据源ID" prop="datasourceId">
        <el-input
          v-model="queryParams.datasourceId"
          placeholder="请输入数据源ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="授权状态" prop="authStatus">
        <el-select v-model="queryParams.authStatus" placeholder="请选择授权状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_dashboard_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['screen:mydatasource:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['screen:mydatasource:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['screen:mydatasource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['screen:mydatasource:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mydatasourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80" type="index">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (queryParams.pageNum - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <el-table-column label="数据源名称" align="center" prop="name" />
<!--      <el-table-column label="数据源ID" align="center" prop="datasourceId" />-->
      <el-table-column label="数据源类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_datasource_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="可用状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_enabled" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="授权状态" align="center" prop="authStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_dashboard_status" :value="scope.row.authStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="授权时间" align="center" prop="authTime" width="180" />
      <el-table-column label="审批人" align="center" prop="authBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleShow(scope.row)"
            :disabled="scope.row.status !== 1 || scope.row.authStatus !== 1"
            v-hasPermi="['screen:datasource:show']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['screen:mydatasource:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改数据源信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="数据源名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入数据源名称" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据源类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择数据源类型" :disabled="isReadOnly">
                <el-option
                  v-for="dict in dict.type.sys_datasource_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="数据源描述" prop="desc">
              <el-input v-model="form.desc" type="textarea" placeholder="相关数据源简要描述" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据源地址" prop="ip">
              <el-input v-model="form.ip" placeholder="请输入地址" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本信息" prop="version">
              <el-input v-model="form.version" placeholder="请输入版本信息" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口" prop="port">
              <el-input v-model="form.port" placeholder="请输入端口" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据库名称" prop="database">
              <el-input v-model="form.database" placeholder="请输入数据库名称" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" :type="showPwd ? 'text' : 'password'" placeholder="请输入密码" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="其他必要配置" prop="otherInfo">
              <el-input v-model="form.otherInfo" type="textarea" placeholder="连接数据源需要的其他配置参数描述" :disabled="isReadOnly"/>
            </el-form-item>
          </el-col>
          <template v-if="isReadOnly">
            <el-col :span="12">
              <el-form-item label="创建人">
                <el-input v-model="form.createBy" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="创建时间">
                <el-input v-model="form.createTime" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="最近更新人">
                <el-input v-model="form.updateBy" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="最近更新时间">
                <el-input v-model="form.updateTime" disabled />
              </el-form-item>
            </el-col>
          </template>
          <el-col :span="24">
            <el-form-item label="可用状态" prop="status">
              <el-radio-group v-model="form.status" :disabled="isReadOnly">
                <el-radio
                  v-for="dict in dict.type.sys_enabled"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <template v-if="!isReadOnly">
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </template>
        <template v-if="isReadOnly">
          <el-button type="primary" @click="showPwd = !showPwd">{{ showPwd ? '隐藏密码' : '显示密码'}}</el-button>
        </template>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMydatasource, getMydatasource, delMydatasource, addMydatasource, updateMydatasource } from "@/api/screen/mydatasource";
import { showMyDatasourceInfo } from "@/api/screen/datasource";

export default {
  name: "Mydatasource",
  dicts: ['sys_dashboard_status','sys_datasource_type','sys_enabled'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 数据源申请授权表格数据
      mydatasourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      showPwd: false,// 是否显示密码
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        datasourceId: null,
        authStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "申请人ID不能为空", trigger: "blur" }
        ],
        datasourceId: [
          { required: true, message: "数据源ID不能为空", trigger: "blur" }
        ],
        authStatus: [
          { required: true, message: "授权状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据源申请授权列表 */
    getList() {
      this.loading = true;
      listMydatasource(this.queryParams).then(response => {
        this.mydatasourceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        datasourceId: null,
        authStatus: null,
        authTime: null,
        authBy: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据源申请授权";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMydatasource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据源申请授权";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMydatasource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMydatasource(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除数据源申请授权编号为"' + ids + '"的数据项？').then(function() {
        return delMydatasource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('screen/mydatasource/export', {
        ...this.queryParams
      }, `mydatasource_${new Date().getTime()}.xlsx`)
    },
    /** 显示详情按钮操作 */
    handleShow(row) {
      this.reset();
      const id = row.datasourceId
      showMyDatasourceInfo(id).then(response => {
        this.form = response.data;
        this.isReadOnly = true; // 设置为只读状态
        this.open = true;
        this.title = "查看数据源信息";
      });
    }
  }
};
</script>
