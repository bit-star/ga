import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IApprover } from '@/shared/model/approver.model';

import ApproverService from './approver.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Approver extends Vue {
  @Inject('approverService') private approverService: () => ApproverService;
  private removeId: number = null;

  public approvers: IApprover[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApprovers();
  }

  public clear(): void {
    this.retrieveAllApprovers();
  }

  public retrieveAllApprovers(): void {
    this.isFetching = true;
    this.approverService()
      .retrieve()
      .then(
        res => {
          this.approvers = res.data;
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

  public prepareRemove(instance: IApprover): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApprover(): void {
    this.approverService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.approver.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllApprovers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
