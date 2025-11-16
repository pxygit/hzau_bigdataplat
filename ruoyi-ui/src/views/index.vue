<template>
  <div class="app-container home">
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
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
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>大屏账号申请入口页</span>
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
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
        <el-card class="tool-card" >
          <div slot="header" class="clearfix">
            <span>数据治理平台DGC</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              数据治理平台服务入口：
              <el-link type="primary" @click="jumpToDGC">点我前往数据治理平台</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
        <el-card class="tool-card">
          <div slot="header" class="clearfix">
            <span>数据流处理工具ETL</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              Apache NiFi服务入口：
              <el-link type="primary" @click="jumpToETL">点我前往NiFi数据处理工具</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
        <el-card class="tool-card" >
          <div slot="header" class="clearfix">
            <span>知识问答系统QA</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              智能问答服务入口：
              <el-link type="primary" @click="jumpToKQA">点我前往智能问答畅聊</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" align="center">
        <el-card class="tool-card" >
          <div slot="header" class="clearfix">
            <span>统一身份管理CAS</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-link"></i>
              Keycloak服务入口：
              <el-link type="primary" @click="jumpToCAS">点我前往Keycloak管理</el-link>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-empty description="功能开发中..."></el-empty>
  </div>
</template>

<script>
import {checkUidMapping} from "@/api/screen/uidMapping";
export default {
  name: 'Home',
  methods: {
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
    },
    jumpToDGC() {
      const dgcBaseUrl = process.env["VUE_APP_DGC_BASE_URL"]
      const url = `http://${dgcBaseUrl}`
      window.open(url, '_blank');
    },
    jumpToETL() {
      const etlBaseUrl = process.env["VUE_APP_ETL_BASE_URL"]
      const url = `https://${etlBaseUrl}`
      window.open(url, '_blank');
    },
    jumpToKQA() {
      // const kqaBaseUrl = process.env["VUE_APP_KQA_BASE_URL"]
      // const url = `http://${kqaBaseUrl}`
      // window.open(url, '_blank');
      this.$modal.msgWarning("该入口还未开发，敬请期待！");
    },
    jumpToCAS() {
      const casBaseUrl = process.env["VUE_APP_CAS_BASE_URL"]
      const url = `http://${casBaseUrl}`
      window.open(url, '_blank');
    }
  }
}
</script>

<style scoped lang="scss">

</style>

