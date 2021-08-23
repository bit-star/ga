import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IApiInvokeLog } from '@/shared/model/api-invoke-log.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ApiInvokeLogService from './api-invoke-log.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ApiInvokeLog extends mixins(JhiDataUtils) {
  @Inject('apiInvokeLogService') private apiInvokeLogService: () => ApiInvokeLogService;
  private removeId: number = null;

  public apiInvokeLogs: IApiInvokeLog[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApiInvokeLogs();
  }

  public clear(): void {
    this.retrieveAllApiInvokeLogs();
  }

  public retrieveAllApiInvokeLogs(): void {
    this.isFetching = true;
    this.apiInvokeLogService()
      .retrieve()
      .then(
        res => {
          this.apiInvokeLogs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IApiInvokeLog): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApiInvokeLog(): void {
    this.apiInvokeLogService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.apiInvokeLog.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllApiInvokeLogs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
