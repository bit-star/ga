import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAlertCard } from '@/shared/model/alert-card.model';

import AlertCardService from './alert-card.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AlertCard extends Vue {
  @Inject('alertCardService') private alertCardService: () => AlertCardService;
  private removeId: number = null;

  public alertCards: IAlertCard[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAlertCards();
  }

  public clear(): void {
    this.retrieveAllAlertCards();
  }

  public retrieveAllAlertCards(): void {
    this.isFetching = true;
    this.alertCardService()
      .retrieve()
      .then(
        res => {
          this.alertCards = res.data;
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

  public prepareRemove(instance: IAlertCard): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAlertCard(): void {
    this.alertCardService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.alertCard.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAlertCards();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
