import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOperationResults } from '@/shared/model/operation-results.model';

import OperationResultsService from './operation-results.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class OperationResults extends Vue {
  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;
  private removeId: number = null;

  public operationResults: IOperationResults[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOperationResultss();
  }

  public clear(): void {
    this.retrieveAllOperationResultss();
  }

  public retrieveAllOperationResultss(): void {
    this.isFetching = true;
    this.operationResultsService()
      .retrieve()
      .then(
        res => {
          this.operationResults = res.data;
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

  public prepareRemove(instance: IOperationResults): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOperationResults(): void {
    this.operationResultsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.operationResults.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOperationResultss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
