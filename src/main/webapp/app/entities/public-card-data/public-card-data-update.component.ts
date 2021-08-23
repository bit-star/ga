import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';
import { IPrivateCardData } from '@/shared/model/private-card-data.model';

import OperationResultsService from '@/entities/operation-results/operation-results.service';
import { IOperationResults } from '@/shared/model/operation-results.model';

import ConfirmCardService from '@/entities/confirm-card/confirm-card.service';
import { IConfirmCard } from '@/shared/model/confirm-card.model';

import AlertCardService from '@/entities/alert-card/alert-card.service';
import { IAlertCard } from '@/shared/model/alert-card.model';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import ConversationService from '@/entities/conversation/conversation.service';
import { IConversation } from '@/shared/model/conversation.model';

import { IPublicCardData, PublicCardData } from '@/shared/model/public-card-data.model';
import PublicCardDataService from './public-card-data.service';

const validations: any = {
  publicCardData: {
    requestid: {},
    workflowid: {},
    valid: {},
    finish: {},
    status: {},
    variables: {},
    createdTime: {},
    link: {},
    updateLink: {},
    agreeNum: {},
    refuseNum: {},
    oaStatus: {},
  },
};

@Component({
  validations,
})
export default class PublicCardDataUpdate extends mixins(JhiDataUtils) {
  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;
  public publicCardData: IPublicCardData = new PublicCardData();

  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;

  public privateCardData: IPrivateCardData[] = [];

  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;

  public operationResults: IOperationResults[] = [];

  @Inject('confirmCardService') private confirmCardService: () => ConfirmCardService;

  public confirmCards: IConfirmCard[] = [];

  @Inject('alertCardService') private alertCardService: () => AlertCardService;

  public alertCards: IAlertCard[] = [];

  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;

  public workflowInstances: IWorkflowInstance[] = [];

  @Inject('conversationService') private conversationService: () => ConversationService;

  public conversations: IConversation[] = [];
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.publicCardData[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publicCardData[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.publicCardData[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publicCardData[field] = null;
    }
  }

  public retrievePublicCardData(publicCardDataId): void {
    this.publicCardDataService()
      .find(publicCardDataId)
      .then(res => {
        res.createdTime = new Date(res.createdTime);
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
    this.confirmCardService()
      .retrieve()
      .then(res => {
        this.confirmCards = res.data;
      });
    this.alertCardService()
      .retrieve()
      .then(res => {
        this.alertCards = res.data;
      });
    this.workflowInstanceService()
      .retrieve()
      .then(res => {
        this.workflowInstances = res.data;
      });
    this.conversationService()
      .retrieve()
      .then(res => {
        this.conversations = res.data;
      });
  }
}
