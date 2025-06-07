<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['screen:auth:add']"
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
          v-hasPermi="['screen:auth:edit']"
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
          v-hasPermi="['screen:auth:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['screen:auth:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80" type="index">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (queryParams.pageNum - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <el-table-column label="大屏名称" align="center" prop="name" />
      <el-table-column label="大屏ID" align="center" prop="resourceId" width="300"/>
      <el-table-column label="授权状态" align="center" prop="authStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_dashboard_status" :value="scope.row.authStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="最后授权时间" align="center" prop="authTime" width="150"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-position"
            @click="handleOpen(scope.row)"
            :disabled="scope.row.authStatus !== 1"
            v-hasPermi="['screen:auth:open']"
          >打开</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleCopy(scope.row)"
            :disabled="scope.row.authStatus !== 1"
            v-hasPermi="['screen:auth:copy']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['screen:auth:remove']"
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

    <!-- 添加或修改大屏授权状态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="大屏ID" prop="resourceId">
          <el-input v-model="form.resourceId" placeholder="请输入大屏ID" />
        </el-form-item>
        <el-form-item label="授权状态" prop="authStatus">
          <el-radio-group v-model="form.authStatus">
            <el-radio
              v-for="dict in dict.type.sys_dashboard_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="授权时间" prop="authTime">
          <el-date-picker clearable
                          v-model="form.authTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择授权时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

<!--    大屏链接复制，信息显示-->
    <el-dialog :title="title" :visible.sync="openCopy" width="500px" append-to-body>
      <el-form ref="formCopy" :model="formCopy" label-width="80px">
        <el-form-item label="大屏名称" prop="name">
          <el-input v-model="formCopy.name" disabled />
        </el-form-item>
        <el-form-item label="大屏ID" prop="resourceId">
          <el-input v-model="formCopy.resourceId" disabled />
        </el-form-item>
        <el-form-item label="映射路径" prop="path">
          <el-input v-model="formCopy.path" disabled />
        </el-form-item>
        <el-form-item label="访问密码" prop="pwd">
          <el-input v-model="formCopy.pwd" disabled />
        </el-form-item>
        <el-form-item label="链接状态" prop="pubStatus">
          <div style="display: inline-flex; align-items: center">
            <dict-tag :options="dict.type.sys_dashboard_linkstatus" :value=formCopy.pubStatus disabled />
            <el-tooltip class="item" effect="dark" content="未启用时链接不可用" placement="right"
                      v-if="formCopy.pubStatus !== 1">
              <el-icon class="el-icon-info" style="margin-left: 8px"/>
            </el-tooltip>
          </div>
        </el-form-item>
        <el-form-item label="链接地址" prop="url">
          <el-input v-model="formCopy.url" disabled />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="copyLink">复制链接</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAuth, getAuth, delAuth, addAuth, updateAuth } from "@/api/screen/auth";
import { getCopy } from "@/api/screen/info";

export default {
  name: "Auth",
  dicts: ['sys_dashboard_status','sys_dashboard_linkstatus'],
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
      // 大屏授权状态表格数据
      authList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openCopy: false, // 显示大屏链接复制
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        authStatus: null,
      },
      // 表单参数
      form: {},
      formCopy: {},
      // 表单校验
      rules: {
        resourceId: [
          { required: true, message: "大屏ID不能为空", trigger: "blur" }
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
    /** 查询大屏授权状态列表 */
    getList() {
      this.loading = true;
      listAuth(this.queryParams).then(response => {
        this.authList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openCopy = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        resourceId: null,
        authStatus: null,
        authTime: null
      };
      this.formCopy = {
        name: null,
        resourceId: null,
        path: null,
        pwd: null,
        pubStatus: null
      }
      this.resetForm("form");
      this.resetForm("formCopy");
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
      this.title = "添加大屏授权状态";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAuth(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改大屏授权状态";
      });
    },
    /** 快捷打开链接 */
    handleOpen(row) {
      getCopy(row.resourceId).then(response => {
        const path = response.data.path
        const dataeaseBaseUrl = process.env.VUE_APP_DATAEASE_BASE_URL
        const url = `http://${dataeaseBaseUrl}/link/${path}`
        window.open(url, '_blank');
      })
    },
    /** 复制按钮操作 */
    handleCopy(row) {
      this.reset();
      getCopy(row.resourceId).then(response => {
        this.formCopy = response.data;
        this.formCopy.url = `http://` + window.location.hostname + `:9528/link/` + this.formCopy.path;
        this.openCopy = true;
        this.title = "数据大屏信息 -> " + this.formCopy.name;
      });
    },
    copyLink() {
      if(this.formCopy.pubStatus !== 1) {
        this.$modal.msgError("大屏未启用，无法复制链接");
        return;
      }
      let url = this.formCopy.url;
      if(this.formCopy.pwd !== null && this.formCopy.pwd !== "" && this.formCopy.pwd.trim().length === 4) {
       url += ` Password: ` + this.formCopy.pwd.trim()
      }
      const copySuccess = this.$copy.text(url);
      if(copySuccess) {
        this.$modal.msgSuccess("复制成功");
      } else {
        this.$modal.msgError("复制失败");
      }
      this.openCopy = false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAuth(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAuth(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除大屏授权状态编号为"' + ids + '"的数据项？').then(function() {
        return delAuth(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('screen/auth/export', {
        ...this.queryParams
      }, `auth_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
