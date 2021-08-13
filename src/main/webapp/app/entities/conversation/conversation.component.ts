import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IConversation } from '@/shared/model/conversation.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ConversationService from './conversation.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Conversation extends mixins(JhiDataUtils) {
  @Inject('conversationService') private conversationService: () => ConversationService;
  private removeId: string = null;

  public conversations: IConversation[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllConversations();
  }

  public clear(): void {
    this.retrieveAllConversations();
  }

  public retrieveAllConversations(): void {
    this.isFetching = true;
    this.conversationService()
      .retrieve()
      .then(
        res => {
          this.conversations = res.data;
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

  public prepareRemove(instance: IConversation): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeConversation(): void {
    this.conversationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.conversation.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllConversations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
