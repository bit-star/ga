import { Component, Vue, Inject } from 'vue-property-decorator';

import ConversationService from '@/entities/conversation/conversation.service';
import { IConversation } from '@/shared/model/conversation.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IGroupMembers, GroupMembers } from '@/shared/model/group-members.model';
import GroupMembersService from './group-members.service';

const validations: any = {
  groupMembers: {
    groupRole: {},
  },
};

@Component({
  validations,
})
export default class GroupMembersUpdate extends Vue {
  @Inject('groupMembersService') private groupMembersService: () => GroupMembersService;
  public groupMembers: IGroupMembers = new GroupMembers();

  @Inject('conversationService') private conversationService: () => ConversationService;

  public conversations: IConversation[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groupMembersId) {
        vm.retrieveGroupMembers(to.params.groupMembersId);
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
    if (this.groupMembers.id) {
      this.groupMembersService()
        .update(this.groupMembers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.groupMembers.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.groupMembersService()
        .create(this.groupMembers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.groupMembers.created', { param: param.id });
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

  public retrieveGroupMembers(groupMembersId): void {
    this.groupMembersService()
      .find(groupMembersId)
      .then(res => {
        this.groupMembers = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.conversationService()
      .retrieve()
      .then(res => {
        this.conversations = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}
