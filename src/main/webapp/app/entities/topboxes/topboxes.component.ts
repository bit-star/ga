import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITopboxes } from '@/shared/model/topboxes.model';

import TopboxesService from './topboxes.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Topboxes extends Vue {
  @Inject('topboxesService') private topboxesService: () => TopboxesService;
  private removeId: string = null;

  public topboxes: ITopboxes[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTopboxess();
  }

  public clear(): void {
    this.retrieveAllTopboxess();
  }

  public retrieveAllTopboxess(): void {
    this.isFetching = true;
    this.topboxesService()
      .retrieve()
      .then(
        res => {
          this.topboxes = res.data;
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

  public prepareRemove(instance: ITopboxes): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTopboxes(): void {
    this.topboxesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.topboxes.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTopboxess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
