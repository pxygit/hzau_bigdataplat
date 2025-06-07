<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="大屏账号ID" prop="dUserId">
        <el-input
          v-model="queryParams.dUserId"
          placeholder="请输入大屏账号ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前账号ID" prop="ryUserId">
        <el-input
          v-model="queryParams.ryUserId"
          placeholder="请输入当前账号ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择" clearable>
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
          v-hasPermi="['screen:uidmapping:add']"
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
          v-hasPermi="['screen:uidmapping:edit']"
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
          v-hasPermi="['screen:uidmapping:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['screen:uidmapping:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="uidmappingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80" type="index">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (queryParams.pageNum - 1) * queryParams.pageSize }}
        </template>
      </el-table-column>
      <el-table-column label="大屏账号ID" align="center" prop="dUserId" />
      <el-table-column label="当前账号ID" align="center" prop="ryUserId" />
      <el-table-column label="账号状态" align="center" prop="enabled">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_enabled" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['screen:uidmapping:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['screen:uidmapping:remove']"
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

    <!-- 添加或修改用户ID映射关系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="大屏账号ID" prop="dUserId">
          <el-input v-model="form.dUserId" placeholder="请输入大屏账号ID" />
        </el-form-item>
        <el-form-item label="当前账号ID" prop="ryUserId">
          <el-input v-model="form.ryUserId" placeholder="请输入当前账号ID" />
        </el-form-item>
        <el-form-item label="状态设置" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio
              v-for="dict in dict.type.sys_enabled"
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
import { listUidmapping, getUidmapping, delUidmapping, addUidmapping, updateUidmapping } from "@/api/screen/uidMapping";

export default {
  name: "Uidmapping",
  dicts: ['sys_enabled'],
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
      // 用户ID映射关系表格数据
      uidmappingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dUserId: null,
        ryUserId: null,
        enabled: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dUserId: [
          { required: true, message: "大屏账号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户ID映射关系列表 */
    getList() {
      this.loading = true;
      listUidmapping(this.queryParams).then(response => {
        this.uidmappingList = response.rows;
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
        dUserId: null,
        ryUserId: null,
        enabled: null
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
      this.ids = selection.map(item => item.dUserId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户ID映射关系";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const dUserId = row.dUserId || this.ids
      getUidmapping(dUserId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户ID映射关系";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dUserId != null) {
            updateUidmapping(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUidmapping(this.form).then(response => {
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
      const dUserIds = row.dUserId || this.ids;
      this.$modal.confirm('是否确认删除用户ID映射关系编号为"' + dUserIds + '"的数据项？').then(function() {
        return delUidmapping(dUserIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('screen/uidmapping/export', {
        ...this.queryParams
      }, `uidmapping_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
