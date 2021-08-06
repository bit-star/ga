import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import WorkflowInstanceService from './workflow-instance.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class WorkflowInstance extends mixins(JhiDataUtils) {
  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;
  private removeId: number = null;

  public workflowInstances: IWorkflowInstance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllWorkflowInstances();
  }

  public clear(): void {
    this.retrieveAllWorkflowInstances();
  }

  public retrieveAllWorkflowInstances(): void {
    this.isFetching = true;
    this.workflowInstanceService()
      .retrieve()
      .then(
        res => {
          this.workflowInstances = res.data;
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

  public prepareRemove(instance: IWorkflowInstance): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeWorkflowInstance(): void {
    this.workflowInstanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.workflowInstance.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllWorkflowInstances();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
