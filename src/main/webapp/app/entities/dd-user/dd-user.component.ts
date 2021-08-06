import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDdUser } from '@/shared/model/dd-user.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import DdUserService from './dd-user.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DdUser extends mixins(JhiDataUtils) {
  @Inject('ddUserService') private ddUserService: () => DdUserService;
  private removeId: number = null;

  public ddUsers: IDdUser[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDdUsers();
  }

  public clear(): void {
    this.retrieveAllDdUsers();
  }

  public retrieveAllDdUsers(): void {
    this.isFetching = true;
    this.ddUserService()
      .retrieve()
      .then(
        res => {
          this.ddUsers = res.data;
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

  public prepareRemove(instance: IDdUser): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDdUser(): void {
    this.ddUserService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.ddUser.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDdUsers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
