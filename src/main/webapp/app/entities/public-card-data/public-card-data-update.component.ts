import { Component, Vue, Inject } from 'vue-property-decorator';

import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';
import { IPrivateCardData } from '@/shared/model/private-card-data.model';

import OperationResultsService from '@/entities/operation-results/operation-results.service';
import { IOperationResults } from '@/shared/model/operation-results.model';

import ConversationService from '@/entities/conversation/conversation.service';
import { IConversation } from '@/shared/model/conversation.model';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import { IPublicCardData, PublicCardData } from '@/shared/model/public-card-data.model';
import PublicCardDataService from './public-card-data.service';

const validations: any = {
  publicCardData: {
    name: {},
    fee: {},
    reason: {},
    itemType: {},
    typesOfFee: {},
    agree: {},
    requestid: {},
    workflowid: {},
    valid: {},
  },
};

@Component({
  validations,
})
export default class PublicCardDataUpdate extends Vue {
  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;
  public publicCardData: IPublicCardData = new PublicCardData();

  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;

  public privateCardData: IPrivateCardData[] = [];

  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;

  public operationResults: IOperationResults[] = [];

  @Inject('conversationService') private conversationService: () => ConversationService;

  public conversations: IConversation[] = [];

  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;

  public workflowTemplates: IWorkflowTemplate[] = [];

  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;

  public workflowInstances: IWorkflowInstance[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicCardDataId) {
        vm.retrievePublicCardData(to.params.publicCardDataId);
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
    if (this.publicCardData.id) {
      this.publicCardDataService()
        .update(this.publicCardData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.publicCardData.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.publicCardDataService()
        .create(this.publicCardData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.publicCardData.created', { param: param.id });
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

  public retrievePublicCardData(publicCardDataId): void {
    this.publicCardDataService()
      .find(publicCardDataId)
      .then(res => {
        this.publicCardData = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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
    this.conversationService()
      .retrieve()
      .then(res => {
        this.conversations = res.data;
      });
    this.workflowTemplateService()
      .retrieve()
      .then(res => {
        this.workflowTemplates = res.data;
      });
    this.workflowInstanceService()
      .retrieve()
      .then(res => {
        this.workflowInstances = res.data;
      });
  }
}
