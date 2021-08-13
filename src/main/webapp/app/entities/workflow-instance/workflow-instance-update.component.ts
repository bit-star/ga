import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import ApproverService from '@/entities/approver/approver.service';
import { IApprover } from '@/shared/model/approver.model';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { IWorkflowInstance, WorkflowInstance } from '@/shared/model/workflow-instance.model';
import WorkflowInstanceService from './workflow-instance.service';

const validations: any = {
  workflowInstance: {
    form: {},
    ddCardId: {},
    title: {},
    ddCardTemplateId: {},
    ddCardCallBackKey: {},
    requestId: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class WorkflowInstanceUpdate extends mixins(JhiDataUtils) {
  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;
  public workflowInstance: IWorkflowInstance = new WorkflowInstance();

  @Inject('approverService') private approverService: () => ApproverService;

  public approvers: IApprover[] = [];

  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;

  public workflowTemplates: IWorkflowTemplate[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workflowInstanceId) {
        vm.retrieveWorkflowInstance(to.params.workflowInstanceId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.workflowInstance.id) {
      this.workflowInstanceService()
        .update(this.workflowInstance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.workflowInstance.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.workflowInstanceService()
        .create(this.workflowInstance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.workflowInstance.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveWorkflowInstance(workflowInstanceId): void {
    this.workflowInstanceService()
      .find(workflowInstanceId)
      .then(res => {
        this.workflowInstance = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.approverService()
      .retrieve()
      .then(res => {
        this.approvers = res.data;
      });
    this.workflowTemplateService()
      .retrieve()
      .then(res => {
        this.workflowTemplates = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
    this.publicCardDataService()
      .retrieve()
      .then(res => {
        this.publicCardData = res.data;
      });
  }
}
