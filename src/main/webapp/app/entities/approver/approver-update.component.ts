import { Component, Vue, Inject } from 'vue-property-decorator';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IApprover, Approver } from '@/shared/model/approver.model';
import ApproverService from './approver.service';

const validations: any = {
  approver: {
    approverRole: {},
  },
};

@Component({
  validations,
})
export default class ApproverUpdate extends Vue {
  @Inject('approverService') private approverService: () => ApproverService;
  public approver: IApprover = new Approver();

  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;

  public workflowInstances: IWorkflowInstance[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.approverId) {
        vm.retrieveApprover(to.params.approverId);
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
    if (this.approver.id) {
      this.approverService()
        .update(this.approver)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.approver.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.approverService()
        .create(this.approver)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.approver.created', { param: param.id });
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

  public retrieveApprover(approverId): void {
    this.approverService()
      .find(approverId)
      .then(res => {
        this.approver = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.workflowInstanceService()
      .retrieve()
      .then(res => {
        this.workflowInstances = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}
