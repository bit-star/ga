import { Component, Vue, Inject } from 'vue-property-decorator';

import { IConversation } from '@/shared/model/conversation.model';
import ConversationService from './conversation.service';

@Component
export default class ConversationDetails extends Vue {
  @Inject('conversationService') private conversationService: () => ConversationService;
  public conversation: IConversation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.conversationId) {
        vm.retrieveConversation(to.params.conversationId);
      }
    });
  }

  public retrieveConversation(conversationId) {
    this.conversationService()
      .find(conversationId)
      .then(res => {
        this.conversation = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
