<template>
  <div class="app-container">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="24" :md="12" :lg="14" align="center">
        <el-card class="update-log" >
          <div slot="header" class="clearfix">
            <span>数据大屏制作入口页</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              数据大屏服务入口：
              <el-link type="primary" @click="jumpToDataEase">点我跳转制作数据大屏</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="10" align="center">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>其他服务入口页</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              大屏账号申请入口：
              <el-link type="primary" @click="jumpToApplyAccount">点我申请数据大屏账号</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-divider />
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="大屏名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入大屏名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="链接状态" prop="pubStatus">
        <el-select v-model="queryParams.pubStatus" placeholder="请选择链接状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_dashboard_pubstatus"
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
          v-hasPermi="['screen:info:add']"
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
          v-hasPermi="['screen:info:edit']"
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
          v-hasPermi="['screen:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['screen:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList">
      <el-table-column label="序号" align="center" width="80" type="index">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (queryParams.pageNum - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <el-table-column label="授权状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_dashboard_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="大屏名称" align="center" prop="name" />
      <el-table-column label="大屏ID" align="center" prop="resourceId" width="300"/>
<!--      <el-table-column label="映射字符" align="center" prop="path" />-->
<!--      <el-table-column label="访问密码" align="center" prop="pwd" />-->
      <el-table-column label="链接状态" align="center" prop="pubStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_dashboard_linkstatus" :value="scope.row.pubStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建用户" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="150"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="jumpToApplyAccess(scope.row)"
            :disabled="scope.row.status !== 0 || scope.row.pubStatus === 0"
            v-hasPermi="['screen:info:apply']"
          >申请</el-button>
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

    <!-- 添加或修改大屏信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="大屏名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入大屏名称" />
        </el-form-item>
        <el-form-item label="大屏ID" prop="resourceId">
          <el-input v-model="form.resourceId" placeholder="请输入大屏ID" />
        </el-form-item>
        <el-form-item label="映射字符" prop="path">
          <el-input v-model="form.path" placeholder="请输入映射字符" />
        </el-form-item>
        <el-form-item label="访问密码" prop="pwd">
          <el-input v-model="form.pwd" placeholder="请输入访问密码" />
        </el-form-item>
        <el-form-item label="发布状态" prop="pubStatus">
          <el-radio-group v-model="form.pubStatus">
            <el-radio
              v-for="dict in dict.type.sys_dashboard_pubstatus"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/screen/info";
import {checkUidMapping} from "@/api/screen/uidMapping";

export default {
  name: "Info",
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
      // 大屏信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        pubStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "大屏名称不能为空", trigger: "blur" }
        ],
        resourceId: [
          { required: true, message: "大屏ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询大屏信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
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
        resourceId: null,
        path: null,
        pwd: null,
        pubStatus: null,
        createBy: null,
        createTime: null
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
      this.title = "添加大屏信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改大屏信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除大屏信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('screen/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    },
    /** 跳转到数据大屏制作页面 */
    jumpToDataEase() {
      //检查是否有对应的账号权限
      checkUidMapping().then( res => {
        if (res.code === 200 && res.data.token) {
          const token = res.data.token
          const dataeaseBaseUrl = process.env.VUE_APP_DATAEASE_BASE_URL
          const url = `http://${dataeaseBaseUrl}/#/login?token=${token}`
          window.open(url, '_blank');
        } else {
          this.$modal.msgError("未找到对应的账号权限，请联系管理员");
        }
      }).catch( () => {} )
    },
    jumpToApplyAccess(row) {
      if(row.status === 0 && row.pubStatus === 1){
        this.$router.push('/task/process');
      }
    },
    jumpToApplyAccount() {
      checkUidMapping().then(res => {
        if (res.code === 200) {
          if (res.data.hasAccount !== "0") {
            this.$modal.msgWarning("当前用户已拥有对应的大屏账户,不可再申请!");
          } else {
            this.$router.push('/task/process');
          }
        }
      }).catch(() => {
        this.$modal.msgError("检查账号状态失败，请稍后重试");
      });
    }
  }
};
</script>
