import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

import WorkflowTemplateService from './workflow-template.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class WorkflowTemplate extends Vue {
  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;
  private removeId: number = null;

  public workflowTemplates: IWorkflowTemplate[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllWorkflowTemplates();
  }

  public clear(): void {
    this.retrieveAllWorkflowTemplates();
  }

  public retrieveAllWorkflowTemplates(): void {
    this.isFetching = true;
    this.workflowTemplateService()
      .retrieve()
      .then(
        res => {
          this.workflowTemplates = res.data;
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

  public prepareRemove(instance: IWorkflowTemplate): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeWorkflowTemplate(): void {
    this.workflowTemplateService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.workflowTemplate.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllWorkflowTemplates();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
