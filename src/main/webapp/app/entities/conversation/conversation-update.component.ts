import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IConversation, Conversation } from '@/shared/model/conversation.model';
import ConversationService from './conversation.service';

const validations: any = {
  conversation: {
    name: {},
    title: {},
    owner: {},
    ownerUserId: {},
    chatid: {},
    openConversationId: {},
    conversationTag: {},
    useridlist: {},
    uuid: {},
    icon: {},
    showHistoryType: {},
    searchable: {},
    validationType: {},
    chatBannedType: {},
    mentionAllAuthority: {},
    managementType: {},
    templateId: {},
    officialGroup: {},
    enableScenegroup: {},
    groupUrl: {},
    time: {},
  },
};

@Component({
  validations,
})
export default class ConversationUpdate extends mixins(JhiDataUtils) {
  @Inject('conversationService') private conversationService: () => ConversationService;
  public conversation: IConversation = new Conversation();

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.conversationId) {
        vm.retrieveConversation(to.params.conversationId);
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
    this.conversation.ddUsers = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.conversation.id) {
      this.conversationService()
        .update(this.conversation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.conversation.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.conversationService()
        .create(this.conversation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.conversation.created', { param: param.id });
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
      this.conversation[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.conversation[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.conversation[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.conversation[field] = null;
    }
  }

  public retrieveConversation(conversationId): void {
    this.conversationService()
      .find(conversationId)
      .then(res => {
        res.time = new Date(res.time);
        this.conversation = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicCardDataService()
      .retrieve()
      .then(res => {
        this.publicCardData = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
