import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import GroupMembersService from '@/entities/group-members/group-members.service';
import { IGroupMembers } from '@/shared/model/group-members.model';

import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';
import { IPrivateCardData } from '@/shared/model/private-card-data.model';

import OperationResultsService from '@/entities/operation-results/operation-results.service';
import { IOperationResults } from '@/shared/model/operation-results.model';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import { IDdUser, DdUser } from '@/shared/model/dd-user.model';
import DdUserService from './dd-user.service';

const validations: any = {
  ddUser: {
    unionid: {},
    remark: {},
    userid: {},
    isLeaderInDepts: {},
    isBoss: {},
    hiredDate: {},
    isSenior: {},
    tel: {},
    department: {},
    workPlace: {},
    orderInDepts: {},
    mobile: {},
    errmsg: {},
    active: {},
    avatar: {},
    isAdmin: {},
    isHide: {},
    jobnumber: {},
    name: {},
    extattr: {},
    stateCode: {},
    position: {},
    roles: {},
  },
};

@Component({
  validations,
})
export default class DdUserUpdate extends mixins(JhiDataUtils) {
  @Inject('ddUserService') private ddUserService: () => DdUserService;
  public ddUser: IDdUser = new DdUser();

  @Inject('groupMembersService') private groupMembersService: () => GroupMembersService;

  public groupMembers: IGroupMembers[] = [];

  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;

  public privateCardData: IPrivateCardData[] = [];

  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;

  public operationResults: IOperationResults[] = [];

  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;

  public workflowInstances: IWorkflowInstance[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ddUserId) {
        vm.retrieveDdUser(to.params.ddUserId);
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
    if (this.ddUser.id) {
      this.ddUserService()
        .update(this.ddUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.ddUser.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ddUserService()
        .create(this.ddUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.ddUser.created', { param: param.id });
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

  public retrieveDdUser(ddUserId): void {
    this.ddUserService()
      .find(ddUserId)
      .then(res => {
        this.ddUser = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.groupMembersService()
      .retrieve()
      .then(res => {
        this.groupMembers = res.data;
      });
    this.privateCardDataService()
      .retrieve()
      .then(res => {
        this.privateCardData = res.data;
      });
    this.operationResultsService()
      .retrieve()
      .then(res => {
        this.operationResults = res.data;
      });
    this.workflowInstanceService()
      .retrieve()
      .then(res => {
        this.workflowInstances = res.data;
      });
  }
}
