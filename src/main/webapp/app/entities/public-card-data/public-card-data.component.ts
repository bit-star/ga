import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import PublicCardDataService from './public-card-data.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PublicCardData extends Vue {
  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;
  private removeId: number = null;

  public publicCardData: IPublicCardData[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublicCardDatas();
  }

  public clear(): void {
    this.retrieveAllPublicCardDatas();
  }

  public retrieveAllPublicCardDatas(): void {
    this.isFetching = true;
    this.publicCardDataService()
      .retrieve()
      .then(
        res => {
          this.publicCardData = res.data;
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

  public prepareRemove(instance: IPublicCardData): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublicCardData(): void {
    this.publicCardDataService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.publicCardData.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublicCardDatas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
