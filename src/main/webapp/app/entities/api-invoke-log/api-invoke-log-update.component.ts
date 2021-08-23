import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IApiInvokeLog, ApiInvokeLog } from '@/shared/model/api-invoke-log.model';
import ApiInvokeLogService from './api-invoke-log.service';

const validations: any = {
  apiInvokeLog: {
    login: {},
    apiName: {},
    method: {},
    direction: {},
    httpStatusCode: {},
    time: {},
    reqeustData: {},
    responseData: {},
    ok: {},
  },
};

@Component({
  validations,
})
export default class ApiInvokeLogUpdate extends mixins(JhiDataUtils) {
  @Inject('apiInvokeLogService') private apiInvokeLogService: () => ApiInvokeLogService;
  public apiInvokeLog: IApiInvokeLog = new ApiInvokeLog();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiInvokeLogId) {
        vm.retrieveApiInvokeLog(to.params.apiInvokeLogId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.apiInvokeLog.id) {
      this.apiInvokeLogService()
        .update(this.apiInvokeLog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.apiInvokeLog.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apiInvokeLogService()
        .create(this.apiInvokeLog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.apiInvokeLog.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.apiInvokeLog[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.apiInvokeLog[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.apiInvokeLog[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.apiInvokeLog[field] = null;
    }
  }

  public retrieveApiInvokeLog(apiInvokeLogId): void {
    this.apiInvokeLogService()
      .find(apiInvokeLogId)
      .then(res => {
        res.time = new Date(res.time);
        this.apiInvokeLog = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
