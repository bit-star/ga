import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IApiInvokeLog } from '@/shared/model/api-invoke-log.model';
import ApiInvokeLogService from './api-invoke-log.service';

@Component
export default class ApiInvokeLogDetails extends mixins(JhiDataUtils) {
  @Inject('apiInvokeLogService') private apiInvokeLogService: () => ApiInvokeLogService;
  public apiInvokeLog: IApiInvokeLog = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiInvokeLogId) {
        vm.retrieveApiInvokeLog(to.params.apiInvokeLogId);
      }
    });
  }

  public retrieveApiInvokeLog(apiInvokeLogId) {
    this.apiInvokeLogService()
      .find(apiInvokeLogId)
      .then(res => {
        this.apiInvokeLog = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
