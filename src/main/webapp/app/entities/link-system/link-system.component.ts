import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILinkSystem } from '@/shared/model/link-system.model';

import LinkSystemService from './link-system.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class LinkSystem extends Vue {
  @Inject('linkSystemService') private linkSystemService: () => LinkSystemService;
  private removeId: number = null;

  public linkSystems: ILinkSystem[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLinkSystems();
  }

  public clear(): void {
    this.retrieveAllLinkSystems();
  }

  public retrieveAllLinkSystems(): void {
    this.isFetching = true;
    this.linkSystemService()
      .retrieve()
      .then(
        res => {
          this.linkSystems = res.data;
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

  public prepareRemove(instance: ILinkSystem): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLinkSystem(): void {
    this.linkSystemService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.linkSystem.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLinkSystems();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
