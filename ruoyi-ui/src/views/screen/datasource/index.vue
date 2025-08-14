<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="数据源名称" prop="name" label-width="100px">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入数据源名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据源类型" prop="type" label-width="100px">
        <el-select v-model="queryParams.type" placeholder="请选择数据源类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_datasource_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="可用状态" prop="status" label-width="80px">
        <el-select v-model="queryParams.status" placeholder="请选择可用状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_enabled"
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
          v-hasPermi="['screen:datasource:add']"
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
          v-hasPermi="['screen:datasource:edit']"
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
          v-hasPermi="['screen:datasource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['screen:datasource:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="datasourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80" type="index">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (queryParams.pageNum - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <el-table-column label="数据源名称" align="center" prop="name" />
      <el-table-column label="数据源类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_datasource_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="数据源描述" align="center" prop="desc" />
<!--      <el-table-column label="版本信息" align="center" prop="version" />-->
<!--      <el-table-column label="地址" align="center" prop="ip" />-->
<!--      <el-table-column label="端口" align="center" prop="port" />-->
<!--      <el-table-column label="数据库名称" align="center" prop="database" />-->
<!--      <el-table-column label="用户名" align="center" prop="username" />-->
<!--      <el-table-column label="密码" align="center" prop="password" />-->
<!--      <el-table-column label="其他必要配置" align="center" prop="otherInfo" />-->
      <el-table-column label="可用状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_enabled" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleShow(scope.row)"
            v-hasPermi="['screen:datasource:show']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['screen:datasource:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['screen:datasource:remove']"
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
              <el-input v-model="form.password" :type="showPwd ? 'text' : 'password'" placeholder="请输入密码" :disabled="isReadOnly" :show-password="!isReadOnly"/>
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
import { listDatasource, getDatasource, delDatasource, addDatasource, updateDatasource } from "@/api/screen/datasource";

export default {
  name: "Datasource",
  dicts: ['sys_datasource_type', 'sys_enabled'],
  data() {
    return {
      isReadOnly: false, // 是否只读
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
      // 数据源信息表格数据
      datasourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      showPwd: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        desc: null,
        version: null,
        ip: null,
        port: null,
        database: null,
        username: null,
        password: null,
        otherInfo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "数据源名称不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "数据源类型不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据源信息列表 */
    getList() {
      this.loading = true;
      listDatasource(this.queryParams).then(response => {
        this.datasourceList = response.rows;
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
        name: null,
        type: null,
        desc: null,
        version: null,
        ip: null,
        port: null,
        database: null,
        username: null,
        password: null,
        otherInfo: null,
        status: 0,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
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
      this.isReadOnly = false; // 设置为可编辑状态
      this.open = true;
      this.title = "添加数据源信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDatasource(id).then(response => {
        this.form = response.data;
        this.isReadOnly = false; // 设置为可编辑状态
        this.open = true;
        this.title = "修改数据源信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDatasource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDatasource(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除数据源信息编号为"' + ids + '"的数据项？').then(function() {
        return delDatasource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('screen/datasource/export', {
        ...this.queryParams
      }, `datasource_${new Date().getTime()}.xlsx`)
    },
    /** 显示详情按钮操作 */
    handleShow(row) {
      this.reset();
      const id = row.id || this.ids
      getDatasource(id).then(response => {
        this.form = response.data;
        this.isReadOnly = true; // 设置为只读状态
        this.open = true;
        this.title = "查看数据源信息";
      });
    }
  }
};
</script>
