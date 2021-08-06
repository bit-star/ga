import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrivateCardData } from '@/shared/model/private-card-data.model';

import PrivateCardDataService from './private-card-data.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PrivateCardData extends Vue {
  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;
  private removeId: number = null;

  public privateCardData: IPrivateCardData[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPrivateCardDatas();
  }

  public clear(): void {
    this.retrieveAllPrivateCardDatas();
  }

  public retrieveAllPrivateCardDatas(): void {
    this.isFetching = true;
    this.privateCardDataService()
      .retrieve()
      .then(
        res => {
          this.privateCardData = res.data;
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

  public prepareRemove(instance: IPrivateCardData): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePrivateCardData(): void {
    this.privateCardDataService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.privateCardData.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPrivateCardDatas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
