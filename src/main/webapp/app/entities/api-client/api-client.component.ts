import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IApiClient } from '@/shared/model/api-client.model';

import ApiClientService from './api-client.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ApiClient extends Vue {
  @Inject('apiClientService') private apiClientService: () => ApiClientService;
  private removeId: number = null;

  public apiClients: IApiClient[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApiClients();
  }

  public clear(): void {
    this.retrieveAllApiClients();
  }

  public retrieveAllApiClients(): void {
    this.isFetching = true;
    this.apiClientService()
      .retrieve()
      .then(
        res => {
          this.apiClients = res.data;
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

  public prepareRemove(instance: IApiClient): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApiClient(): void {
    this.apiClientService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.apiClient.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllApiClients();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
