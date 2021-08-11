import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IConfirmCard } from '@/shared/model/confirm-card.model';

import ConfirmCardService from './confirm-card.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ConfirmCard extends Vue {
  @Inject('confirmCardService') private confirmCardService: () => ConfirmCardService;
  private removeId: number = null;

  public confirmCards: IConfirmCard[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllConfirmCards();
  }

  public clear(): void {
    this.retrieveAllConfirmCards();
  }

  public retrieveAllConfirmCards(): void {
    this.isFetching = true;
    this.confirmCardService()
      .retrieve()
      .then(
        res => {
          this.confirmCards = res.data;
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

  public prepareRemove(instance: IConfirmCard): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeConfirmCard(): void {
    this.confirmCardService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.confirmCard.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllConfirmCards();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
